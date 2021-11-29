package com.lnicolet.data_lib.entities

data class CommentEntity(
    var id: Int,
    var postId: Int,
    var name: String,
    var email: String,
    var body: String
)