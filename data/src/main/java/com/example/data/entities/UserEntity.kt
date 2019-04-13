package com.example.data.entities

data class UserEntity(
    val id: Int,
    val name: String,
    val userName: String,
    val email: String,
    val address: AddressEntity,
    val phone: String,
    val website: String,
    val company: CompanyEntity
)