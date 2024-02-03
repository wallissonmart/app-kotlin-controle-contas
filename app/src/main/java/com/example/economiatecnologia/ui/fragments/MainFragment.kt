package com.example.economiatecnologia.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.economiatecnologia.R
import com.example.economiatecnologia.ui.activities.MainActivity

class MainFragment : Fragment() {

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

        return view
    }
}