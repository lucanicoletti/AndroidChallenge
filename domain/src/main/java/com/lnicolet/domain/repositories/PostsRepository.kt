package com.lnicolet.domain.repositories

import com.lnicolet.domain.models.PostDomainModel
import io.reactivex.Observable

interface PostsRepository {
    fun getPosts(): Observable<List<PostDomainModel>>
}