package com.lnicolet.domain_lib.repositories

import com.lnicolet.domain_lib.models.CommentDomainModel

interface CommentsRepository {
    suspend fun getComments(): List<CommentDomainModel>
    suspend fun getCommentsByPost(postId: Int): List<CommentDomainModel>
}