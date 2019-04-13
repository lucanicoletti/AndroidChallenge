package com.example.data.mappers

import com.example.data.entities.CommentEntity
import com.example.domain.models.CommentDomainModel
import javax.inject.Inject

class CommentEntityMapper @Inject constructor() {

    fun mapToDomain(entity: CommentEntity): CommentDomainModel =
            CommentDomainModel(
                    entity.postId,
                    entity.id,
                    entity.name,
                    entity.email,
                    entity.body
            )
}