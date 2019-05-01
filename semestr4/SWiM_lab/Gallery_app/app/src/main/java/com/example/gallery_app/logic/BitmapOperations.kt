package com.example.gallery_app.logic

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.ByteArrayOutputStream
import java.lang.Math.max

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}


fun fromByteArray(byteArray: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun Bitmap.scaleDown(maxWidth: Int, maxHeight: Int, filter: Boolean) : Bitmap{
    val scale = max(width.toDouble() / maxWidth, height.toDouble() / maxHeight)
    Log.d("SCALE", scale.toString())
    return Bitmap.createScaledBitmap(
        this,
        (width/scale).toInt(),
        (height/scale).toInt(),
        filter)
}