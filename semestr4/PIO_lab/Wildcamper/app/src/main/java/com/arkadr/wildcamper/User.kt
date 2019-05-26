package com.arkadr.wildcamper

import java.util.*
import kotlin.collections.ArrayList

class User (val nickname: String) {

    fun follow(user: User) {
        require (user != this) {"User can't follow himself."}
        following.add(user)
    }

    fun sharePlace() : Spot {
        require(reviews.size >= 10) {"User must review at least 10 places before sharing one."}
        return WildCampingSpot("spotx", "", Location(0.0, 0.0))
    }

    fun reviewPlace(spot: Spot, rating: Int, description: String) {
        require (!reviews.any { r -> r.spot == spot}) {"This user already reviewed this place"}
        val review = Review(this, spot, rating, description, Date())
        reviews.add(review)
        spot.addReview(review)
    }

    val following = ArrayList<User>()
    val sharedPlaces = ArrayList<Spot>()
    val reviews = ArrayList<Review>()

}