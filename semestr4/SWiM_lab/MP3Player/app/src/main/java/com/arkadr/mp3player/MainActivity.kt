package com.arkadr.mp3player

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val RESULT_FOLDER = 141
    }

    private lateinit var audioServiceBinder: PlayerServiceBinder

    lateinit var songListAdapter : SongListAdapter

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            audioServiceBinder = iBinder as PlayerServiceBinder
            audioServiceBinder.context = this@MainActivity
            songListAdapter.playerServiceBinder = audioServiceBinder
        }
        override fun onServiceDisconnected(componentName: ComponentName) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        songListAdapter = SongListAdapter()
        songList.adapter = songListAdapter
        songList.layoutManager = LinearLayoutManager(this)

        bindAudioService()

        chooseFolder()
    }

    private fun unBoundAudioService() {
        unbindService(serviceConnection)
    }

    override fun onDestroy() {
        unBoundAudioService()
        super.onDestroy()
    }

    private fun bindAudioService() {
        if (!::audioServiceBinder.isInitialized) {
            val intent = Intent(this@MainActivity, PlayerService::class.java)
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun chooseFolder() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(Intent.createChooser(intent, "Choose folder"), RESULT_FOLDER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RESULT_FOLDER -> {
                    val treeUri = data?.data!!
                    val pickedDir = DocumentFile.fromTreeUri(this, treeUri)
                    SongList.get().clear()
                    for (file in pickedDir!!.listFiles()) {
                        if (file.name?.endsWith(".mp3") == true) {
                            SongList.get().add(file)
                        }
                    }
                    songListAdapter.notifyDataSetChanged()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}