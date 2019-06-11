package com.arkadr.mp3player

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.music_player.*

class PlayerActivity : AppCompatActivity() {

    var playerServiceBinder: PlayerServiceBinder? = null
    var audioProgressUpdateHandler: Handler? = null


    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            playerServiceBinder = iBinder as PlayerServiceBinder
            audioProgressUpdateHandler = AudioUpdateHandler(playerServiceBinder, this@PlayerActivity)
            playerServiceBinder?.audioProgressUpdateHandler = audioProgressUpdateHandler

            seekBar.setOnSeekBarChangeListener(playerServiceBinder)
            button_prev.setOnClickListener { playerServiceBinder?.previousSong() }
            button_next.setOnClickListener { playerServiceBinder?.nextSong() }
            button_play.setOnClickListener { playerServiceBinder?.playPause() }
            pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) { }
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }
                override fun onPageSelected(position: Int) {
                    playerServiceBinder?.playSong(position)
                }
            })

            playerServiceBinder?.playSong(currIndex)
        }
        override fun onServiceDisconnected(componentName: ComponentName) { }
    }

    lateinit var pager: ViewPager
    private var currIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.music_player)
        val bundle = intent.extras!!
        currIndex = bundle.getInt("index")
        pager = track_pager.apply {
            adapter = TrackAdapter(supportFragmentManager)
        }
        pager.currentItem = currIndex
        bindAudioService()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }

    private fun bindAudioService() {
        if (playerServiceBinder == null) {
            val intent = Intent(this@PlayerActivity, PlayerService::class.java)
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    class AudioUpdateHandler(private val playerServiceBinder: PlayerServiceBinder?, private val activity: PlayerActivity): Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                PlayerServiceBinder.UPDATE_AUDIO_PROGRESS -> {
                    activity.seekBar.progress = playerServiceBinder?.currentAudioPosition ?: 0
                    val progress = (playerServiceBinder?.currentAudioPosition ?: 0)/1000
                    activity.text_progress.text = String.format("%2d:%02d", progress / 60, progress % 60)
                }
                PlayerServiceBinder.UPDATE_AUDIO_CHANGED -> {
                    onAudioChanged()
                }
                PlayerServiceBinder.UPDATE_AUDIO_STARTED -> {
                    activity.button_play.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24px)
                }
                PlayerServiceBinder.UPDATE_AUDIO_PAUSED -> {
                    activity.button_play.setBackgroundResource(R.drawable.ic_baseline_play_circle_filled_24px)
                }
            }
        }

        private fun onAudioChanged() {
            val length = (playerServiceBinder?.totalAudioDuration ?: 0)/1000
            activity.text_length.text = String.format("%2d:%02d", length / 60, length % 60)
            Log.d("SeekBar", playerServiceBinder?.totalAudioDuration.toString())
            activity.text_progress.text = String.format("%2d:%02d", 0, 0)
            activity.seekBar.progress = 0
            activity.seekBar.max = playerServiceBinder?.totalAudioDuration ?: 10000
            if (activity.pager.currentItem != playerServiceBinder?.currIndex)
                activity.pager.currentItem = playerServiceBinder?.currIndex ?: 0
        }
    }

}
