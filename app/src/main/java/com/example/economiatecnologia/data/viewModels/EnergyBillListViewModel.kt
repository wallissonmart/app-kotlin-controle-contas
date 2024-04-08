package com.example.economiatecnologia.data.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.economiatecnologia.data.local.AppDatabase
import com.example.economiatecnologia.data.local.entity.EnergyBillEntity
import com.example.economiatecnologia.data.local.repository.EnergyBillRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EnergyBillListViewModel(private val energyBillRepository: EnergyBillRepository) :
    ViewModel() {

    private val _energyBills = MutableLiveData<List<EnergyBillEntity>>()
    val energyBills: LiveData<List<EnergyBillEntity>> get() = _energyBills

    fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val energyBillsList = energyBillRepository.getAllEnergyBill()
            _energyBills.postValue(energyBillsList)
        }
    }

    fun insertEnergyBill(value: Double, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            energyBillRepository.insertEnergyBill(value, date)
            getAll()
        }
    }

    fun editEnergyBill(id: Long, value: Double, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            energyBillRepository.editEnergyBill(id, value, date)
            getAll()
        }
    }

    fun deleteEnergyBill(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            energyBillRepository.deleteEnergyBill(id)
            getAll()
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun createFactory(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val repo =
                        EnergyBillRepository(AppDatabase.getInstance(context).energyBillDao())
                    return EnergyBillListViewModel(repo) as T
                }
            }
        }
    }
}
