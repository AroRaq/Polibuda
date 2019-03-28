package com.example.bmi_app.logic

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.bmi_app.R

interface BMI {
    fun calcBMI() : Double

    companion object {

        fun toColor(context: Context, bmi: Double): Int {
            return ContextCompat.getColor(context, when {
                bmi < 18.5 -> R.color.lapislazuli
                bmi < 25 -> R.color.grynszpan
                bmi < 30 -> R.color.orange
                bmi < 35 -> R.color.rozpompejanski
                else -> R.color.violet
            })
        }

        fun toMessage(bmi: Double): String {
            return when {
                bmi < 18.5 -> "Underweight"
                bmi < 25 -> "Normal"
                bmi < 30 -> "Overweight"
                bmi < 35 -> "Obese"
                else -> "Extremely Obese"
            }
        }
    }
}

