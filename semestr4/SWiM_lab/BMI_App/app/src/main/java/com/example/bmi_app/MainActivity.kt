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
import com.example.bmi_app.`class`.HistoryEntry
import com.example.bmi_app.logic.*
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

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
                weightInput.text = null
                heightInput.text = null
            }
            "entryHistory" -> {
                optionsMenu.findItem(R.id.History).isEnabled = sharedPreferences?.contains("entryHistory") ?: false
            }
            else -> Unit
        }
    }

    private fun setUnits(units: String?) = when (units) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.optionsmenu, menu)
        optionsMenu = menu!!

        //DEACTIVATE HISTORY BUTTON IF NO ENTRIES
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        optionsMenu.findItem(R.id.History).isEnabled = sharedPrefs.contains("entryHistory")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
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
        R.id.History -> {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("weightInput", weightInput.text.toString())
        outState.putString("heightInput", heightInput.text.toString())
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
            val (message, color) = Pair(BMI.toMessage(bmiValue), BMI.toColor(this, bmiValue))

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
            saveToHistory(weight, height, currUnits, bmiValue)
        }
        catch (e: IllegalArgumentException) {
            Snackbar.make(view, "Enter correct data", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun saveToHistory(weight: Double, height: Double, currUnits: String, bmiValue: Double) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        //RETRIEVE LIST OF ENTRIES FROM SETTINGS
        val serializedList = sharedPreferences.getString("entryHistory", "")
        val entryList = Gson().fromJson(serializedList) ?: LinkedList<HistoryEntry>()

        //ADD NEW ENTRY AND REMOVE EXCESS
        entryList.addFirst(HistoryEntry(weight, height, bmiValue, Calendar.getInstance().time, currUnits))
        if (entryList.size > 10) entryList.removeLast()

        //WRITE LIST BACK TO SHARED PREFERENCES
        with (sharedPreferences.edit()) {
            putString("entryHistory", Gson().toJson(entryList))
                .apply()
        }
    }

    fun onMoreInfoButtonClick(view: View) {
        val intent = Intent(this, MoreInfoActivity::class.java)
        intent.putExtra("bmiValue", bmiValue)
        intent.putExtra("bmiText", BMI.toMessage(bmiValue))
        startActivity(intent)
    }

    private var bmiValue = 0.0
    private var currUnits = "metric"

    private lateinit var optionsMenu: Menu
}
