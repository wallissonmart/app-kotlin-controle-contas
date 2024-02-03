package com.example.economiatecnologia.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.economiatecnologia.data.local.dao.EnergyBillDao
import com.example.economiatecnologia.data.local.dao.WaterBillDao
import com.example.economiatecnologia.data.local.entity.EnergyBillEntity
import com.example.economiatecnologia.data.local.entity.WaterBillEntity

@Database(entities = [EnergyBillEntity::class, WaterBillEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun energyBillDao(): EnergyBillDao
    abstract fun waterBillDao(): WaterBillDao

    companion object {

        private const val DATABASE_NAME: String = "app-economia-tecnologia-db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                .build()
    }
}

