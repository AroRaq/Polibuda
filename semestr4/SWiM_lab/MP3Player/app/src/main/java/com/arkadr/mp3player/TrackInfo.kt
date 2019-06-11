package com.arkadr.mp3player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.track_info.view.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import androidx.documentfile.provider.DocumentFile


class TrackInfo(private val file: DocumentFile) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.track_info, container, false).apply {
            songName.text = if (file.name!!.indexOf(".") > 0)
                file.name?.substring(0, file.name!!.lastIndexOf("."))
            else
                file.name
            artistName.text = retrieveArtist()
            songCover.setImageBitmap(retrieveCover())
        }
    }

    private fun retrieveCover() : Bitmap? {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(context, file.uri)

        val data = mmr.embeddedPicture
        return if (data != null) {
            BitmapFactory.decodeByteArray(data, 0, data.size)
        } else {
            null
        }
    }

    private fun retrieveArtist() : String? {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(context, file.uri)

        val data = mmr.embeddedPicture
        return mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
    }
}