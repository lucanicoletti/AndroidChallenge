package com.lnicolet.data.mappers

import com.lnicolet.data.entities.PostEntity
import com.lnicolet.domain.model.PostDomainModel
import javax.inject.Inject

class PostEntityMapper @Inject constructor() {

    fun mapToDomain(entity: PostEntity): PostDomainModel =
            PostDomainModel(
                    entity.userId,
                    entity.id,
                    entity.title,
                    entity.body
            )
}