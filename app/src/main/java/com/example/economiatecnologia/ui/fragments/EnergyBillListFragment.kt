package com.example.economiatecnologia.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.economiatecnologia.R
import com.example.economiatecnologia.data.local.entity.EnergyBillEntity
import com.example.economiatecnologia.data.viewModels.EnergyBillListViewModel
import com.example.economiatecnologia.ui.adapters.EnergyBillListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EnergyBillListFragment : Fragment(), AddBillDialogFragment.AddBillListener,
    EditBillDialogFragment.UpdateBillListener, DeleteBillDialogFragment.DeleteBillListener,
    EnergyBillListAdapter.EditIconClickListener, EnergyBillListAdapter.DeleteIconClickListener {

    private lateinit var energyBillListViewModel: EnergyBillListViewModel
    private lateinit var energyBillAdapter: EnergyBillListAdapter
    private lateinit var fabAddBill: FloatingActionButton
    private lateinit var messageListEmpty: TextView

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
        energyBillAdapter.setOnDeleteIconClickListener(this)
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

    private fun showDeleteBillDialog(id: Long) {
        val dialogFragment = DeleteBillDialogFragment(id)
        dialogFragment.setDeleteBillListener(this)
        dialogFragment.show(requireActivity().supportFragmentManager, "DeleteBillDialog")
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
                showMessageListEmpty(it.size)
            }
        }
    }

    override fun onBillAdded(value: Double, date: String) {
        energyBillListViewModel.insertEnergyBill(value, date)
    }

    override fun onBillUpdated(id: Long, value: Double, date: String) {
        energyBillListViewModel.editEnergyBill(id, value, date)
    }

    override fun onBillDeleted(id: Long) {
        energyBillListViewModel.deleteEnergyBill(id)
    }

    override fun onEditIconClick(energyBill: EnergyBillEntity) {
        showEditBillDialog(energyBill)
    }

    override fun onDeleteIconClick(id: Long) {
        showDeleteBillDialog(id)
    }

    private fun showMessageListEmpty(listSize: Int) {
        messageListEmpty = requireView().findViewById(R.id.messageListEnergyEmpty)

        if (listSize == 0) {
            messageListEmpty.visibility = View.VISIBLE
            return
        }

        if (listSize >= 1) {
            messageListEmpty.visibility = View.INVISIBLE
        }
    }
}
