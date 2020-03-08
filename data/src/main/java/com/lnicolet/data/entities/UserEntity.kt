package com.lnicolet.data.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey var id: Int,
    var name: String,
    @SerializedName("username") var userName: String,
    var email: String,
    @Ignore var address: AddressEntity?,
    var phone: String,
    var website: String,
    @Ignore var company: CompanyEntity?
) {
    constructor(
        id: Int,
        name: String,
        userName: String,
        email: String,
        phone: String,
        website: String
    ) : this(id, name, userName, email, null, phone, website, null)
}