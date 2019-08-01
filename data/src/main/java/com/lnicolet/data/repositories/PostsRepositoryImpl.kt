package com.lnicolet.data.repositories

import com.lnicolet.data.PostsApi
import com.lnicolet.data.mappers.PostEntityMapper
import com.lnicolet.domain.model.PostDomainModel
import com.lnicolet.domain.repository.PostsRepository
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