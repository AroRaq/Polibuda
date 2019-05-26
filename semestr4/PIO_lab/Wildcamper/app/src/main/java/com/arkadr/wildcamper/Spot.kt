package com.arkadr.wildcamper

import java.util.*
import kotlin.collections.ArrayList

abstract class Spot(val name: String, val description: String, val location: Location) {

    fun navigate() = 0
    fun edit() = 0
    fun addReview(review: Review) {
        reviews.add(review)
    }
    val rating: Double
        get() {
            if (reviews.isEmpty())
                return 0.0
            return reviews.fold(0, {acc, e -> acc + e.rating}).toDouble() / reviews.size
        }

    val reviews = ArrayList<Review>()

}