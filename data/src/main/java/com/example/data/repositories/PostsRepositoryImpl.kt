package com.example.data.repositories

import com.example.data.PostsApi
import com.example.data.mappers.PostEntityMapper
import com.example.domain.models.PostDomainModel
import com.example.domain.repositories.PostsRepository
import io.reactivex.Single
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
        private val postsApi: PostsApi,
        private val postEntityMapper: PostEntityMapper
) : PostsRepository {
    override fun getPosts(): Single<List<PostDomainModel>> =
            postsApi.getPosts()
                    .map { list ->
                        list.map {
                            postEntityMapper.mapToDomain(it)
                        }
                    }
}