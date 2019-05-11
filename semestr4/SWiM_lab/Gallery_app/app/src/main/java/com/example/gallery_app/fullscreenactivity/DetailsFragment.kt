package com.example.gallery_app.fullscreenactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gallery_app.R
import kotlinx.android.synthetic.main.fragment_details.view.*
import java.text.SimpleDateFormat
import java.util.*


class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        val entry = (activity as FullscreenActivity).entry
        view.apply {
            textView_title.text = entry.title
            textView_tags.text = entry.tags?.joinToString() ?: ""
            textView_date.text = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(entry.date)
            textView_source.text = entry.uri.toString()
        }
        return view
    }

}
