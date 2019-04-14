package com.example.babylon.postlist.mappers

import com.example.babylon.postlist.models.Post
import com.example.domain.models.PostDomainModel
import javax.inject.Inject


class PostsMapper @Inject constructor(private val postMapper: PostMapper) {

    fun mapToPresentation(list: List<PostDomainModel>): List<Post> =
            list.map {
                postMapper.mapToPresentation(it)
            }
}

class PostMapper @Inject constructor() {

    fun mapToPresentation(domainModel: PostDomainModel): Post =
            Post(
                    domainModel.id,
                    domainModel.userId,
                    domainModel.title,
                    domainModel.body
            )
}