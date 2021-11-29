package com.lnicolet.androidchallenge.postdetails.mappers

import com.lnicolet.androidchallenge.postdetails.models.PostDetail
import com.lnicolet.domain_lib.models.PostDetailDomainModel
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