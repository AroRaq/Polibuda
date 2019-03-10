package com.example.bmi_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.example.bmi_app.logic.BMI_kgcm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        return true
    }
    */

    public fun onCalculateClick(view: View) {
        var weight = weightInput.text.toString().toDouble()
        var height = heightInput.text.toString().toDouble()
        var calculator = BMI_kgcm(weight, height)
        var bmi = calculator.calcBMI()
        bmiText.text = bmi.toString()
    }
}
