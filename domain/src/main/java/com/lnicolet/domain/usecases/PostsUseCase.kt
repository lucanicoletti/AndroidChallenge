package com.lnicolet.domain.usecases

import com.lnicolet.domain.models.PostDomainModel
import com.lnicolet.domain.repositories.PostsRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class PostsUseCase @Inject constructor(private val postsRepository: PostsRepository)  {

    fun getPots(): Observable<List<PostDomainModel>> = postsRepository.getPosts()
}