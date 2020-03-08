package com.lnicolet.domain.models

class UserDomainModel(
    val id: Int,
    val name: String,
    val userName: String,
    val email: String,
    val address: AddressDomainModel?,
    val phone: String,
    val website: String,
    val company: CompanyDomainModel?,
    val imageUrl: String
)