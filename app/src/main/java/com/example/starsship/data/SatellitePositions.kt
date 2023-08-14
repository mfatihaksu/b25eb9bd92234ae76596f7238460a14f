package com.example.starsship.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SatellitePositions(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "positions") val positions: List<Position>?
)
