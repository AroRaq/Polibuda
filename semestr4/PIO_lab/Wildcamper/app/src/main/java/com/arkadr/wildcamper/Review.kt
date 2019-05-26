package com.arkadr.wildcamper

import java.util.*

class Review(val user: User, val spot: Spot, val rating: Int, val desription: String, val reviewDate: Date) {

    init {
        require(rating in 1..6) {"Review must be a number from 1 to 6"}
    }

}