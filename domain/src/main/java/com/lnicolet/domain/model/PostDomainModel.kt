package com.lnicolet.domain.model

data class PostDomainModel(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    var user: UserDomainModel? = null
)