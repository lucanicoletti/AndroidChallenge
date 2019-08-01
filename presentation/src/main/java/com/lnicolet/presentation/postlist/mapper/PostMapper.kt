package com.lnicolet.presentation.postlist.mapper

import com.lnicolet.domain.model.PostDomainModel
import com.lnicolet.presentation.base.mapper.Mapper
import com.lnicolet.presentation.postlist.model.Post
import javax.inject.Inject


class PostsMapper @Inject constructor(
    private val postMapper: PostMapper
) : Mapper<List<Post>, List<PostDomainModel>> {
    override fun mapToView(domainModel: List<PostDomainModel>): List<Post> =
        domainModel.map {
            postMapper.mapToView(it)
        }
}

class PostMapper @Inject constructor(private val userMapper: UserMapper) :
    Mapper<Post, PostDomainModel> {
    override fun mapToView(domainModel: PostDomainModel): Post =
        Post(
            domainModel.id,
            domainModel.userId,
            domainModel.title,
            domainModel.body,
            domainModel.user?.let { userMapper.mapToView(it) }
        )
}