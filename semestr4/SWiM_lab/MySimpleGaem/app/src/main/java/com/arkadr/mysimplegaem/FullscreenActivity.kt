package com.arkadr.mysimplegaem

import android.content.Context
import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arkadr.mysimplegaem.game.GameView


class FullscreenActivity : AppCompatActivity() {

    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        gameView = GameView(this, size)

        setContentView(gameView)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        sensorManager.registerListener(gameView.game, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        sensorManager.registerListener(gameView.game, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }

}
