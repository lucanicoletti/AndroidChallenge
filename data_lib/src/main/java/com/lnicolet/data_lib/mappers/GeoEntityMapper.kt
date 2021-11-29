package com.lnicolet.data_lib.mappers

import com.lnicolet.data_lib.entities.GeoEntity
import com.lnicolet.domain_lib.models.GeoDomainModel
import javax.inject.Inject

class GeoEntityMapper @Inject constructor() {

    fun mapToDomain(entity: GeoEntity?): GeoDomainModel? =
        entity?.let {
            GeoDomainModel(it.lat, it.lng)
        }

}