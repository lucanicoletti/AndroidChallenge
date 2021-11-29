package com.lnicolet.data_lib.mappers

import com.lnicolet.data_lib.entities.PostEntity
import com.lnicolet.domain_lib.models.PostDomainModel
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