package com.lnicolet.domain.usecases

import com.lnicolet.domain.models.PostDomainModel
import com.lnicolet.domain.repositories.PostsRepository
import com.lnicolet.domain.repositories.UsersRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * Created by Luca Nicoletti
 * on 19/04/2019
 */

class PostsAndUsersUseCase @Inject constructor(
    private val postsRepository: PostsRepository,
    private val usersRepository: UsersRepository
) {

    fun getPostsWithUsers(): Observable<List<PostDomainModel>> =
            Observable.zip(
                postsRepository.getPosts(),
                usersRepository.getUsers(),
                BiFunction { posts, users ->
                    posts.forEach { singlePost ->
                        singlePost.user = users.firstOrNull { singleUser ->
                            singleUser.id == singlePost.userId
                        }
                    }
                    posts.shuffled() // just to not see all the post of 1 user at the beginning
                }
            )

}