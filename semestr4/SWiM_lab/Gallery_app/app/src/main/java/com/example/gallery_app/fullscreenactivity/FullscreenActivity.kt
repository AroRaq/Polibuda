package com.example.gallery_app.fullscreenactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.gallery_app.GalleryEntry
import com.example.gallery_app.R

class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fullscreen_image)

        pager = findViewById<ViewPager>(R.id.pager).apply {
            adapter = MyPagerAdapter(supportFragmentManager)
        }

        val bundle = intent.extras!!
        similarImages = bundle.getParcelableArrayList<GalleryEntry>("similar")!!
        entry = bundle.getParcelable("entry")!!
    }

    private lateinit var pager: ViewPager
    lateinit var entry: GalleryEntry
    lateinit var similarImages: ArrayList<GalleryEntry>

    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = 2
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> ImageFragment()
                1 -> MultiFragment()

                else -> ImageFragment()
            }
        }
    }
}