package com.lnicolet.domain.repositories

import com.lnicolet.domain.models.CommentDomainModel

interface CommentsRepository {
    suspend fun getComments(): List<CommentDomainModel>
    suspend fun getCommentsByPost(postId: Int): List<CommentDomainModel>
}