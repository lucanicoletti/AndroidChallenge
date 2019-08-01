package com.lnicolet.domain.model

data class PostDetailDomainModel(
    val user: UserDomainModel,
    val commentList: List<CommentDomainModel>
)