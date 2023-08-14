package com.example.starsship.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.starsship.data.Satellite
import com.example.starsship.domain.dao.SatelliteDao

@Database(entities = [Satellite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun satelliteDao(): SatelliteDao
}