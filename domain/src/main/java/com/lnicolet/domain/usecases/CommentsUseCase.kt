package com.lnicolet.domain.usecases

import com.lnicolet.domain.models.CommentDomainModel
import com.lnicolet.domain.repositories.CommentsRepository
import io.reactivex.Single
import javax.inject.Inject

class CommentsUseCase @Inject constructor(private val commentsRepository: CommentsRepository) {

    suspend fun getComments(): List<CommentDomainModel> =
            commentsRepository.getComments()

    suspend fun getCommentsByPostId(postId: Int): List<CommentDomainModel> =
            commentsRepository.getCommentsByPost(postId)
}