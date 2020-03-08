package com.lnicolet.data.mappers

import com.lnicolet.data.entities.GeoEntity
import com.lnicolet.domain.models.GeoDomainModel
import javax.inject.Inject

class GeoEntityMapper @Inject constructor() {

    fun mapToDomain(entity: GeoEntity?): GeoDomainModel? =
        entity?.let {
            GeoDomainModel(it.lat, it.lng)
        }

}