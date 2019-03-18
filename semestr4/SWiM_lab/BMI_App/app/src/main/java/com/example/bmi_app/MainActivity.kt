package com.example.bmi_app

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.bmi_app.logic.BMI_kgcm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.optionsmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.Settings -> {
                openSettings()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }


    public fun onCalculateClick(view: View) {
        val weight = weightInput.text.toString().toDouble()
        val height = heightInput.text.toString().toDouble()
        val calculator = BMI_kgcm(weight, height)
        try {
            val bmi = calculator.calcBMI()
            bmiRating.text = bmi.toString()
            val (message, color) = when {
                bmi < 18.5 -> Pair("Underweight", R.color.darkblue)
                bmi < 25 -> Pair("Normal", R.color.lapislazuli)
                bmi < 30 -> Pair("Overweight", R.color.grynszpan)
                bmi < 35 -> Pair("Obese", R.color.orange)
                else -> Pair("Extremely Obese", R.color.rozpompejanski)
            }
            bmiText.text = message
            bmiText.setTextColor(color)
        }
        catch (e: IllegalArgumentException) {
            Toast.makeText(this, "Wrong arguments", Toast.LENGTH_LONG).show()
        }
    }
}
