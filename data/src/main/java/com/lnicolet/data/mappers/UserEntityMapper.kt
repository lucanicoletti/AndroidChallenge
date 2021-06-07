package com.lnicolet.data.mappers

import com.lnicolet.data.entities.UserEntity
import com.lnicolet.domain.model.UserDomainModel
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
            companyEntityMapper.mapToDomain(entity.company),
            "$BASE_AVATAR_URL${entity.userName.first()}${AVATAR_PARAMS}"
        )
}

private const val BASE_AVATAR_URL = "https://eu.ui-avatars.com/api/?name="
private const val AVATAR_PARAMS = "&size=512&background=random"
