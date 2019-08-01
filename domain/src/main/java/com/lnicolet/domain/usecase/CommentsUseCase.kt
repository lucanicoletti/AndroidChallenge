package com.lnicolet.domain.usecase

import com.lnicolet.domain.model.CommentDomainModel
import com.lnicolet.domain.repository.CommentsRepository
import io.reactivex.Single
import javax.inject.Inject

class CommentsUseCase @Inject constructor(private val commentsRepository: CommentsRepository) {

    fun getComments(): Single<List<CommentDomainModel>> =
            commentsRepository.getComments()

    fun getCommentsByPostId(postId: Int): Single<List<CommentDomainModel>> =
            commentsRepository.getCommentsByPost(postId)
}