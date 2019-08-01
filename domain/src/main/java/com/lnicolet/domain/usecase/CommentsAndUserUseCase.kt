package com.lnicolet.domain.usecase

import com.lnicolet.domain.model.CommentDomainModel
import com.lnicolet.domain.model.PostDetailDomainModel
import com.lnicolet.domain.model.UserDomainModel
import com.lnicolet.domain.repository.CommentsRepository
import com.lnicolet.domain.repository.UsersRepository
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