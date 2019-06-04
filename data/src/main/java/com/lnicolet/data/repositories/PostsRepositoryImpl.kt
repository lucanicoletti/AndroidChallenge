package com.lnicolet.data.repositories

import com.lnicolet.data.PostsApi
import com.lnicolet.data.mappers.PostEntityMapper
import com.lnicolet.domain.models.PostDomainModel
import com.lnicolet.domain.repositories.PostsRepository
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