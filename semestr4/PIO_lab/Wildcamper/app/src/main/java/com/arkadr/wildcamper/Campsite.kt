package com.arkadr.wildcamper

class Campsite(name: String, description: String, location: Location,
               var website: String? = null, var telephoneNumber: String? = null, var price: Int? = null)
    : Spot(name, description, location) {

    init {
        price?.let {
            require (it >= 0) {"Campsite's price must not be negative."}
        }
    }

    fun openWebsite() {

    }

    fun call() {

    }

    fun book() {

    }

}