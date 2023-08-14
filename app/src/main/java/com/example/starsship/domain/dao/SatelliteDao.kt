package com.example.starsship.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.starsship.data.Satellite

@Dao
interface SatelliteDao {

    @Insert
    fun insert(satellite: Satellite)

    @Query("Select * from Satellites where id=:id")
    fun getSatellite(id : Int) : Satellite?
}