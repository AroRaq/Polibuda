package com.example.bmi_app.`class`

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bmi_app.R
import com.example.bmi_app.logic.BMI
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class HistoryAdapter (private val context: Context, val data: () -> LinkedList<HistoryEntry>)
    :RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val weightText: TextView
        val heightText: TextView
        val dateText: TextView
        val bmiText: TextView
        val constraintLayout: ConstraintLayout

        init {
            view.setOnClickListener { Log.d("HistoryViewHolder", "Element $adapterPosition clicked")}
            weightText = view.findViewById(R.id.weightView)
            heightText = view.findViewById(R.id.heightView)
            dateText = view.findViewById(R.id.dateView)
            bmiText = view.findViewById(R.id.bmiView)
            constraintLayout = view.findViewById(R.id.historyentry_constraintLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_historyentry, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return HistoryViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val entries = data()
        val units = entries[position].units
        val height = context.resources.getString(when (units) {
            "imperial" ->   R.string.height_imperial
            else ->         R.string.height_metric
        })
        val weight = context.resources.getString(when (units) {
            "imperial" ->   R.string.weight_imperial
            else ->         R.string.weight_metric
        })
        holder.heightText.text = "$height: \t${entries[position].height}"
        holder.weightText.text = "$weight: \t${entries[position].weight}"

        context.resources.getString(R.string.height_imperial)
        holder.dateText.text = "Date: ${SimpleDateFormat("dd/MM/yyy").format(entries[position].date)}"
        holder.bmiText.text = String.format("%.2f", entries[position].bmiValue)
        val color = BMI.toColor(holder.heightText.context, entries[position].bmiValue)
        val g = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            orientation = GradientDrawable.Orientation.LEFT_RIGHT
            colors = intArrayOf(
                ContextCompat.getColor(context, R.color.colorBackground),
                color,
                color
            )
        }
        holder.constraintLayout.background = g
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data().size
}
