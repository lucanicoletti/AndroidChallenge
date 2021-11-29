package com.lnicolet.domain_lib.repositories

import com.lnicolet.domain_lib.models.PostDomainModel

interface PostsRepository {
    suspend fun getPosts(): List<PostDomainModel>
}