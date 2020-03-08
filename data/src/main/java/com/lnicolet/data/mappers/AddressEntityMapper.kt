package com.lnicolet.data.mappers

import com.lnicolet.data.entities.AddressEntity
import com.lnicolet.domain.models.AddressDomainModel
import javax.inject.Inject

class AddressEntityMapper @Inject constructor(private val geoEntityMapper: GeoEntityMapper) {

    fun mapToDomain(entity: AddressEntity?): AddressDomainModel? =
        entity?.let {
            AddressDomainModel(
                it.street,
                it.suite,
                it.city,
                it.zipCode,
                geoEntityMapper.mapToDomain(it.geo)
            )
        }
}