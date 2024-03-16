package com.example.economiatecnologia.data.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.economiatecnologia.data.local.AppDatabase
import com.example.economiatecnologia.data.local.entity.WaterBillEntity
import com.example.economiatecnologia.data.local.repository.WaterBillRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WaterBillListViewModel(private val waterBillRepository: WaterBillRepository) : ViewModel() {

    private val _waterBills = MutableLiveData<List<WaterBillEntity>>()
    val waterBills: LiveData<List<WaterBillEntity>> get() = _waterBills

    fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val waterBillsList = waterBillRepository.getAllWaterBill()
            _waterBills.postValue(waterBillsList)
        }
    }

    fun insertWaterBill(value: Double, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            waterBillRepository.insertWaterBill(value, date)
            getAll()
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun createFactory(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val repo =
                        WaterBillRepository(AppDatabase.getInstance(context).waterBillDao())
                    return WaterBillListViewModel(repo) as T
                }
            }
        }
    }
}