package com.example.gallery_app.mainactivity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.gallery_app.R
import java.lang.ClassCastException
import java.lang.IllegalStateException
import java.util.*

class NewEntryDialogFragment : DialogFragment() {

    interface NewEntryDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, bundle: Bundle)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NewEntryDialogListener
        } catch(e: ClassCastException) {
            throw ClassCastException((context.toString() + "must implement NewEntryDialogListener"))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let{
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_newentry, null)

            builder
                .setView(view)
                .setPositiveButton("Confirm") { _, _ ->
                    val bundle = Bundle().apply {
                        putCharSequence("Title", view.findViewById<EditText>(R.id.editText_imagetitle).text.toString())
                        putCharSequence("Tags", view.findViewById<EditText>(R.id.editText_imagetags).text.toString())
                        putCharSequence("Date", view.findViewById<EditText>(R.id.editText_imagedate).text.toString())
                        putCharSequence("Url", view.findViewById<EditText>(R.id.editText_imageurl).text.toString())
                    }
                    listener.onDialogPositiveClick(this, bundle)
                }
                .setNegativeButton("Cancel") {_, _ ->
                    listener.onDialogNegativeClick(this)
                }

            view.findViewById<EditText>(R.id.editText_imagedate).apply {
                inputType = InputType.TYPE_NULL
                setOnClickListener {
                    val cal = Calendar.getInstance()
                    val day = cal.get(Calendar.DAY_OF_MONTH)
                    val month = cal.get(Calendar.MONTH)
                    val year = cal.get(Calendar.YEAR)
                    val picker = DatePickerDialog(view.context,
                        {_, y, m, d -> this.setText("$d-${m+1}-$y")},
                        year, month, day
                    )
                    picker.show()
                }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private lateinit var listener: NewEntryDialogListener
}