package com.example.economiatecnologia.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.economiatecnologia.data.local.entity.WaterBillEntity

@Dao
interface WaterBillDao {
    // Essa consulta converte a string de data "05/2012" para "201205" e, em seguida, ordena numericamente em ordem decrescente.
    @Query("SELECT * FROM water_bills ORDER BY CAST(SUBSTR(date, 4) || SUBSTR(date, 0, 3) AS INTEGER) DESC")
    suspend fun getAllWaterBills(): List< WaterBillEntity>

    @Insert
    suspend fun insertWaterBill(waterBill:  WaterBillEntity)

    @Delete
    suspend fun deleteWaterBill(waterBill:  WaterBillEntity)

    @Update
    suspend fun editWaterBill(waterBill:  WaterBillEntity)

    @Query("SELECT * FROM water_bills WHERE id = :id")
    suspend fun getWaterBillById(id: Long): WaterBillEntity
}
