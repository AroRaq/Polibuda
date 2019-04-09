package com.example.gallery_app

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.lang.ClassCastException
import java.lang.IllegalStateException

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_newentry, null)
            builder.setView(view)
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
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private lateinit var listener: NewEntryDialogListener
}