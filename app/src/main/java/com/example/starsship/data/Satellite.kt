package com.example.starsship.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "Satellites")
data class Satellite(
    @PrimaryKey
    @field:Json(name = "id") val id: Int,

    @ColumnInfo(name = "active")
    @field:Json(name = "active") val active: Boolean?,

    @ColumnInfo(name = "name")
    @field:Json(name = "name") var name: String?,

    @ColumnInfo("costPerLaunch")
    @field:Json(name = "cost_per_launch") val costPerLaunch: Int?,

    @ColumnInfo(name = "firstFlight")
    @field:Json(name = "first_flight") var firstFlight: String?,

    @ColumnInfo(name = "height")
    @field:Json(name = "height") val height: Int?,

    @ColumnInfo(name = "mass")
    @field:Json(name = "mass") val mass: Int?
)
