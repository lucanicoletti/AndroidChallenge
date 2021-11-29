package com.lnicolet.data_lib.repositories

import com.lnicolet.data_lib.PostsApi
import com.lnicolet.data_lib.mappers.PostEntityMapper
import com.lnicolet.domain_lib.models.PostDomainModel
import com.lnicolet.domain_lib.repositories.PostsRepository
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