package com.example.domain.repositories

import com.example.domain.models.PostDomainModel
import io.reactivex.Single

interface PostsRepository {
    fun getPosts(): Single<List<PostDomainModel>>
}