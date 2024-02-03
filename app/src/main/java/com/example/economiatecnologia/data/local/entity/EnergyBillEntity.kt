package com.example.economiatecnologia.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "energy_bills")
data class EnergyBillEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val value: Double,
    val date: String
)
