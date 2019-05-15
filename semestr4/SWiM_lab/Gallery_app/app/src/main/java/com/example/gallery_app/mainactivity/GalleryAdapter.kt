package com.example.gallery_app.mainactivity

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery_app.GalleryEntry
import com.example.gallery_app.R
import com.example.gallery_app.fullscreenactivity.FullscreenActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_main.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler

class GalleryAdapter(private val myDataset: MutableList<GalleryEntry>) :
    RecyclerView.Adapter<GalleryAdapter.MyViewHolder>() {

    class MyViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        labeler = FirebaseVision.getInstance().onDeviceImageLabeler

        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_main, parent, false) as CardView

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
                .resize(1920, 1080)
                .onlyScaleDown()
                .centerInside()
                .into(view.imageView_card,
                    object: Callback {
                        override fun onSuccess() {
                            downloadTags(myDataset[position], view.imageView_card, holder.cardView.textView_cardtags)
                            view.imageView_card.setOnClickListener {
                                val currEntry = myDataset[holder.adapterPosition]
                                val intent = Intent(view.context, FullscreenActivity::class.java).apply {
                                    putParcelableArrayListExtra("similar", ArrayList(currEntry.getSimilarImages(myDataset)))
                                    putExtra("entry", currEntry)
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