package com.example.gallery_app

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class GalleryAdapter(private val myDataset: MutableList<GalleryEntry>) :
    RecyclerView.Adapter<GalleryAdapter.MyViewHolder>() {

    class MyViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): GalleryAdapter.MyViewHolder {

        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false) as CardView

        return MyViewHolder(cardView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cardView.textView_cardtitle.text = myDataset[position].title
        holder.cardView.textView_carddate.text =
            SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(myDataset[position].date)

        Picasso
            .get()
            .load(myDataset[position].uri)
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.ic_cancel_black_24dp)
            .into(holder.cardView.imageView_card,
                object: Callback {
                    override fun onSuccess() {
                        downloadTags(holder.cardView.imageView_card, holder.cardView.textView_cardtags)
                    }

                    override fun onError(e: Exception?) {

                    }
                })
    }

    @SuppressLint("SetTextI18n")
    private fun downloadTags(imageView: ImageView, textView: TextView) {
        val img = FirebaseVisionImage.fromBitmap(imageView.drawable.toBitmap())
        val labeler = FirebaseVision.getInstance().onDeviceImageLabeler
        labeler.processImage(img)
            .addOnSuccessListener { list ->
                textView.text = "Tags: ${list.joinToString { it.text }}"
            }
            .addOnFailureListener {
                Log.e("Labeler", "Cos sie zdupilo")
            }
    }

    override fun getItemCount() = myDataset.size
}