package com.lnicolet.domain_lib.usecases

import com.lnicolet.domain_lib.models.PostDomainModel
import com.lnicolet.domain_lib.repositories.PostsRepository
import javax.inject.Inject

class PostsUseCase @Inject constructor(private val postsRepository: PostsRepository)  {

    suspend fun getPots(): List<PostDomainModel> = postsRepository.getPosts()
}