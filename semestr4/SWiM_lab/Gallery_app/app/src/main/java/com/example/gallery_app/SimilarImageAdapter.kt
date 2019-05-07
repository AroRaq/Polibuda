package com.example.gallery_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item.view.*


class SimilarImageAdapter(val dataset: List<GalleryEntry>) : RecyclerView.Adapter<SimilarImageAdapter.SimilarImageViewHolder>() {

    class SimilarImageViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false) as CardView
        return SimilarImageViewHolder(view)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: SimilarImageViewHolder, position: Int) {
        Picasso.get().load(dataset[position].uri)
            .resize(540, 540)
            .onlyScaleDown()
            .centerInside()
            .into(holder.cardView.image_similar)
    }

}