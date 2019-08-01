package com.lnicolet.domain.repository

import com.lnicolet.domain.model.PostDomainModel
import io.reactivex.Single

interface PostsRepository {
    fun getPosts(): Single<List<PostDomainModel>>
}