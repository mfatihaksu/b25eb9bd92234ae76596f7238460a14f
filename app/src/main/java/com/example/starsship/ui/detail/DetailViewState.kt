package com.example.starsship.ui.detail

import com.example.starsship.data.Position
import com.example.starsship.data.Satellite
import com.example.starsship.data.SatellitePositions

data class DetailViewState(val satellite : Satellite? = null, val satellitePositions : SatellitePositions? = null, val currentPosition : Position? = null, val currentPositionIndex :Int = -1, val isLoading : Boolean = false)