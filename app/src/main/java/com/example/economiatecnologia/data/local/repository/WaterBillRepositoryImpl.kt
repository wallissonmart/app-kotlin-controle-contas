package com.example.economiatecnologia.data.local.repository

import androidx.lifecycle.LiveData
import com.example.economiatecnologia.data.local.entity.WaterBillEntity

interface WaterBillRepositoryImpl {

    suspend fun insertWaterBill(value: Double, date: String)

    suspend fun getAllWaterBill(): List<WaterBillEntity>
}