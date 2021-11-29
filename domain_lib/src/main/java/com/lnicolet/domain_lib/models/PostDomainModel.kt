package com.lnicolet.domain_lib.models

data class PostDomainModel(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    var user: UserDomainModel? = null
)