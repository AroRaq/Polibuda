package com.example.bmi_app.`class`

import java.io.Serializable
import java.util.*

data class HistoryEntry(val height: Double, val weight: Double, val bmiValue: Double, val date: Date) : Serializable