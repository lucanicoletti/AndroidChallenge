package com.lnicolet.data.repositories

import com.lnicolet.data.PostsApi
import com.lnicolet.data.dao.PostDao
import com.lnicolet.data.entities.PostEntity
import com.lnicolet.data.mappers.PostEntityMapper
import com.lnicolet.domain.models.PostDomainModel
import com.lnicolet.domain.repositories.PostsRepository
import io.reactivex.Observable
import javax.inject.Inject


class PostsRepositoryImpl @Inject constructor(
    private val postsApi: PostsApi,
    private val postEntityMapper: PostEntityMapper,
    private val postDao: PostDao
) : PostsRepository {

    private fun cachePost(postEntity: PostEntity) {
        postDao.insert(postEntity)
    }

    override fun getPosts(): Observable<List<PostDomainModel>> =
        postsApi.getPosts()
            .map { list ->
                list.map {
                    cachePost(it)
                    postEntityMapper.mapToDomain(it)
                }
            }.onErrorResumeNext(getCachedPosts())

    private fun getCachedPosts(): Observable<List<PostDomainModel>> =
        postDao.getPosts().map { list ->
            list.map {
                postEntityMapper.mapToDomain(it)
            }
        }.take(1).filter { list -> list.isNotEmpty() }
}