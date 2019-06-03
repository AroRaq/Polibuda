package com.arkadr.mp3player

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.adamratzman.spotify.spotifyApi
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    val CLIENT_ID = "307c90e9631446b48a6d354bfa5e86ca"
    val CLIENT_SECRET = "202be2e395ca4b80b14ecec6cd746a8c"
    val REDIRECT_URI = "testschema://callback"

    lateinit var spotifyAppRemote: SpotifyAppRemote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val api = spotifyApi {
            credentials {
                clientId = CLIENT_ID
                redirectUri = REDIRECT_URI
                clientSecret = CLIENT_SECRET


            }
        }.buildCredentialed()

//        val connectionParams: ConnectionParams = ConnectionParams.Builder(CLIENT_ID)
//            .setRedirectUri(REDIRECT_URI)
//            .showAuthView(true)
//            .build()

//        SpotifyAppRemote.connect(this, connectionParams, object:Connector.ConnectionListener {
//            override fun onFailure(p0: Throwable?) {
//                Log.d("ERROR", p0.toString())
//            }
//            override fun onConnected(sap: SpotifyAppRemote) {
//                spotifyAppRemote =  sap
//                connected()
//            }
//
//        })



        button_open.setOnClickListener {
            val intent = Intent(this, PlayerActivity::class.java).apply {
                putExtra("index", 0)
                putParcelableArrayListExtra("songs", ArrayList<Uri>())
            }
            startActivity(intent)
        }
    }


    fun connected() {
        spotifyAppRemote.playerApi.play("spotify:playlist:37i9dQZF1DX7K31D69s4M1")
    }
}
