package com.example.economiatecnologia.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.economiatecnologia.R
import com.example.economiatecnologia.data.local.entity.WaterBillEntity
import com.example.economiatecnologia.data.viewModels.WaterBillListViewModel
import com.example.economiatecnologia.ui.adapters.WaterBillListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WaterBillListFragment : Fragment(), AddBillDialogFragment.AddBillListener,
    EditBillDialogFragment.UpdateBillListener, DeleteBillDialogFragment.DeleteBillListener,
    WaterBillListAdapter.EditIconClickListener, WaterBillListAdapter.DeleteIconClickListener {

    private lateinit var waterBillListViewModel: WaterBillListViewModel
    private lateinit var waterBillAdapter: WaterBillListAdapter
    private lateinit var fabAddBill: FloatingActionButton
    private lateinit var messageListEmpty: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_water_bill_list, container, false)

        setupViewModel()
        setupRecyclerView(view)
        setupAddBillButton(view)

        return view
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewWaterBill)
        waterBillAdapter = WaterBillListAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = waterBillAdapter

        waterBillAdapter.setOnEditIconClickListener(this)
        waterBillAdapter.setOnDeleteIconClickListener(this)
    }

    private fun setupAddBillButton(view: View) {
        fabAddBill = view.findViewById(R.id.fabAddWaterBill)
        fabAddBill.setOnClickListener {
            showAddBillDialog()
        }
    }

    private fun showAddBillDialog() {
        val dialogFragment = AddBillDialogFragment()
        dialogFragment.setAddBillListener(this)
        dialogFragment.show(requireActivity().supportFragmentManager, "AddBillDialog")
    }

    private fun showEditBillDialog(item: WaterBillEntity) {
        val dialogFragment = EditBillDialogFragment(item.id, item.value, item.date)
        dialogFragment.setUpdateBillListener(this)
        dialogFragment.show(requireActivity().supportFragmentManager, "EditBillDialog")
    }

    private fun showDeleteBillDialog(id: Long) {
        val dialogFragment = DeleteBillDialogFragment(id)
        dialogFragment.setDeleteBillListener(this)
        dialogFragment.show(requireActivity().supportFragmentManager, "DeleteBillDialog")
    }


    private fun setupViewModel() {
        waterBillListViewModel = ViewModelProvider(
            this,
            WaterBillListViewModel.createFactory(requireContext())
        ).get(WaterBillListViewModel::class.java)

        waterBillListViewModel.getAll()

        waterBillListViewModel.waterBills.observe(viewLifecycleOwner) { waterBills ->
            waterBills?.let {
                waterBillAdapter.updateData(it)
                showMessageListEmpty(it.size)
            }
        }
    }

    override fun onBillAdded(value: Double, date: String) {
        waterBillListViewModel.insertWaterBill(value, date)
    }

    override fun onBillUpdated(id: Long, value: Double, date: String) {
        waterBillListViewModel.editWaterBill(id, value, date)
    }

    override fun onBillDeleted(id: Long) {
        waterBillListViewModel.deleteWaterBill(id)
    }

    override fun onEditIconClick(waterBill: WaterBillEntity) {
        showEditBillDialog(waterBill)
    }

    override fun onDeleteIconClick(id: Long) {
        showDeleteBillDialog(id)
    }

    private fun showMessageListEmpty(listSize: Int) {
        messageListEmpty = requireView().findViewById(R.id.messageListWaterEmpty)

        if (listSize == 0) {
            messageListEmpty.visibility = View.VISIBLE
            return
        }

        if (listSize >= 1) {
            messageListEmpty.visibility = View.INVISIBLE
        }
    }
}