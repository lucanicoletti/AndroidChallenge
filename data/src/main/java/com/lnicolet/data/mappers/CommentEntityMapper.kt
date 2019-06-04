package com.lnicolet.data.mappers

import com.lnicolet.data.entities.CommentEntity
import com.lnicolet.domain.models.CommentDomainModel
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