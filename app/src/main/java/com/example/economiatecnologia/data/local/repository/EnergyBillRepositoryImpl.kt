package com.example.economiatecnologia.data.local.repository

import com.example.economiatecnologia.data.local.entity.EnergyBillEntity

interface EnergyBillRepositoryImpl {

    suspend fun getAllEnergyBill(): List<EnergyBillEntity>

    suspend fun insertEnergyBill(value: Double, date: String)

    suspend fun deleteEnergyBill(existingId: Long)

    suspend fun editEnergyBill(existingId: Long, newValue: Double, newDate: String)
}