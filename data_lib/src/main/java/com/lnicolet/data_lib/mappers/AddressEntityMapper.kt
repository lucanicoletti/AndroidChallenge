package com.lnicolet.data_lib.mappers

import com.lnicolet.data_lib.entities.AddressEntity
import com.lnicolet.domain_lib.models.AddressDomainModel
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