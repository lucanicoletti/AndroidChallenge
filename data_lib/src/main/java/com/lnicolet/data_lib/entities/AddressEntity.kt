package com.lnicolet.data_lib.entities

import com.google.gson.annotations.SerializedName

data class AddressEntity(
    var street: String,
    var suite: String,
    var city: String,
    @SerializedName("zipcode") var zipCode: String,
    var geo: GeoEntity? = null
)