package com.example.data.entities

import com.google.gson.annotations.SerializedName

data class UserEntity(
    val id: Int,
    val name: String,
    @SerializedName("username") val userName: String,
    val email: String,
    val address: AddressEntity,
    val phone: String,
    val website: String,
    val company: CompanyEntity
)