package com.arkadr.mp3player

import androidx.annotation.MainThread
import androidx.documentfile.provider.DocumentFile

class SongList {

    companion object {
        private val instance = ArrayList<DocumentFile>()

        @MainThread
        fun get() = instance
    }
}