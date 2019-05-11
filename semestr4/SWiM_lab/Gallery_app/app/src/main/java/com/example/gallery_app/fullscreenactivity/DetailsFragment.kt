package com.example.gallery_app

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_details.view.*
import java.text.SimpleDateFormat
import java.util.*


class DetailsFragment(val dataset: List<GalleryEntry>, val currEntry: GalleryEntry) : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        view.recycler_view_similar.apply {
            layoutManager = GridLayoutManager(view.context, 2)
            adapter = SimilarImageAdapter(dataset)
            //addItemDecoration()
        }
        view.apply {
            textView_title.text = currEntry.title
            textView_tags.text = currEntry.tags?.joinToString() ?: ""
            textView_date.text = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(currEntry.date)
            textView_source.text = currEntry.uri.toString()
        }
        return view
    }


    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }


}
