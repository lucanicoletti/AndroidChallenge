package com.lnicolet.domain.repositories

import com.lnicolet.domain.models.PostDomainModel

interface PostsRepository {
    suspend fun getPosts(): List<PostDomainModel>
}