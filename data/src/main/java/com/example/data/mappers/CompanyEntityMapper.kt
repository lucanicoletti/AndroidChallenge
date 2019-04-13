package com.example.data.mappers

import com.example.data.entities.CompanyEntity
import com.example.domain.models.CompanyDomainModel
import javax.inject.Inject

class CompanyEntityMapper @Inject constructor() {

    fun mapToDomain(entity: CompanyEntity): CompanyDomainModel =
            CompanyDomainModel(
                    entity.name,
                    entity.catchPhrase,
                    entity.bs
            )
}