package com.lnicolet.domain.usecase

import com.lnicolet.domain.model.PostDomainModel
import com.lnicolet.domain.repository.PostsRepository
import io.reactivex.Single
import javax.inject.Inject

class PostsUseCase @Inject constructor(private val postsRepository: PostsRepository)  {

    fun getPots(): Single<List<PostDomainModel>> = postsRepository.getPosts()
}