package com.example.economiatecnologia.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.economiatecnologia.R
import com.example.economiatecnologia.data.local.entity.WaterBillEntity
import com.example.economiatecnologia.util.CurrencyFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WaterBillListAdapter(private var waterBills: List<WaterBillEntity>) :
    RecyclerView.Adapter<WaterBillListAdapter.ViewHolder>() {

    interface EditIconClickListener {
        fun onEditIconClick(waterBill: WaterBillEntity)
    }

    interface DeleteIconClickListener {
        fun onDeleteIconClick(id: Long)
    }


    private var listenerEditIcon: WaterBillListAdapter.EditIconClickListener? = null
    private var listenerDeleteIcon: WaterBillListAdapter.DeleteIconClickListener? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textValue: TextView = itemView.findViewById(R.id.textValue)
        val textDate: TextView = itemView.findViewById(R.id.textDate)

        val editIcon: ImageView = itemView.findViewById(R.id.editIcon)
        val deleteIcon: ImageView = itemView.findViewById(R.id.deleteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bill_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: WaterBillListAdapter.ViewHolder, position: Int) {
        val waterBill = waterBills[position]
        holder.textValue.text = CurrencyFormatter.formatCurrency(waterBill.value)
        holder.textDate.text = waterBill.date

        holder.editIcon.setOnClickListener {
            listenerEditIcon?.onEditIconClick(waterBill)
        }

        holder.deleteIcon.setOnClickListener {
            listenerDeleteIcon?.onDeleteIconClick(waterBill.id)
        }
    }

    override fun getItemCount(): Int {
        return waterBills.size
    }

    fun updateData(newWaterBills: List<WaterBillEntity>) {
        CoroutineScope(Dispatchers.Main).launch {
            waterBills = newWaterBills
            notifyDataSetChanged()
        }
    }

    fun setOnEditIconClickListener(listener: WaterBillListAdapter.EditIconClickListener) {
        this.listenerEditIcon = listener
    }

    fun setOnDeleteIconClickListener(listener: WaterBillListAdapter.DeleteIconClickListener) {
        this.listenerDeleteIcon = listener
    }
}
