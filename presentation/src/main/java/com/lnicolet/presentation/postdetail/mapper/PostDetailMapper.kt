package com.lnicolet.presentation.postdetail.mapper

import com.lnicolet.presentation.postdetail.model.PostDetail
import com.lnicolet.domain.model.PostDetailDomainModel
import com.lnicolet.presentation.base.mapper.Mapper
import com.lnicolet.presentation.postlist.mapper.UserMapper
import javax.inject.Inject

class PostDetailMapper @Inject constructor(
    private val commentsMapper: CommentsMapper,
    private val userMapper: UserMapper
): Mapper<PostDetail, PostDetailDomainModel> {
    override fun mapToView(domainModel: PostDetailDomainModel): PostDetail =
        PostDetail(
            userMapper.mapToView(domainModel.user),
            commentsMapper.mapToView(domainModel.commentList)
        )
}