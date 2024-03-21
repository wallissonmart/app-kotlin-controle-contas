package com.example.economiatecnologia.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.economiatecnologia.data.local.entity.EnergyBillEntity
import com.example.economiatecnologia.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EnergyBillListAdapter(private var energyBills: List<EnergyBillEntity>) :
    RecyclerView.Adapter<EnergyBillListAdapter.ViewHolder>() {

    interface EditIconClickListener {
        fun onEditIconClick(energyBill: EnergyBillEntity)
    }

    private var listener: EditIconClickListener? = null

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val energyBill = energyBills[position]
        holder.textValue.text = "R$ ${energyBill.value}"
        holder.textDate.text = energyBill.date

        holder.editIcon.setOnClickListener {
            val clickedItem = energyBill
            println(clickedItem)
            listener?.onEditIconClick(energyBill)
        }
    }


    override fun getItemCount(): Int {
        return energyBills.size
    }

    fun updateData(newEnergyBills: List<EnergyBillEntity>) {
        CoroutineScope(Dispatchers.Main).launch {
            energyBills = newEnergyBills
            notifyDataSetChanged()
        }
    }

    fun setOnEditIconClickListener(listener: EditIconClickListener) {
        this.listener = listener
    }
}
