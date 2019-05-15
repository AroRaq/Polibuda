package com.example.gallery_app.fullscreenactivity

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.gallery_app.GalleryEntry
import com.example.gallery_app.R
import com.squareup.picasso.Picasso


class ImageFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        Picasso.get()
                .load(entry.uri)
                .resize(1080, 1920)
                .onlyScaleDown()
                .centerInside()
                .into(view.findViewById<ImageView>(R.id.image_fullscreen))
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        entry = (activity as FullscreenActivity).entry
    }

    private lateinit var entry: GalleryEntry
}
