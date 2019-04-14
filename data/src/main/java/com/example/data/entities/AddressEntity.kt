package com.example.data.entities

import com.google.gson.annotations.SerializedName

data class AddressEntity(
    val street: String,
    val suite: String,
    val city: String,
    @SerializedName("zipcode") val zipCode: String,
    val geo: GeoEntity
)