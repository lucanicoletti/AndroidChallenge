package com.example.domain.repositories

import com.example.domain.models.CommentDomainModel
import io.reactivex.Single

interface CommentsRepository {
    fun getComments(): Single<List<CommentDomainModel>>
    fun getCommentsByPost(postId: Int): Single<List<CommentDomainModel>>
}