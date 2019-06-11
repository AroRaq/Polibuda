package com.arkadr.mp3player

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TrackAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return TrackInfo(SongList.get()[position])
    }

    override fun getCount(): Int {
        return SongList.get().size
    }
}