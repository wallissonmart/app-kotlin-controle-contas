package com.example.economiatecnologia.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.economiatecnologia.data.local.entity.WaterBillEntity

@Dao
interface WaterBillDao {
    @Query("SELECT * FROM water_bills")
    fun getAllWaterBills(): List<WaterBillEntity>

    @Insert
    fun insertWaterBill(waterBill: WaterBillEntity)
}
