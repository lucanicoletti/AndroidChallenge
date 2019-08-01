package com.lnicolet.presentation.postdetail.mapper

import com.lnicolet.domain.model.CommentDomainModel
import com.lnicolet.presentation.base.mapper.Mapper
import com.lnicolet.presentation.postdetail.model.Comment
import javax.inject.Inject

class CommentsMapper @Inject constructor(private val commentMapper: CommentMapper) :
    Mapper<List<Comment>, List<CommentDomainModel>> {
    override fun mapToView(domainModel: List<CommentDomainModel>): List<Comment> =
        domainModel.map {
            commentMapper.mapToView(it)
        }
}

class CommentMapper @Inject constructor() : Mapper<Comment, CommentDomainModel> {
    override fun mapToView(domainModel: CommentDomainModel): Comment =
        Comment(
            domainModel.postId,
            domainModel.id,
            domainModel.name,
            domainModel.body
        )
}