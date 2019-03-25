package com.example.bmi_app

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.bmi_app.logic.BMI_kgcm
import com.example.bmi_app.logic.BMI_lbsin
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //SET UNITS ON APP STARTUP
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        setUnits(sharedPrefs.getString("units_preference", "metric"))

        //HIDE MAIN IMAGE IF PHONE FLIPPED
        val orientation = resources.configuration.orientation
        mainImage.visibility = if (orientation == Configuration.ORIENTATION_PORTRAIT) View.VISIBLE else View.GONE

        //CREATE PREFERENCE LISTENER
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        //DELETE PREFERENCE LISTENER
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "units_preference" -> {
                setUnits(sharedPreferences?.getString(key, "metric"))
                weightInput.setText("")
                heightInput.setText("")
            }
            else -> Unit
        }
    }

    private fun setUnits(units: String?) {
        when (units) {
            "metric" -> {
                currUnits = units
                weightLabel.setText(R.string.weight_metric)
                heightLabel.setText(R.string.height_metric)
            }
            "imperial" -> {
                currUnits = units
                weightLabel.setText(R.string.weight_imperial)
                heightLabel.setText(R.string.height_imperial)
            }
            else -> {
                Toast.makeText(this, "Something went wrong (units fetching) $units", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.optionsmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.Settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.About -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                true
            }/*
            R.id.History -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                true
            }*/
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("weightInput", weightInput.text.toString())
        outState?.putString("heightInput", heightInput.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        weightInput.setText(savedInstanceState?.getString("weightInput"))
        heightInput.setText(savedInstanceState?.getString("heightInput"))

        //PERFORM CLICK TO AVOID SAVING INFO ABOUT BMI VALUE, COLORS, ETC
        calculateButton.performClick()
    }

    fun onCalculateButtonClick(view: View) {

        val weight = if (weightInput.text.toString().isEmpty()) 0.0 else weightInput.text.toString().toDouble()
        val height = if (heightInput.text.toString().isEmpty()) 0.0 else heightInput.text.toString().toDouble()
        val calculator = when (currUnits) {
            "imperial" -> BMI_lbsin(weight, height)
            else -> BMI_kgcm(weight, height)
        }
        try {
            bmiValue = calculator.calcBMI()
            val (message, color) = Pair(getMessageFromBMI(bmiValue), getColorFromBMI(bmiValue))

            //DISPLAY BMI AND CHANGE COLOR ACCOARDING TO ITS VALUE
            bmiRating.visibility = View.VISIBLE
            bmiRating.text = String.format("%.2f", bmiValue)
            bmiRating.setTextColor(color)

            //DISPLAY BMI AS DESCRIPTION
            bmiText.text = message
            bmiText.visibility = View.VISIBLE

            //SHOW BUTTON THAT TAKES USER TO MORE INFO SCREEN
            infoButton.visibility = View.VISIBLE

            //WIP: SAVE ENTRY TO SHARED PREFERENCES
            saveToHistory(weight, height, currUnits, bmiValue, bmiValue)
        }
        catch (e: IllegalArgumentException) {
            Snackbar.make(view, "Enter correct data", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun saveToHistory(weight: Double, height: Double, currUnits: String, bmiValue: Double, bmiValue1: Double) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        //with (sharedPreferences.edit()) {
            //put
        //}
    }

    private fun getColorFromBMI(bmi: Double): Int {
        return resources.getColor(when {
            bmi < 18.5 -> R.color.lapislazuli
            bmi < 25 -> R.color.grynszpan
            bmi < 30 -> R.color.orange
            bmi < 35 -> R.color.rozpompejanski
            else -> R.color.violet
        }, theme)
    }

    private fun getMessageFromBMI(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 25 -> "Normal"
            bmi < 30 -> "Overweight"
            bmi < 35 -> "Obese"
            else -> "Extremely Obese"
        }
    }

    fun onMoreInfoButtonClick(view: View) {
        val intent = Intent(this, MoreInfoActivity::class.java)
        intent.putExtra("bmiValue", bmiValue)
        intent.putExtra("bmiText", getMessageFromBMI(bmiValue))
        startActivity(intent)
    }

    private var bmiValue = 0.0
    private var currUnits = "metric"
}
