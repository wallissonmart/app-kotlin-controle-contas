package com.example.economiatecnologia.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.economiatecnologia.R
import com.example.economiatecnologia.data.local.entity.WaterBillEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WaterBillListAdapter(private var waterBills: List<WaterBillEntity>) :
    RecyclerView.Adapter<WaterBillListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textValue: TextView = itemView.findViewById(R.id.textValue)
        val textDate: TextView = itemView.findViewById(R.id.textDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bill_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val energyBill = waterBills[position]
        holder.textValue.text = "R$ ${energyBill.value}"
        holder.textDate.text =
            energyBill.date.toString()
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
}
