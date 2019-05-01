package com.example.gallery_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager

class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fullscreen_image)

        pager = findViewById<ViewPager>(R.id.pager).apply {
            adapter = MyPagerAdapter(supportFragmentManager)
        }

        val bundle = intent.extras!!
        dataset = bundle.getParcelableArrayList<GalleryEntry>("galleryEntries")!!
        index = bundle.getInt("index")
    }

    private lateinit var pager: ViewPager
    private var index: Int = 0
    private lateinit var dataset: ArrayList<GalleryEntry>

    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = 2
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> ImageFragment(dataset[index])
                1 -> DetailsFragment(
                    dataset[index].let {
                        dataset.filter { el -> el != it && el.tags?.any { tag -> it.tags?.contains(tag) ?: false} ?: false} },
                    dataset[index])

                else -> ImageFragment(dataset[index])
            }
        }
    }
}