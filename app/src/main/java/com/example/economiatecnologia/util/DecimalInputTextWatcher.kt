import android.icu.math.BigDecimal
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.util.Log
import java.text.NumberFormat
import java.util.*

class DecimalInputTextWatcher(private val editText: EditText) : TextWatcher {

    companion object {
        private const val replaceRegex: String = "[R$,.\u00A0]"
    }

    override fun beforeTextChanged(
        charSequence: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable?) {
        val stringEditable = editable.toString()

        if (stringEditable.isEmpty()) return

        editText.removeTextChangedListener(this)

        try {
            val cleanString = stringEditable.replace(replaceRegex.toRegex(), "")
            val parsed = BigDecimal(cleanString)
                .setScale(2, BigDecimal.ROUND_FLOOR)
                .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)
            val decimalFormat =
                NumberFormat.getCurrencyInstance(Locale("pt", "BR")) as NumberFormat
            val formatted = decimalFormat.format(parsed)

            editText.setText(formatted)
            editText.setSelection(formatted.length)
        } catch (e: Exception) {
            Log.e("ERROR", e.toString())
        }

        editText.addTextChangedListener(this)
    }
}
