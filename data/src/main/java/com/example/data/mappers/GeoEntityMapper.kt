package com.example.data.mappers

import com.example.data.entities.GeoEntity
import com.example.domain.models.GeoDomainModel
import javax.inject.Inject

class GeoEntityMapper @Inject constructor() {

    fun mapToDomain(entity: GeoEntity): GeoDomainModel =
            GeoDomainModel(entity.lat, entity.lng)
}