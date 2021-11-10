package com.lnicolet.domain.usecases

import com.lnicolet.domain.models.CommentDomainModel
import com.lnicolet.domain.models.PostDetailDomainModel
import com.lnicolet.domain.models.UserDomainModel
import com.lnicolet.domain.repositories.CommentsRepository
import com.lnicolet.domain.repositories.UsersRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class CommentsAndUserUseCase @Inject constructor(
    private val commentsRepository: CommentsRepository,
    private val usersRepository: UsersRepository
) {

    suspend fun getCommentsAndUser(params: Params): PostDetailDomainModel {
        val comments = commentsRepository.getCommentsByPost(params.postId)
        val user = usersRepository.getUsersById(params.userId)
        return PostDetailDomainModel(user, comments)
    }

    data class Params(val userId: Int, val postId: Int)
}