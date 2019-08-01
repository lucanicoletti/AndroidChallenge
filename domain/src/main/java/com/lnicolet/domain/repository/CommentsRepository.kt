package com.lnicolet.domain.repository

import com.lnicolet.domain.model.CommentDomainModel
import io.reactivex.Single

interface CommentsRepository {
    fun getComments(): Single<List<CommentDomainModel>>
    fun getCommentsByPost(postId: Int): Single<List<CommentDomainModel>>
}