package com.arkadr.mysimplegaem

import java.util.*

abstract class Utils {
    companion object {

        private val random = Random()

        fun randInt(from: Int, to: Int) : Int {
            return random.nextInt(to - from) + from
        }
    }
}