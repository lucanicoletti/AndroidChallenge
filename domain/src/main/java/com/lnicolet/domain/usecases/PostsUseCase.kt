package com.lnicolet.domain.usecases

import com.lnicolet.domain.models.PostDomainModel
import com.lnicolet.domain.repositories.PostsRepository
import io.reactivex.Single
import javax.inject.Inject

class PostsUseCase @Inject constructor(private val postsRepository: PostsRepository)  {

    fun getPots(): Single<List<PostDomainModel>> = postsRepository.getPosts()
}