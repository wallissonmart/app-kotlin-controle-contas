package com.example.economiatecnologia.data.local.repository

import com.example.economiatecnologia.data.local.entity.WaterBillEntity

interface WaterBillRepositoryImpl {

    suspend fun getAllWaterBill(): List<WaterBillEntity>

    suspend fun insertWaterBill(value: Double, date: String)

    suspend fun deleteWaterBill(existingId: Long)

    suspend fun editWaterBill(existingId: Long, newValue: Double, newDate: String)
}