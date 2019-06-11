package com.arkadr.mp3player

import android.app.Service
import android.content.Intent
import android.os.IBinder

class PlayerService : Service() {

    private val playerServiceBinder = PlayerServiceBinder()

    override fun onBind(intent: Intent): IBinder? {
        return playerServiceBinder
    }
}