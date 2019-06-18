package com.arkadr.tvgallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_photo.*
import kotlinx.android.synthetic.main.fragment_photo.view.*

class PhotoFragment : Fragment() {

    private lateinit var photo: Photo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo, container, false)
        Glide.with(view.context)
            .load(photo.uri)
            .placeholder(R.drawable.black)
            .centerCrop()
            .crossFade(500)
            .into(view.image)
        return view
    }

    fun setPhoto(photo: Photo) {
        this.photo = photo
        if (view != null) {
            Glide.with(view?.context)
                .load(photo.uri)
                .placeholder(R.drawable.black)
                .centerCrop()
                .crossFade(500)
                .into(image)
        }
    }
}
