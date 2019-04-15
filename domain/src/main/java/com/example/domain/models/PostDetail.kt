package com.example.domain.models

data class PostDetailDomainModel(
    val user: UserDomainModel,
    val commentList: List<CommentDomainModel>
)