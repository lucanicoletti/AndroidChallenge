package com.lnicolet.data.repositories

import com.lnicolet.data.CommentsApi
import com.lnicolet.data.db.PostsDatabase
import com.lnicolet.data.mappers.CommentEntityMapper
import com.lnicolet.domain.models.CommentDomainModel
import com.lnicolet.domain.repositories.CommentsRepository
import io.reactivex.Single
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
        private val commentsApi: CommentsApi,
        private val commentEntityMapper: CommentEntityMapper,
        private val db: PostsDatabase
) : CommentsRepository {

    override fun getComments(): Single<List<CommentDomainModel>> =
            commentsApi.getComments()
                    .map { list ->
                        list.map {
                            commentEntityMapper.mapToDomain(it)
                        }
                    }

    override fun getCommentsByPost(postId: Int): Single<List<CommentDomainModel>> =
            commentsApi.getCommentsByPost(postId)
                    .map { list ->
                        list.map {
                            commentEntityMapper.mapToDomain(it)
                        }
                    }
}