package com.arkadr.mysimplegaem.game

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.SurfaceView
import android.view.View
import com.arkadr.mysimplegaem.graphics.Vector2i
import java.util.*


class GameView(context: Context, size: Point) : SurfaceView(context), Runnable, View.OnClickListener {

    var game = Game(Vector2i(size.x, size.y), context)
    private val brush = Paint()
    private var lastFrameTime = Calendar.getInstance().timeInMillis
    private val gameThread = Thread(this)
    private var playing = true

    override fun run() {
        while (playing) {

            val timeElapsed = (System.currentTimeMillis() - lastFrameTime) / 1000.0
            lastFrameTime = System.currentTimeMillis()
            if (!game.gameOver)
                game.update(timeElapsed)

            draw()
        }
    }

    override fun onClick(v: View?) {
        if (game.gameOver)
            game.restart()
    }

    private fun draw() {
        if (holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            canvas.drawRGB(0, 0, 0)
            game.draw(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
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
        gameThread.start()
        this.setOnClickListener(this)
    }

}



