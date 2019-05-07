package com.example.gallery_app

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso


class ImageFragment(private val entry: GalleryEntry) : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        Picasso.get()
            .load(entry.uri)
            .resize(1080, 1920)
            .onlyScaleDown()
            .centerInside()
            .into(view.findViewById<ImageView>(R.id.image_fullscreen))
        return view
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

}
