package com.lnicolet.androidchallenge.postdetails.mappers

import com.lnicolet.androidchallenge.postdetails.models.Comment
import com.lnicolet.domain.models.CommentDomainModel
import javax.inject.Inject

class CommentsMapper @Inject constructor(private val commentMapper: CommentMapper) {

    fun mapToPresentation(list: List<CommentDomainModel>): List<Comment> =
        list.map {
            commentMapper.mapToPresentation(it)
        }
}

class CommentMapper @Inject constructor() {

    fun mapToPresentation(domainModel: CommentDomainModel): Comment =
        Comment(
            domainModel.postId,
            domainModel.id,
            domainModel.name,
            domainModel.body
        )
}