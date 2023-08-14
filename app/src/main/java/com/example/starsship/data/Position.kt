package com.example.starsship.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Position(
    @field:Json(name = "posX") val posX : Double?,
    @field:Json(name = "posY") val posY : Double?
)
