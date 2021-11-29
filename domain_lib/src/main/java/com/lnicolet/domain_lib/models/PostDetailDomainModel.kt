package com.lnicolet.domain_lib.models

data class PostDetailDomainModel(
    val user: UserDomainModel,
    val commentList: List<CommentDomainModel>
)