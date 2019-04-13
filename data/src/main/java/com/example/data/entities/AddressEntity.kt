package com.example.data.entities

data class AddressEntity(
    val street: String,
    val suite: String,
    val city: String,
    val zipCode: String,
    val geo: GeoEntity
)