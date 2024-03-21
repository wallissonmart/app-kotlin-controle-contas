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

    override suspend fun deleteEnergyBill(existingId: Long) {
        val energyBillToDelete = energyBillDao.getEnergyBillById(existingId)

        return energyBillDao.deleteEnergyBill(energyBillToDelete)
    }

    override suspend fun editEnergyBill(existingId: Long, newValue: Double, newDate: String) {
        val energyBillToUpdate = EnergyBillEntity(id = existingId, value = newValue, date = newDate)

        return energyBillDao.editEnergyBill(energyBillToUpdate)
    }

    override suspend fun getAllEnergyBill(): List<EnergyBillEntity> {
        return energyBillDao.getAllEnergyBills()
    }
}