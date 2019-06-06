package com.lnicolet.domain.models

data class PostDetailDomainModel(
    val user: UserDomainModel,
    val commentList: List<CommentDomainModel>
)