package com.lnicolet.domain.repositories

import com.lnicolet.domain.models.CommentDomainModel
import io.reactivex.Single

interface CommentsRepository {
    fun getComments(): Single<List<CommentDomainModel>>
    fun getCommentsByPost(postId: Int): Single<List<CommentDomainModel>>
}