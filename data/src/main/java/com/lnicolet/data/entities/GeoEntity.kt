package com.lnicolet.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geo_table")
data class GeoEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var lat: String,
    var lng: String
)