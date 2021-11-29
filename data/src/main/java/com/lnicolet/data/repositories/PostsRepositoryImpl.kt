package com.lnicolet.data.repositories

import com.lnicolet.data.PostsApi
import com.lnicolet.data.mappers.PostEntityMapper
import com.lnicolet.domain.models.PostDomainModel
import com.lnicolet.domain.repositories.PostsRepository
import javax.inject.Inject


class PostsRepositoryImpl @Inject constructor(
    private val postsApi: PostsApi,
    private val postEntityMapper: PostEntityMapper
) : PostsRepository {

    override suspend fun getPosts(): List<PostDomainModel> =
        postsApi.getPosts().map {
            postEntityMapper.mapToDomain(it)
        }
}