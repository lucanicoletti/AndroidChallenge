package com.lnicolet.domain_lib.usecases

import com.lnicolet.domain_lib.models.PostDetailDomainModel
import com.lnicolet.domain_lib.repositories.CommentsRepository
import com.lnicolet.domain_lib.repositories.UsersRepository
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