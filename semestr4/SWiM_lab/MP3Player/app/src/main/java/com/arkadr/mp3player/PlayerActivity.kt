package com.arkadr.mp3player

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.music_player.*

class PlayerActivity : AppCompatActivity() {

    lateinit var pager: ViewPager
    lateinit var dataset: ArrayList<Uri>
    var currIndex = 0
    lateinit var mediaPlayerThread: MediaPlayerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.music_player)
        val bundle = intent.extras!!
//        dataset = bundle.getParcelableArrayList<Track>("songs")!!
        currIndex = bundle.getInt("index")
        pager = track_pager.apply {
            adapter = TrackAdapter(supportFragmentManager)
        }
        mediaPlayerThread = MediaPlayerThread(this, dataset, currIndex)
        runOnUiThread(mediaPlayerThread)
        seekBar.setOnSeekBarChangeListener(mediaPlayerThread)
        button_prev.setOnClickListener { mediaPlayerThread.previousSong() }
        button_next.setOnClickListener { mediaPlayerThread.nextSong() }
        button_play.setOnClickListener { mediaPlayerThread.onPlayClick() }

    }


}