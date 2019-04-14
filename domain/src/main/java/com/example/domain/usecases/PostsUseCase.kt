package com.example.domain.usecases

import com.example.domain.models.PostDomainModel
import com.example.domain.repositories.PostsRepository
import io.reactivex.Single
import javax.inject.Inject

class PostsUseCase @Inject constructor(private val postsRepository: PostsRepository) : SingleUseCase<List<PostDomainModel>, Unit>() {

    override fun buildUseCaseSingle(params: Unit): Single<List<PostDomainModel>> =
            postsRepository.getPosts()

}