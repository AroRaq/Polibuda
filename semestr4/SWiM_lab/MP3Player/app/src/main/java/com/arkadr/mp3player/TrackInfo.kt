package com.arkadr.mp3player

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class TrackInfo : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.track_info, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

}