package com.example.economiatecnologia.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.economiatecnologia.R
import com.example.economiatecnologia.data.viewModels.MainFragmentViewModel
import com.example.economiatecnologia.ui.activities.MainActivity
import com.example.economiatecnologia.util.CurrencyFormatter

class MainFragment : Fragment() {

    private lateinit var averageWaterValue: TextView
    private lateinit var averageEnergyValue: TextView
    private lateinit var mainFragmentViewModel: MainFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        view.findViewById<Button>(R.id.buttonWater).setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(WaterBillListFragment())
        }

        view.findViewById<Button>(R.id.buttonEnergy).setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(EnergyBillListFragment())
        }

        setupViewModel()
        setupMainFragment(view)

        return view
    }


    private fun setupMainFragment(view: View) {
        getAverageValues()

        averageWaterValue = view.findViewById(R.id.averageWaterValue)
        averageEnergyValue = view.findViewById(R.id.averageEnergyValue)

        mainFragmentViewModel.averageWaterValueLiveData.observe(viewLifecycleOwner) { waterAverageValue ->
            waterAverageValue?.let {
                averageWaterValue.text = CurrencyFormatter.formatCurrency(it)
            }
        }

        mainFragmentViewModel.averageEnergyValueLiveData.observe(viewLifecycleOwner) { energyAverageValue ->
            energyAverageValue?.let {
                averageEnergyValue.text = CurrencyFormatter.formatCurrency(it)
            }
        }
    }

    private fun setupViewModel() {
        mainFragmentViewModel = ViewModelProvider(
            this,
            MainFragmentViewModel.createFactory(requireContext())
        ).get(MainFragmentViewModel::class.java)
    }

    private fun getAverageValues() {
        mainFragmentViewModel.calculateAverageWaterValue()
        mainFragmentViewModel.calculateAverageEnergyValue()
    }
}