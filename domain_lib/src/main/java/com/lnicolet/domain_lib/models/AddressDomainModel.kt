package com.lnicolet.domain_lib.models

data class AddressDomainModel(
    val street: String,
    val suite: String,
    val city: String,
    val zipCode: String,
    val geo: GeoDomainModel?
)