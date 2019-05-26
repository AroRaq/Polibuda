package com.arkadr.mysimplegaem.game

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.arkadr.mysimplegaem.R
import com.arkadr.mysimplegaem.Utils
import com.arkadr.mysimplegaem.graphics.Collision
import com.arkadr.mysimplegaem.graphics.Vector2f
import com.arkadr.mysimplegaem.graphics.Vector2i
import java.util.*

class Game (private val canvasSize: Vector2i, private val resources: Resources): Drawable(), SensorEventListener {

    private var obstacleBrush = Paint()
    private val smoothness = 0.1f
    private var sensorX = 0.0f
    private val obstacles = LinkedList<RectF>()
    private var gameSpeed = resources.getInteger(R.integer.initialGameSpeed)
    private val gameSpeedIncreaseRate = resources.getInteger(R.integer.gameSpeedIncreaseRate)
    private var distanceSinceLastObstacle = 0.0
    private val distanceBetweenObstacles = resources.getInteger(R.integer.distanceBetweenObstacles)
    private val gapSize = resources.getInteger(R.integer.gapSize)
    private var score = 0

    private val player = Player(Vector2f((canvasSize.x / 2).toFloat(), (canvasSize.y - 300).toFloat()), canvasSize)

    init {
        obstacleBrush.color = Color.WHITE
        obstacleBrush.strokeCap = Paint.Cap.ROUND
    }



    override fun draw(canvas: Canvas) {
        player.draw(canvas)
        obstacles.forEach { r -> canvas.drawRect(r, obstacleBrush) }
    }

    override fun setAlpha(alpha: Int) { }
    override fun getOpacity() = PixelFormat.TRANSPARENT
    override fun setColorFilter(colorFilter: ColorFilter?) { }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    override fun onSensorChanged(event: SensorEvent) {
        sensorX = (sensorX + event.values[0]) / 2
        val diff = ((canvasSize.x / 2) + sensorX * -100) - player.position.x
        player.position.x += diff * smoothness
    }

    fun update(dt: Double) {
        val verticalChange = gameSpeed * dt
        distanceSinceLastObstacle += verticalChange
        updateObjects(verticalChange)
        player.update(verticalChange)
        if (distanceSinceLastObstacle > distanceBetweenObstacles) {
            addObstacle()
            gameSpeed += gameSpeedIncreaseRate
        }
        if (obstacles.any { r -> Collision.circleRect(player.position, player.radius, r) }) {
            gameOver()
        }
    }

    private fun updateObjects(offset: Double) {
        obstacles.apply {
            forEach {
                it.top += offset.toFloat()
                it.bottom += offset.toFloat()
            }
            removeAll {
                it.top > canvasSize.y
            }
        }
    }

    private fun addObstacle() {
        val gapLeft = Utils.randInt(10, canvasSize.x - gapSize - 10).toFloat()
        obstacles.add(RectF(0.0f, -100.0f, gapLeft, 0.0f))
        obstacles.add(RectF(gapLeft + gapSize, -100.0f, canvasSize.y.toFloat(), 0.0f))
        distanceSinceLastObstacle = 0.0
    }

    fun gameOver() {
        restart()
    }

    fun restart() {
        obstacles.clear()
        player.restart()
        score = 0
        gameSpeed = resources.getInteger(R.integer.initialGameSpeed)
    }
}

