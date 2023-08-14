package com.example.starsship.domain.rp.position

import com.example.starsship.data.SatellitePositions
import com.example.starsship.domain.StarShipsResult

interface PositionRepository {

    suspend fun getPositions(id : Int) : StarShipsResult<SatellitePositions?>
}