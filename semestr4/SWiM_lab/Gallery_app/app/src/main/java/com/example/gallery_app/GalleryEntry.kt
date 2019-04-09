package com.example.gallery_app

import android.net.Uri
import java.util.*

data class GalleryEntry(val uri: Uri, val title: String?, val tags: List<String>?, val date: Date?)