package com.lnicolet.data.entities

import com.google.gson.annotations.SerializedName

data class UserEntity(
    var id: Int,
    var name: String,
    @SerializedName("username") var userName: String,
    var email: String,
    var address: AddressEntity?,
    var phone: String,
    var website: String,
    var company: CompanyEntity?
)