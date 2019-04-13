package com.example.data.mappers

import com.example.data.entities.AddressEntity
import com.example.domain.models.AddressDomainModel
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