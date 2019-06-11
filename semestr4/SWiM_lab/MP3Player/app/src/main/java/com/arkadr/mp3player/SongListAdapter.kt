package com.arkadr.mp3player

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.song_listing.view.*

class SongListAdapter: RecyclerView.Adapter<SongListAdapter.MyViewHolder>() {

    lateinit var playerServiceBinder: PlayerServiceBinder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.song_listing, parent,false) as CardView
        return MyViewHolder(cardView)
    }

    override fun getItemCount() = SongList.get().size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cardView.songName.text = SongList.get()[position].name
        holder.cardView.setOnClickListener {
            val intent = Intent(it.context, PlayerActivity::class.java)
            intent.putExtra("index", position)
            it.context.startActivity(intent)
        }
    }

    class MyViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)
}