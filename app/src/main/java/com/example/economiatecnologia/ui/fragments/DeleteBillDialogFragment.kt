package com.example.economiatecnologia.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.economiatecnologia.R

class DeleteBillDialogFragment(private val idToDelete: Long) : DialogFragment() {
    interface DeleteBillListener {
        fun onBillDeleted(id: Long)
    }

    private var listener: DeleteBillListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_delete_bill_dialog, null)

            builder.setView(view)
                .setTitle("Confirmar exclusão")
                .setPositiveButton("Confirmar") { _, _ ->
                    listener?.onBillDeleted(idToDelete)
                }
                .setNegativeButton("Cancelar") { _, _ ->
                    dialog?.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun setDeleteBillListener(listener: DeleteBillListener) {
        this.listener = listener
    }
}