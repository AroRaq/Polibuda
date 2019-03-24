package com.example.bmi_app

import android.content.Intent
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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        setUpSharedPreferences()
        setUnits(sharedPrefs.getString("units_preference", "metric"))

        val orientation = resources.configuration.orientation
        mainImage.visibility = if (orientation == Configuration.ORIENTATION_PORTRAIT) View.VISIBLE else View.GONE
    }

    private fun setUnits(units: String?) {
        //Toast.makeText(this, "Units changed", Toast.LENGTH_SHORT).show()
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

    private fun setUpSharedPreferences() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
       // Toast.makeText(this, "Registered Listener", Toast.LENGTH_SHORT).show()
        sharedPreferences.registerOnSharedPreferenceChangeListener { sp, s ->
            setUnits(sp.getString(s, ""))
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
            }
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
        calculateButton.performClick()
    }

    fun onCalculateButtonClick(view: View) {

        val weight = if (weightInput.text.toString().isEmpty()) 0.0 else weightInput.text.toString().toDouble()
        val height = if (heightInput.text.toString().isEmpty()) 0.0 else heightInput.text.toString().toDouble()
        val calculator = when (currUnits) {
            "metric" -> BMI_kgcm(weight, height)
            else -> BMI_lbsin(weight, height)
        }
        try {
            bmiValue = calculator.calcBMI()
            val (message, color) = Pair(getMessageFromBMI(bmiValue), getColorFromBMI(bmiValue))
            bmiRating.visibility = View.VISIBLE
            bmiRating.text = String.format("%.2f", bmiValue)
            bmiRating.setTextColor(color)

            bmiText.text = message
            bmiText.visibility = View.VISIBLE

            infoButton.visibility = View.VISIBLE
        }
        catch (e: IllegalArgumentException) {
            Snackbar.make(view, "Enter correct data", Snackbar.LENGTH_SHORT).show()
        }
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
