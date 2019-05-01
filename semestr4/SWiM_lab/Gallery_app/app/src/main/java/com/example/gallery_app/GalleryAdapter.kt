package com.example.gallery_app

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery_app.logic.scaleDown
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import com.example.gallery_app.logic.toByteArray
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler

class GalleryAdapter(private val myDataset: MutableList<GalleryEntry>) :
    RecyclerView.Adapter<GalleryAdapter.MyViewHolder>() {

    class MyViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        labeler = FirebaseVision.getInstance().onDeviceImageLabeler

        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false) as CardView

        return MyViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val galleryEntry = myDataset[position]
        holder.cardView.textView_cardtitle.text = galleryEntry.title
        holder.cardView.textView_carddate.text =
            SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(galleryEntry.date)
        holder.cardView.textView_cardtags.text =
            galleryEntry.tags?.take(TAG_DISPLAY_COUNT)?.joinToString() ?: ""

        holder.cardView.let { view ->
            Picasso
                .get()
                .load(myDataset[position].uri)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_cancel_black_24dp)
                .into(view.imageView_card,
                    object: Callback {
                        override fun onSuccess() {
                            downloadTags(myDataset[position], view.imageView_card, holder.cardView.textView_cardtags)
                            view.imageView_card.setOnClickListener {
                                val intent = Intent(view.context, FullscreenActivity::class.java).apply {
                                    putParcelableArrayListExtra("galleryEntries", ArrayList(myDataset))
                                    putExtra("index", holder.adapterPosition)
                                }
                                view.context.startActivity(intent)
                            }
                        }
                        override fun onError(e: Exception?) {}
                    })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun downloadTags(ge: GalleryEntry, imageView: ImageView, textView: TextView) {
        val img = FirebaseVisionImage.fromBitmap(imageView.drawable.toBitmap())
        labeler.processImage(img)
            .addOnSuccessListener { list ->
                ge.tags = list.map {el -> el.text}
                textView.text = list.take(TAG_DISPLAY_COUNT).joinToString { it.text }
            }
            .addOnFailureListener { Log.e("Labeler", "Cos sie zdupilo") }
    }

    override fun getItemCount() = myDataset.size

    private lateinit var labeler: FirebaseVisionImageLabeler

    private val TAG_DISPLAY_COUNT = 5
}