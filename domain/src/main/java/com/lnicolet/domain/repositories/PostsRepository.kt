package com.lnicolet.domain.repositories

import com.lnicolet.domain.models.PostDomainModel
import io.reactivex.Single

interface PostsRepository {
    fun getPosts(): Single<List<PostDomainModel>>
}