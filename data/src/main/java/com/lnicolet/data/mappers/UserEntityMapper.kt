package com.lnicolet.data.mappers

import com.lnicolet.data.entities.UserEntity
import com.lnicolet.domain.models.UserDomainModel
import javax.inject.Inject

class UserEntityMapper @Inject constructor(
        private val addressEntityMapper: AddressEntityMapper,
        private val companyEntityMapper: CompanyEntityMapper
) {

    companion object {
        private const val BASE_AVATAR_URL = "https://api.adorable.io/avatars/256/"
        private const val AVATAR_URL_EXTENSION = ".png"
    }

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
                    "$BASE_AVATAR_URL${entity.id}$AVATAR_URL_EXTENSION"
            )
}