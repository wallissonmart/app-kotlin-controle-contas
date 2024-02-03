package com.example.economiatecnologia.data.local.repository

import com.example.economiatecnologia.data.local.dao.EnergyBillDao
import com.example.economiatecnologia.data.local.entity.EnergyBillEntity

class EnergyBillRepository(private val energyBillDao: EnergyBillDao) : EnergyBillRepositoryImpl {
    override suspend fun insertEnergyBill(value: Double, date: String) {
        val energyBill = EnergyBillEntity(
            value = value,
            date = date
        )

        energyBillDao.insertEnergyBill(energyBill)
    }

    override suspend fun getAllEnergyBill(): List<EnergyBillEntity> {
        return energyBillDao.getAllEnergyBills()
    }
}