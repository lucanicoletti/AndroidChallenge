package com.example.data.repositories

import com.example.data.CommentsApi
import com.example.data.mappers.CommentEntityMapper
import com.example.domain.models.CommentDomainModel
import com.example.domain.repositories.CommentsRepository
import io.reactivex.Single
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
        private val commentsApi: CommentsApi,
        private val commentEntityMapper: CommentEntityMapper
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