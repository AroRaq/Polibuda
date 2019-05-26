package com.arkadr.mysimplegaem.graphics

import android.graphics.RectF
import java.lang.Math.max
import java.lang.Math.min

abstract class Collision {
    companion object {
        fun circleRect(center: Vector2f, radius: Float, rect: RectF) : Boolean {
            val dx = center.x - max(rect.left, min(center.x, rect.right))
            val dy = center.y - max(rect.top, min(center.y, rect.bottom))
            return (dx*dx + dy*dy) < (radius*radius)
        }

    }


}