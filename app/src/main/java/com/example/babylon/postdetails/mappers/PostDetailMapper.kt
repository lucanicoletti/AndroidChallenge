package com.example.babylon.postdetails.mappers

import com.example.babylon.postdetails.models.PostDetail
import com.example.domain.models.PostDetailDomainModel
import javax.inject.Inject

class PostDetailMapper @Inject constructor(
    private val commentsMapper: CommentsMapper,
    private val userMapper: UserMapper
) {

    fun mapToPresentation(domainModel: PostDetailDomainModel): PostDetail =
        PostDetail(
            userMapper.mapToPresentation(domainModel.user),
            commentsMapper.mapToPresentation(domainModel.commentList)
        )
}