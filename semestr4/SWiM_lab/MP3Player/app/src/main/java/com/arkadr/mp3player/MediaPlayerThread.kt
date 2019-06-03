package com.arkadr.mp3player

import android.annotation.SuppressLint
import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.widget.SeekBar
import kotlinx.android.synthetic.main.music_player.*
import kotlin.collections.ArrayList


class MediaPlayerThread(val activity: Activity, val dataset: ArrayList<Uri>, var currIndex: Int) : Runnable,
    SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener {


    lateinit var mediaPlayer: MediaPlayer
    val handler = Handler()

    init {
        playSong(currIndex)
    }

    @SuppressLint("SetTextI18n")
    override fun run() {
        //playSong(currIndex)
        if (mediaPlayer.isPlaying) {
            val seconds = (mediaPlayer.currentPosition / 1000) % 60
            val minutes = (mediaPlayer.currentPosition / (1000 * 60) % 60)
            activity.text_progress.text = "$minutes:${"%02d".format(seconds)}"
            activity.seekBar.progress = mediaPlayer.currentPosition
        }
        handler.postDelayed(this, 1000)
    }

    fun start() {
        mediaPlayer.start()
        mediaPlayer.currentPosition
        activity.button_play.text = "Pause "
    }

    fun pause() {
        mediaPlayer.pause()
        activity.button_play.text = "Resume"
    }


    fun onPlayClick() {
        if (mediaPlayer.isPlaying)
            pause()
        else
            start()
    }

    fun nextSong() {
        currIndex++
        if (currIndex == dataset.size)
            currIndex = 0
        activity.track_pager.currentItem = currIndex
        playSong(currIndex)
    }

    fun previousSong() {
        if (mediaPlayer.currentPosition > 2000) {
            mediaPlayer.seekTo(0)
        }
        else {
            currIndex--
            if (currIndex < 0)
                currIndex = dataset.size - 1
            playSong(currIndex)
        }
    }

    @SuppressLint("SetTextI18n")
    fun playSong(index: Int) {
        if (::mediaPlayer.isInitialized)
            mediaPlayer.release()
//        mediaPlayer = MediaPlayer.create(activity, R.raw.crab_rave)
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource("https://youtu.be/Ba9zDCz0vr0")
        mediaPlayer.prepare()

        activity.text_progress.text = "0:00"
        val seconds = (mediaPlayer.duration / 1000) % 60
        val minutes = (mediaPlayer.duration / (1000 * 60) % 60)
        activity.text_length.text = "$minutes:${"%02d".format(seconds)}"
        activity.seekBar.max = mediaPlayer.duration
        mediaPlayer.start()
    }


    override fun onCompletion(mp: MediaPlayer) {
        nextSong()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        mediaPlayer.seekTo(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) { }
    override fun onStopTrackingTouch(seekBar: SeekBar?) { }
}