package com.arkadr.mysimplegaem.graphics

import kotlin.math.sqrt


class Vector2f(var x: Float, var y: Float) {
    constructor(src: Vector2f) : this(src.x, src.y)

    operator fun plus(other: Vector2f) : Vector2f{
        return Vector2f(x + other.x, y + other.y)
    }

    operator fun minus(other: Vector2f) : Vector2f{
        return Vector2f(x - other.x, y - other.y)
    }

    operator fun invoke() : Float {
        return sqrt(x * x + y * y)
    }
}

class Vector2i(var x: Int, var y: Int) {
    constructor(src: Vector2i) : this(src.x, src.y)
}