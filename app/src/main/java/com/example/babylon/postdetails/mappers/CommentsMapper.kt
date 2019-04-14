package com.example.babylon.postdetails.mappers

import com.example.babylon.postdetails.models.Comment
import com.example.domain.models.CommentDomainModel
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