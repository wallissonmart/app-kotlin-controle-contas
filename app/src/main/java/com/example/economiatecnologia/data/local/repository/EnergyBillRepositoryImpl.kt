package com.example.economiatecnologia.data.local.repository

import com.example.economiatecnologia.data.local.entity.EnergyBillEntity

interface EnergyBillRepositoryImpl {

    suspend fun insertEnergyBill(value: Double, date: String)

    suspend fun getAllEnergyBill(): List<EnergyBillEntity>
}