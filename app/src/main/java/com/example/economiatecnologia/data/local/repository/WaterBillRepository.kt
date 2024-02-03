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

    override suspend fun getAllWaterBill(): List<WaterBillEntity> {
        return waterBillDao.getAllWaterBills()
    }
}