package com.lnicolet.data.mappers

import com.lnicolet.data.entities.GeoEntity
import com.lnicolet.domain.model.GeoDomainModel
import javax.inject.Inject

class GeoEntityMapper @Inject constructor() {

    fun mapToDomain(entity: GeoEntity): GeoDomainModel =
            GeoDomainModel(entity.lat, entity.lng)
}