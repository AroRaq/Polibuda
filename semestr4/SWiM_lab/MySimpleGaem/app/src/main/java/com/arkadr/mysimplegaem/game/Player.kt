package com.arkadr.mysimplegaem.game

import android.graphics.*
import android.graphics.drawable.Drawable
import com.arkadr.mysimplegaem.graphics.Line
import com.arkadr.mysimplegaem.graphics.Vector2f
import com.arkadr.mysimplegaem.graphics.Vector2i
import java.util.*

class Player (var position: Vector2f, val canvasSize: Vector2i) : Drawable() {

    var lastPosition = Vector2f(position)
    val radius = 50.0f
    val brush = Paint()
    private val trace = LinkedList<Line>()

    init {
        brush.strokeWidth = radius * 2
        brush.color = Color.WHITE
        brush.strokeCap = Paint.Cap.ROUND
    }


    override fun draw(canvas: Canvas) {
        trace.forEach { l -> canvas.drawLine(l.start.x, l.start.y, l.stop.x, l.stop.y, brush)}
        canvas.drawCircle(position.x, position.y, radius, brush)
    }

    fun update(offset: Double) {
        lastPosition.y += offset.toInt()
        trace.apply {
            forEach{
                it.start.y += offset.toFloat()
                it.stop.y += offset.toFloat()
            }
            removeAll {
                it.start.y > canvasSize.y + radius
            }
            add(Line(Vector2f(position), Vector2f(lastPosition)))
        }
        lastPosition = Vector2f(position)
    }

    fun restart() {
        trace.clear()
    }

    override fun setAlpha(alpha: Int) {}
    override fun getOpacity() = PixelFormat.TRANSPARENT
    override fun setColorFilter(colorFilter: ColorFilter?) {}
}