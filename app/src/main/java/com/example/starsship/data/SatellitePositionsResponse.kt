package com.example.starsship.data

import com.squareup.moshi.Json

data class SatellitePositionsResponse(
    @field:Json(name = "list") var list : Array<SatellitePositions> = arrayOf()
){
    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SatellitePositionsResponse
        if (!list.contentEquals(other.list)) return false
        return true
    }

    override fun hashCode(): Int {
        return list.contentHashCode()
    }
}