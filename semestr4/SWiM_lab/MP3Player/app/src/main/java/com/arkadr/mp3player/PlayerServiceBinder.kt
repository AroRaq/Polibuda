package com.arkadr.mp3player

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.Handler
import android.os.Message
import android.widget.SeekBar

class PlayerServiceBinder : Binder(), SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener {

    companion object {
        const val UPDATE_AUDIO_PROGRESS = 1
        const val UPDATE_AUDIO_CHANGED = 3
        const val UPDATE_AUDIO_PAUSED = 4
        const val UPDATE_AUDIO_STARTED = 5
    }

    private var player = MediaPlayer()
    private lateinit var updateAudioProgressThread: Thread
    lateinit var context: Context

    var audioProgressUpdateHandler: Handler? = null
    var currIndex = 0
    val currentAudioPosition get() = player.currentPosition
    val totalAudioDuration get() = player.duration
    private val tracks = SongList.get()


    init {
        initAudioProgressThread()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        player.seekTo(seekBar?.progress ?: 0)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        nextSong()
    }

    private fun start() = player.start()
    private fun pause() = player.pause()

    fun playSong(idx: Int) {
        currIndex = idx
        player.release()
        player = MediaPlayer()
        player.setDataSource(this.context, SongList.get()[idx].uri)
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        player.setAudioAttributes(audioAttributes)
        player.setOnPreparedListener(this)
        player.setOnCompletionListener(this)
        player.prepareAsync()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        val msg = Message()
        msg.what = UPDATE_AUDIO_CHANGED
        audioProgressUpdateHandler?.sendMessage(msg)
        start()
    }

    fun nextSong() {
        currIndex++
        if (currIndex == tracks.size)
            currIndex = 0
        playSong(currIndex)
    }

    fun previousSong() {
        currIndex--
        if (currIndex < 0)
            currIndex = tracks.size - 1
        playSong(currIndex)
    }

    fun playPause() {
        if (player.isPlaying) {
            pause()
            val msg = Message()
            msg.what = UPDATE_AUDIO_PAUSED
            audioProgressUpdateHandler?.sendMessage(msg)
        } else {
            start()
            val msg = Message()
            msg.what = UPDATE_AUDIO_STARTED
            audioProgressUpdateHandler?.sendMessage(msg)
        }
    }

    private fun initAudioProgressThread() {
        updateAudioProgressThread = object : Thread() {
            override fun run() {
                while (true) {
                    val updateAudioProgressMsg = Message()
                    updateAudioProgressMsg.what = UPDATE_AUDIO_PROGRESS
                    audioProgressUpdateHandler?.sendMessage(updateAudioProgressMsg)
                    try {
                        sleep(1000)
                    } catch (ex: InterruptedException) {
                        ex.printStackTrace()
                    }
                }
            }
        }
        updateAudioProgressThread.start()
    }
}