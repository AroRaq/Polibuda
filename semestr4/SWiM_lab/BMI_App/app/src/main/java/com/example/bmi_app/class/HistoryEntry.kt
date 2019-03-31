package com.example.bmi_app.`class`

import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

data class HistoryEntry(val weight: Double, val height: Double, val bmiValue: Double, val date: Date, val units: String) : Serializable