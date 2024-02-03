package com.example.economiatecnologia.ui.fragments

import DecimalInputTextWatcher
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.economiatecnologia.R

class AddBillDialogFragment : DialogFragment() {
    interface AddBillListener {
        fun onBillAdded(value: Double, date: String)
    }

    private var listener: AddBillListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_add_bill_dialog, null)

            val editTextValue: EditText = view.findViewById(R.id.editTextValue)
            editTextValue.addTextChangedListener(DecimalInputTextWatcher(editTextValue))
            val editTextDate = view.findViewById<EditText>(R.id.editTextDate).text

            builder.setView(view)
                .setTitle("Adicionar nova conta")
                .setPositiveButton("Adicionar") { _, _ ->
                    val valueText = editTextValue.text.toString()
                    val cleanedValueText = valueText.replace("[^\\d.]".toRegex(), "")
                    val value = cleanedValueText.toDoubleOrNull() ?: 0.0
                    val finalValue = value / 100.0
                    val date = editTextDate.toString()

                    listener?.onBillAdded(finalValue, date)
                }
                .setNegativeButton("Cancelar") { _, _ ->
                    dialog?.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun setAddBillListener(listener: AddBillListener) {
        this.listener = listener
    }
}