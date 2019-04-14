package com.example.domain.usecases

import com.example.domain.models.CommentDomainModel
import com.example.domain.models.UserDomainModel
import com.example.domain.repositories.CommentsRepository
import com.example.domain.repositories.UsersRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class CommentsAndUserUseCase @Inject constructor(
        private val commentsRepository: CommentsRepository,
        private val usersRepository: UsersRepository
) : SingleUseCase<Pair<List<CommentDomainModel>, UserDomainModel>, Int>() {

    override fun buildUseCaseSingle(params: Int):
            Single<Pair<List<CommentDomainModel>, UserDomainModel>> =
            Single.zip(
                    commentsRepository.getCommentsByPost(params),
                    usersRepository.getUsersById(params),
                    BiFunction<List<CommentDomainModel>, UserDomainModel,
                            Pair<List<CommentDomainModel>, UserDomainModel>> { t1, t2 ->
                        Pair(t1, t2)
                    }
            )
}