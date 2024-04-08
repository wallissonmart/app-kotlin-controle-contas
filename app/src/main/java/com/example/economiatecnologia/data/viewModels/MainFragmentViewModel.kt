package com.example.economiatecnologia.data.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.economiatecnologia.data.local.AppDatabase
import com.example.economiatecnologia.data.local.repository.EnergyBillRepository
import com.example.economiatecnologia.data.local.repository.WaterBillRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragmentViewModel(
    private val waterBillRepository: WaterBillRepository,
    private val energyBillRepository: EnergyBillRepository
) : ViewModel() {

    private val _averageWaterValueLiveData = MutableLiveData<Double>()
    val averageWaterValueLiveData: LiveData<Double>
        get() = _averageWaterValueLiveData

    private val _averageEnergyValueLiveData = MutableLiveData<Double>()
    val averageEnergyValueLiveData: LiveData<Double>
        get() = _averageEnergyValueLiveData

    fun calculateAverageWaterValue() {
        viewModelScope.launch(Dispatchers.IO) {
            val waterBillsList = waterBillRepository.getAllWaterBill()
            val waterValuesList = waterBillsList.map { it.value }
            val averageValue = waterValuesList.average()

            if (waterValuesList.isNotEmpty()) {
                _averageWaterValueLiveData.postValue(averageValue)

            } else {
                _averageWaterValueLiveData.postValue(0.0)
            }
        }
    }

    fun calculateAverageEnergyValue() {
        viewModelScope.launch(Dispatchers.IO) {
            val energyBillsList = energyBillRepository.getAllEnergyBill()
            val energyValuesList = energyBillsList.map { it.value }
            val averageValue = energyValuesList.average()
            _averageEnergyValueLiveData.postValue(averageValue)

            if (energyValuesList.isNotEmpty()) {
                _averageEnergyValueLiveData.postValue(averageValue)

            } else {
                _averageEnergyValueLiveData.postValue(0.0)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun createFactory(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val energyRepo =
                        EnergyBillRepository(AppDatabase.getInstance(context).energyBillDao())
                    val waterRepo =
                        WaterBillRepository(AppDatabase.getInstance(context).waterBillDao())
                    return MainFragmentViewModel(waterRepo, energyRepo) as T
                }
            }
        }
    }
}
