package com.example.economiatecnologia.data.local.repository

import com.example.economiatecnologia.data.local.dao.WaterBillDao
import com.example.economiatecnologia.data.local.entity.WaterBillEntity

class WaterBillRepository(private val waterBillDao: WaterBillDao) : WaterBillRepositoryImpl {
    override suspend fun insertWaterBill(value: Double, date: String) {
        val waterBill = WaterBillEntity(
            value = value,
            date = date
        )

        waterBillDao.insertWaterBill(waterBill)
    }

    override suspend fun deleteWaterBill(existingId: Long) {
        val waterBillToDelete = waterBillDao.getWaterBillById(existingId)

        return waterBillDao.deleteWaterBill(waterBillToDelete)
    }

    override suspend fun editWaterBill(existingId: Long, newValue: Double, newDate: String) {
        val waterBillToUpdate = WaterBillEntity(id = existingId, value = newValue, date = newDate)

        return waterBillDao.editWaterBill(waterBillToUpdate)
    }

    override suspend fun getAllWaterBill(): List<WaterBillEntity> {
        return waterBillDao.getAllWaterBills()
    }
}