package com.example.domain.usecases

import com.example.domain.models.PostDomainModel
import com.example.domain.repositories.PostsRepository
import com.example.domain.repositories.UsersRepository
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

    fun getPostsWithUsers(): Single<List<PostDomainModel>> =
            Single.zip(
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