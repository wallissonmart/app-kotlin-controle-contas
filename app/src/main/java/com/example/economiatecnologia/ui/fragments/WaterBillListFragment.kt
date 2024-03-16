package com.example.economiatecnologia.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.economiatecnologia.R
import com.example.economiatecnologia.data.viewModels.WaterBillListViewModel
import com.example.economiatecnologia.ui.adapters.WaterBillListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WaterBillListFragment : Fragment(), AddBillDialogFragment.AddBillListener {

    private lateinit var waterBillListViewModel: WaterBillListViewModel
    private lateinit var waterBillAdapter: WaterBillListAdapter
    private lateinit var fabAddBill: FloatingActionButton

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

    private fun setupViewModel() {
        waterBillListViewModel = ViewModelProvider(
            this,
            WaterBillListViewModel.createFactory(requireContext())
        ).get(WaterBillListViewModel::class.java)

        waterBillListViewModel.getAll()

        waterBillListViewModel.waterBills.observe(viewLifecycleOwner) { waterBills ->
            waterBills?.let {
                waterBillAdapter.updateData(it)
            }
        }
    }

    override fun onBillAdded(value: Double, date: String) {
        waterBillListViewModel.insertWaterBill(value, date)
    }
}