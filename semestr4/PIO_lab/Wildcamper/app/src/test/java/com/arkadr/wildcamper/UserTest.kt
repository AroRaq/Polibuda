package com.arkadr.wildcamper

import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import java.lang.IllegalArgumentException


class UserTest : StringSpec() {

    init {
        "for follow same user throw exception" {
            val user = User("noobmaster69")
            shouldThrow<IllegalArgumentException> {
                user.follow(user)
            }
        }
        "for follow different user should add to following" {
            val user1 = User("noobmaster69")
            val user2 = User("TheLegend27")
            user1.follow(user2)
            user1.following shouldContain user2
        }
        "for reviewPlace on new place should add to sharedPlaces" {
            val user = User("noobmaster69")
            val spot = WildCampingSpot("spot1", "desc", Location(0.0, 0.0))
            user.reviewPlace(spot, 5, "")
            user.reviews shouldHaveSize 1
        }
        "for user with too little reviews throw exception on sharePlace" {
            val user = User("noobmaster69")
            for (i in 1..9) {
                val spot = WildCampingSpot("spot$i", "", Location(0.0, 0.0))
                user.reviewPlace(spot, 4, "")
            }
            shouldThrow<IllegalArgumentException> {
                user.sharePlace()
            }
        }
        "for user with enough reviews return place on sharePlace" {
            val user = User("noobmaster69")
            for (i in 1..10) {
                val spot = WildCampingSpot("spot$i", "", Location(0.0, 0.0))
                user.reviewPlace(spot, 4, "")
            }
            user.sharePlace().shouldBeInstanceOf<Spot>()
        }
    }
}