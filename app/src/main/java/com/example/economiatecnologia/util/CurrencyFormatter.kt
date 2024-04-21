package com.example.economiatecnologia.util

import java.text.NumberFormat
import java.util.Locale

class CurrencyFormatter {
    companion object {
        fun formatCurrency(val: Double): String {
            val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            return format.format(val)
        }
    }
}