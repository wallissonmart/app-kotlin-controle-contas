package com.example.economiatecnologia.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.economiatecnologia.data.local.entity.EnergyBillEntity

@Dao
interface EnergyBillDao {
    // Essa consulta converte a string de data "05/2012" para "201205" e, em seguida, ordena numericamente em ordem decrescente.
    @Query("SELECT * FROM energy_bills ORDER BY CAST(SUBSTR(date, 4) || SUBSTR(date, 0, 3) AS INTEGER) DESC")
    suspend fun getAllEnergyBills(): List<EnergyBillEntity>

    @Insert
    suspend fun insertEnergyBill(energyBill: EnergyBillEntity)

    @Delete
    suspend fun deleteEnergyBill(energyBill: EnergyBillEntity)

    @Update
    suspend fun editEnergyBill(energyBill: EnergyBillEntity)

    @Query("SELECT * FROM energy_bills WHERE id = :id")
    suspend fun getEnergyBillById(id: Long): EnergyBillEntity
}
