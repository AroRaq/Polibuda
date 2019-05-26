package com.arkadr.wildcamper

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.junit.Assert
import java.util.*

class SpotTest : StringSpec() {
    init {
        "for no reviews rating should be 0.0" {
            val spot = WildCampingSpot("spot1", "", Location(0.0, 0.0))
            spot.rating shouldBe 0.0
        }
        "for all reviews 5 rating should be 5.0" {
            val spot = WildCampingSpot("spot1", "", Location(0.0, 0.0))
            for (i in 1..10) {
                spot.addReview(Review(User("user$i"), spot, 5, "", Date()))
            }
            spot.rating shouldBe 5.0
        }
        "for reviews {4, 4, 2} rating should be around 3.33" {
            val spot = WildCampingSpot("spot1", "", Location(0.0, 0.0))
            spot.addReview(Review(User("user0"), spot, 4, "", Date()))
            spot.addReview(Review(User("user1"), spot, 4, "", Date()))
            spot.addReview(Review(User("user2"), spot, 2, "", Date()))
            spot.rating shouldBeAround 3.33
        }
    }
}



private infix fun Double.shouldBeAround(value: Double) {
    Assert.assertEquals(value, this, 0.01)
}