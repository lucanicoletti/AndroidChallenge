package com.lnicolet.data_lib.mappers

import com.lnicolet.data_lib.entities.CommentEntity
import com.lnicolet.domain_lib.models.CommentDomainModel
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