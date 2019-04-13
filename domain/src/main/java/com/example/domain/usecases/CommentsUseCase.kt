package com.example.domain.usecases

import com.example.domain.models.CommentDomainModel
import com.example.domain.repositories.CommentsRepository
import io.reactivex.Single
import javax.inject.Inject

class CommentsUseCase @Inject constructor(private val commentsRepository: CommentsRepository)
    : SingleUseCase<List<CommentDomainModel>, Any>() {

    override fun buildUseCaseSingle(params: Any): Single<List<CommentDomainModel>> =
            commentsRepository.getComments()
}