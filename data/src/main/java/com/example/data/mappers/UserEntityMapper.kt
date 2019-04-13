package com.example.data.mappers

import com.example.data.entities.UserEntity
import com.example.domain.models.UserDomainModel
import javax.inject.Inject

class UserEntityMapper @Inject constructor(
        private val addressEntityMapper: AddressEntityMapper,
        private val companyEntityMapper: CompanyEntityMapper
) {

    fun mapToDomain(entity: UserEntity): UserDomainModel =
            UserDomainModel(
                    entity.id,
                    entity.name,
                    entity.userName,
                    entity.email,
                    addressEntityMapper.mapToDomain(entity.address),
                    entity.phone,
                    entity.website,
                    companyEntityMapper.mapToDomain(entity.company)
            )
}