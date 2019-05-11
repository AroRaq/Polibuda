package com.example.gallery_app.logic

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

fun nameFromUri(uri: Uri?, context: Context): String? {
    return uri?.let { returnUri ->
        context.contentResolver.query(returnUri, null, null, null, null)
    }?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        cursor.getString(nameIndex).split(".")[0]
    }
}