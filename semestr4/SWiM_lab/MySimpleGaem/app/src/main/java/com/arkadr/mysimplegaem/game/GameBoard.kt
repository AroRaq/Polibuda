package com.arkadr.mysimplegaem.game

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceView
import com.arkadr.mysimplegaem.graphics.Vector2i
import java.util.*

class GameBoard(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs), Runnable {

    override fun run() {
        while (playing) {

            val timeElapsed = (Calendar.getInstance().timeInMillis - lastFrameTime) / 1000.0
            lastFrameTime = Calendar.getInstance().timeInMillis
            game.update(timeElapsed)

            draw()
        }
    }

    fun draw() {
        if (holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            canvas.drawRGB(0, 0, 0)
            game.draw(canvas)

            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun setupGame() {
        game = Game(Vector2i(width, height), context.resources)
    }

    fun pause() {
        playing = false
        try {
            gameThread.join()
        } catch (e: InterruptedException) {
            Log.e("Error:", "joining thread")
        }
    }

    fun resume() {
        playing = true
//        prepareLevel()
        gameThread.start()
    }


    var game = Game(Vector2i(1000, 1000), context.resources)
    private val brush = Paint()
    private var lastFrameTime = Calendar.getInstance().timeInMillis
    private val gameThread = Thread(this)
    private var playing = true
}



