package com.lnicolet.data.mappers

import com.lnicolet.data.entities.AddressEntity
import com.lnicolet.domain.model.AddressDomainModel
import javax.inject.Inject

class AddressEntityMapper @Inject constructor(private val geoEntityMapper: GeoEntityMapper) {

    fun mapToDomain(entity: AddressEntity): AddressDomainModel =
            AddressDomainModel(
                    entity.street,
                    entity.suite,
                    entity.city,
                    entity.zipCode,
                    geoEntityMapper.mapToDomain(entity.geo)
            )
}