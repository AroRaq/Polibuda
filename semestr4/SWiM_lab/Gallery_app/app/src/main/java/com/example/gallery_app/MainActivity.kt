package com.example.gallery_app

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leinardi.android.speeddial.SpeedDialActionItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), NewEntryDialogFragment.NewEntryDialogListener {

    override fun onDialogNegativeClick(dialog: DialogFragment) {

    }

    override fun onDialogPositiveClick(dialog: DialogFragment, bundle: Bundle) {
        imageHolder.addGalleryEntry(bundle.toGalleryEntry())
        viewAdapter.notifyItemInserted(viewAdapter.itemCount)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        imageHolder = ViewModelProviders.of(this).get(ImageHolder::class.java)
        setUpSpeedDial()
        setUpRecycledView()
        setUpSwipeToDismiss()
        //Picasso.get().isLoggingEnabled = true
    }

    private fun setUpSpeedDial() {
        speedDial.apply {
            addActionItem(
                SpeedDialActionItem.Builder(R.id.fab_fromurl, R.drawable.ic_web_black_24dp)
                    .setLabel("Photo link")
                    .setLabelBackgroundColor(Color.WHITE)
                    .setLabelClickable(false)
                    .create())
            addActionItem(SpeedDialActionItem.Builder(R.id.fab_fromgallery, R.drawable.ic_photo_black_24dp)
                .setLabel("From gallery")
                .setLabelBackgroundColor(Color.WHITE)
                .setLabelClickable(false)
                .create())
            setOnActionSelectedListener {
                when (it.id) {
                    R.id.fab_fromurl -> {
                        NewEntryDialogFragment().show(supportFragmentManager, "fab")
                        false
                    }
                    R.id.fab_fromgallery -> {
                        pickImageFromGallery()
                        false
                    }
                    else ->false
                }
            }
        }
    }

    private fun setUpSwipeToDismiss() {
        simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                imageHolder.getGalleryEntries().removeAt(viewHolder.adapterPosition)
                viewAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false
        }
        itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setUpRecycledView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = GalleryAdapter(imageHolder.getGalleryEntries())

        recyclerView = recycler_view_gallery.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, SELECT_IMAGES_FROM_GALLERY)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                SELECT_IMAGES_FROM_GALLERY -> {
                    if (data!!.clipData != null) {
                        val count = data.clipData.itemCount
                        for (i in 0 until count) {
                            val uri = data.clipData.getItemAt(i).uri
                            imageHolder.addGalleryEntry(GalleryEntry(uri!!, nameFromUri(uri), Date(), null))
                        }
                    }
                    else if (data.data != null) {
                        val uri = data.data
                        imageHolder.addGalleryEntry(GalleryEntry(uri!!, nameFromUri(uri), Date(), null))
                    }

                    viewAdapter.notifyItemInserted(viewAdapter.itemCount)
                }
            }
        }
    }

    private fun nameFromUri(uri: Uri?): String? {
        return uri?.let { returnUri ->
            contentResolver.query(returnUri, null, null, null, null)
        }?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            cursor.getString(nameIndex).split(".")[0]
        }
    }

    private fun Bundle.toGalleryEntry() : GalleryEntry {
        val date = try {
            SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(getString("Date"))
        } catch(e: ParseException) {
            Calendar.getInstance().time
        }
        val title = getString("Title")!!
        val tags = getString("Tags")!!.split(",")
        val url = getString("Url")!!
        return GalleryEntry(Uri.parse(url), title, date, tags)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback
    private lateinit var itemTouchHelper: ItemTouchHelper

    private lateinit var imageHolder: ImageHolder

    companion object {
        const val SELECT_IMAGES_FROM_GALLERY = 667
    }

}

class ImageHolder : ViewModel() {

    private val galleryEntries = mutableListOf<GalleryEntry>()

    fun getGalleryEntries() : MutableList<GalleryEntry> {
        return galleryEntries
    }

    fun addGalleryEntry(entry: GalleryEntry) {
        galleryEntries.add(entry)
    }
}
