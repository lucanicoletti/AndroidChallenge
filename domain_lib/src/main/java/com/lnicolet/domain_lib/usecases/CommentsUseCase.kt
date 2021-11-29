package com.lnicolet.domain_lib.usecases

import com.lnicolet.domain_lib.models.CommentDomainModel
import com.lnicolet.domain_lib.repositories.CommentsRepository
import javax.inject.Inject

class CommentsUseCase @Inject constructor(private val commentsRepository: CommentsRepository) {

    suspend fun getComments(): List<CommentDomainModel> =
            commentsRepository.getComments()

    suspend fun getCommentsByPostId(postId: Int): List<CommentDomainModel> =
            commentsRepository.getCommentsByPost(postId)
}