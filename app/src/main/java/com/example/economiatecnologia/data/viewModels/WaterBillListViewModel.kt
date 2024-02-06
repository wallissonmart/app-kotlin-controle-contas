package com.example.economiatecnologia.data.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.economiatecnologia.data.local.entity.WaterBillEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WaterBillListViewModel : ViewModel() {

    private val _waterBills = MutableLiveData<List<WaterBillEntity>>()
    val waterBills: LiveData<List<WaterBillEntity>> get() = _waterBills

    fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val energyBillsList = waterBillRepository.getAllWaterBill()
            _waterBills.postValue(energyBillsList)
        }
    }

    fun insertEnergyBill(value: Double, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            waterBillRepository.insertWaterBill(value, date)
            getAll()
        }
    }
}