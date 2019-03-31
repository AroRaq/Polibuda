package com.example.bmi_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_moreinfo.*

class MoreInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moreinfo)

        val bundle = intent.extras
        bmiValue = bundle!!.getDouble("bmiValue")
        val bmiText = bundle.getString("bmiText")

        textView.text = String.format("Your BMI is %.2f", bmiValue)
        descriptionText.text = when(bmiText) {
            "Underweight" -> resources.getString(R.string.description_underweight)
            "Normal" -> resources.getString(R.string.description_normal)
            "Overweight" -> resources.getString(R.string.description_overweight)
            "Obese" -> resources.getString(R.string.description_obese)
            "Extremely Obese" -> resources.getString(R.string.description_extremelyobese)
            else -> "Oops"
        }
    }

    var bmiValue = 0.0
}
