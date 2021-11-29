package com.lnicolet.data_lib.entities

data class PostEntity(
    var id: Int,
    var userId: Int,
    var title: String,
    var body: String
)