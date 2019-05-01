package com.example.gallery_app

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_details.view.*


class DetailsFragment(val dataset: List<GalleryEntry>, currEntry: GalleryEntry) : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        view.recycler_view_similar.apply {
            layoutManager = GridLayoutManager(view.context, 2)
            adapter = SimilarImageAdapter(dataset)
        }
        return view
    }


    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }


}
