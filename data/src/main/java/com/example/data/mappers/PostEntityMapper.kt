package com.example.data.mappers

import com.example.data.entities.PostEntity
import com.example.domain.models.PostDomainModel
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