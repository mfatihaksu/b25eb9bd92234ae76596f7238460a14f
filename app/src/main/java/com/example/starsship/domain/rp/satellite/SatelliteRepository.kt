package com.example.starsship.domain.rp.satellite

import com.example.starsship.data.Satellite
import com.example.starsship.domain.StarShipsResult

interface SatelliteRepository {

    suspend fun getSatellites() : StarShipsResult<List<Satellite>>

    suspend fun getSatellite(id : Int) : StarShipsResult<Satellite>

    suspend fun getSatelliteFromDb(id : Int) : Satellite?

    suspend fun saveSatellite(satellite: Satellite)
}