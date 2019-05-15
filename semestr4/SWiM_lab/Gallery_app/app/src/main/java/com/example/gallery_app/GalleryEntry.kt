package com.example.gallery_app

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class GalleryEntry(val uri: Uri,
                        val title: String?,
                        val date: Date?,
                        var tags: List<String>?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Uri::class.java.classLoader)!!,
            parcel.readString(),
            Date(parcel.readLong()),
            mutableListOf<String>().also { parcel.readStringList(it) }
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.apply {
            writeParcelable(uri, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
            writeString(title)
            writeLong(date?.time ?: 0)
            writeStringList(tags)
        }
    }

    fun getSimilarImages(entryList: List<GalleryEntry>): List<GalleryEntry> {
        return entryList.map { el ->
            Pair(el, el.tags!!.count { tag -> this.tags!!.contains(tag) })
        }.filter { el -> el.second > 0 && el.first.uri != this.uri }
                .sortedBy { el -> el.second }
                .map { el -> el.first }
                .reversed()
    }

    companion object CREATOR : Parcelable.Creator<GalleryEntry> {
        override fun createFromParcel(parcel: Parcel): GalleryEntry {
            return GalleryEntry(parcel)
        }

        override fun newArray(size: Int): Array<GalleryEntry?> {
            return arrayOfNulls(size)
        }
    }
}