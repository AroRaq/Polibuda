package com.example.gallery_app.fullscreenactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.example.gallery_app.R
import kotlinx.android.synthetic.main.fragment_similar_images.view.*

class SimilarImagesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_similar_images, container, false)
        view.recycler_view_similar.apply {
            layoutManager = GridLayoutManager(view.context, 2)
            adapter = SimilarImageAdapter((activity as FullscreenActivity).similarImages)
        }
        return view
    }
}