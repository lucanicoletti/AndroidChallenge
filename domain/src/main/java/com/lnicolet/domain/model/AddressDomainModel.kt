package com.lnicolet.domain.model

data class AddressDomainModel(
    val street: String,
    val suite: String,
    val city: String,
    val zipCode: String,
    val geo: GeoDomainModel
)