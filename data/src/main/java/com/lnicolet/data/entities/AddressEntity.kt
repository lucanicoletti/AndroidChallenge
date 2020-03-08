package com.lnicolet.data.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "address_table")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var street: String,
    var suite: String,
    var city: String,
    @SerializedName("zipcode") var zipCode: String,
    @Ignore var geo: GeoEntity? = null
) {
    constructor (
        id: Int,
        street: String,
        suite: String,
        city: String,
        zipCode: String
    ) : this(id, street, suite, city, zipCode, null)
}