package com.example.domain.usecases

import com.example.domain.models.CommentDomainModel
import com.example.domain.models.PostDetailDomainModel
import com.example.domain.models.UserDomainModel
import com.example.domain.repositories.CommentsRepository
import com.example.domain.repositories.UsersRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class CommentsAndUserUseCase @Inject constructor(
    private val commentsRepository: CommentsRepository,
    private val usersRepository: UsersRepository
) {

    fun getCommentsAndUser(params: Params):
        Single<PostDetailDomainModel> =
        Single.zip(
            commentsRepository.getCommentsByPost(params.postId),
            usersRepository.getUsersById(params.userId),
            BiFunction<List<CommentDomainModel>, UserDomainModel,
                PostDetailDomainModel> { comments, user ->
                PostDetailDomainModel(user, comments)
            }
        )

    data class Params(val userId: Int, val postId: Int)
}