package com.example.economiatecnologia.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.economiatecnologia.R
import com.example.economiatecnologia.data.local.entity.EnergyBillEntity
import com.example.economiatecnologia.data.viewModels.EnergyBillListViewModel
import com.example.economiatecnologia.ui.adapters.EnergyBillListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EnergyBillListFragment : Fragment(), AddBillDialogFragment.AddBillListener,
    EditBillDialogFragment.UpdateBillListener, EnergyBillListAdapter.EditIconClickListener {

    private lateinit var energyBillListViewModel: EnergyBillListViewModel
    private lateinit var energyBillAdapter: EnergyBillListAdapter
    private lateinit var fabAddBill: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_energy_bill_list, container, false)

        setupViewModel()
        setupRecyclerView(view)
        setupAddBillButton(view)

        return view
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewEnergyBill)
        energyBillAdapter = EnergyBillListAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = energyBillAdapter

        energyBillAdapter.setOnEditIconClickListener(this)
    }

    private fun setupAddBillButton(view: View) {
        fabAddBill = view.findViewById(R.id.fabAddEnergyBill)
        fabAddBill.setOnClickListener {
            showAddBillDialog()
        }
    }

    private fun showAddBillDialog() {
        val dialogFragment = AddBillDialogFragment()
        dialogFragment.setAddBillListener(this)
        dialogFragment.show(requireActivity().supportFragmentManager, "AddBillDialog")
    }

    private fun showEditBillDialog(item: EnergyBillEntity) {
        val dialogFragment = EditBillDialogFragment(item.id, item.value, item.date)
        dialogFragment.setUpdateBillListener(this)
        dialogFragment.show(requireActivity().supportFragmentManager, "EditBillDialog")
    }

    private fun setupViewModel() {
        energyBillListViewModel = ViewModelProvider(
            this,
            EnergyBillListViewModel.createFactory(requireContext())
        ).get(EnergyBillListViewModel::class.java)

        energyBillListViewModel.getAll()

        energyBillListViewModel.energyBills.observe(viewLifecycleOwner) { energyBills ->
            energyBills?.let {
                energyBillAdapter.updateData(it)
            }
        }
    }

    override fun onBillAdded(value: Double, date: String) {
        energyBillListViewModel.insertEnergyBill(value, date)
    }

    override fun onBillUpdated(id: Long, value: Double, date: String) {
        energyBillListViewModel.editEnergyBill(id, value, date)
    }

    override fun onEditIconClick(energyBill: EnergyBillEntity) {
       println("oneditclick")
    }
}
