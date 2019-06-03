package com.arkadr.mysimplegaem.game

import android.content.Context
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
import java.lang.Math.max
import java.lang.Math.min
import java.util.*

class Game (private val canvasSize: Vector2i, private val context: Context): Drawable(), SensorEventListener {

    private var obstacleBrush = Paint()
    private var textPaint = Paint()
    private val smoothness = 0.1f
    private var sensorX = 0.0f
    private val obstacles = LinkedList<RectF>()
    private var gameSpeed = context.resources.getInteger(R.integer.initialGameSpeed)
    private val gameSpeedIncreaseRate = context.resources.getInteger(R.integer.gameSpeedIncreaseRate)
    private var distanceSinceLastObstacle = 0.0
    private val distanceBetweenObstacles = context.resources.getInteger(R.integer.distanceBetweenObstacles)
    private val gapSize = context.resources.getInteger(R.integer.gapSize)
    private var score = -1
    var gameOver = false
    private val highscore: Int
        get() = context.getSharedPreferences("simplegaem", Context.MODE_PRIVATE).getInt("HighScore", score)

    private val player = Player(Vector2f((canvasSize.x / 2).toFloat(), (canvasSize.y - 300).toFloat()), canvasSize)

    init {
        obstacleBrush.color = Color.WHITE
        obstacleBrush.strokeCap = Paint.Cap.ROUND
        textPaint.textSize = 50.0f
        textPaint.color = Color.WHITE
        textPaint.textAlign = Paint.Align.CENTER
    }



    override fun draw(canvas: Canvas) {
        player.draw(canvas)
        obstacles.forEach { r -> canvas.drawRect(r, obstacleBrush) }
        canvas.drawText("Score: ${max(score, 0)}", 100.0f, 100.0f, textPaint)
        if (gameOver)
            drawGameOver(canvas)
    }

    override fun setAlpha(alpha: Int) { }
    override fun getOpacity() = PixelFormat.TRANSPARENT
    override fun setColorFilter(colorFilter: ColorFilter?) { }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LIGHT) {
            obstacleBrush.color =
                if (event.values[0] < 80)
                    Color.WHITE
                else
                    Color.RED
        }
        else if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            if (!gameOver) {
                sensorX = (sensorX + event.values[0]) / 2
                val diff = ((canvasSize.x / 2) + sensorX * -100) - player.position.x
                player.position.x = max(min(player.position.x + diff * smoothness, canvasSize.x-player.radius), player.radius)
            }
        }
    }

    fun update(dt: Double) {
        val verticalChange = gameSpeed * dt
        distanceSinceLastObstacle += verticalChange
        updateObjects(verticalChange)
        player.update(verticalChange)
        if (distanceSinceLastObstacle > distanceBetweenObstacles) {
            addObstacle()
            gameSpeed += gameSpeedIncreaseRate
            score++
        }
        if (obstacles.any { r -> Collision.circleRect(player.position, player.radius, r) }) {
            gameOver = true
            val prefs = context.getSharedPreferences("simplegaem", Context.MODE_PRIVATE)
            if (prefs.getInt("HighScore", 0) < score)
                prefs.edit().putInt("HighScore", score).apply()
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

    fun restart() {
        obstacles.clear()
        player.restart()
        score = 0
        gameSpeed = context.resources.getInteger(R.integer.initialGameSpeed)
        gameOver = false
    }

    private fun drawGameOver(canvas: Canvas) {
        canvas.drawText("GAME OVER", canvasSize.x/2.0f, canvasSize.y*0.4f, textPaint)
        canvas.drawText("Your score: $score", canvasSize.x/2.0f, canvasSize.y*0.6f, textPaint)
        canvas.drawText("Highscore: $highscore", canvasSize.x/2.0f, canvasSize.y*0.65f, textPaint)
    }

}

