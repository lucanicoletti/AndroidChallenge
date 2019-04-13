package com.example.domain.usecases

import android.annotation.SuppressLint
import com.example.domain.models.CommentDomainModel
import com.example.domain.repositories.CommentsRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import javax.inject.Inject

class CommentsUseCase @Inject constructor(private val commentsRepository: CommentsRepository)
    : SingleUseCase<List<CommentDomainModel>, Any>() {

    override fun buildUseCaseSingle(params: Any): Single<List<CommentDomainModel>> =
        commentsRepository.getComments()

    @SuppressLint("CheckResult")
    private fun test() {
        Single.zip(
                commentsRepository.getComments(),
                commentsRepository.getComments(),
                commentsRepository.getComments(),
                Function3<List<CommentDomainModel>, List<CommentDomainModel>, List<CommentDomainModel>, Any> { t1, t2, t3 -> })
    }
}