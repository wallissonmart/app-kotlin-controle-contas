package com.example.economiatecnologia.ui.fragments

import DecimalInputTextWatcher
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.economiatecnologia.R

class EditBillDialogFragment(private val existingId: Long, private val oldValue: Double, private val oldDate: String) : DialogFragment() {
    interface UpdateBillListener {
        fun onBillUpdated(id: Long, value: Double, date: String)
    }

    private var listener: UpdateBillListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_edit_bill_dialog, null)

            val editTextValue: EditText = view.findViewById(R.id.editTextValue)
            editTextValue.addTextChangedListener(DecimalInputTextWatcher(editTextValue))
            editTextValue.setText(oldValue.toString())

            val editTextDate = view.findViewById<EditText>(R.id.editTextDate)
            editTextDate.setText(oldDate)

            builder.setView(view)
                .setTitle("Adicionar nova conta")
                .setPositiveButton("Adicionar") { _, _ ->
                    val valueText = editTextValue.text.toString()
                    val cleanedValueText = valueText.replace("[^\\d.]".toRegex(), "")
                    val value = cleanedValueText.toDoubleOrNull() ?: 0.0
                    val newValue = value / 100.0
                    val newDate = editTextDate.text.toString()

                    listener?.onBillUpdated(existingId, newValue, newDate)
                }
                .setNegativeButton("Cancelar") { _, _ ->
                    dialog?.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun setUpdateBillListener(listener: UpdateBillListener) {
        this.listener = listener
    }
}