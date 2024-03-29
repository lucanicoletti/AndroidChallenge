package com.lnicolet.data_lib.repositories

import com.lnicolet.data_lib.CommentsApi
import com.lnicolet.data_lib.mappers.CommentEntityMapper
import com.lnicolet.domain_lib.models.CommentDomainModel
import com.lnicolet.domain_lib.repositories.CommentsRepository
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
    private val commentsApi: CommentsApi,
    private val commentEntityMapper: CommentEntityMapper
) : CommentsRepository {

    override suspend fun getComments(): List<CommentDomainModel> =
        commentsApi.getComments().map {
            commentEntityMapper.mapToDomain(it)
        }


    override suspend fun getCommentsByPost(postId: Int): List<CommentDomainModel> =
        commentsApi.getCommentsByPost(postId).map {
            commentEntityMapper.mapToDomain(it)
        }
}