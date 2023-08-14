package com.example.starsship.data

import com.squareup.moshi.Json

data class SatellitesResponse(
    @field:Json(name = "satellites") var satellites: Array<Satellite> = arrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SatellitesResponse
        if (!satellites.contentEquals(other.satellites)) return false
        return true
    }

    override fun hashCode(): Int {
        return satellites.contentHashCode()
    }
}
