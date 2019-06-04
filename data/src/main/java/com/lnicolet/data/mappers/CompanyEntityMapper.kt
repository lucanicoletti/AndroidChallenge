package com.lnicolet.data.mappers

import com.lnicolet.data.entities.CompanyEntity
import com.lnicolet.domain.models.CompanyDomainModel
import javax.inject.Inject

class CompanyEntityMapper @Inject constructor() {

    fun mapToDomain(entity: CompanyEntity): CompanyDomainModel =
            CompanyDomainModel(
                    entity.name,
                    entity.catchPhrase,
                    entity.bs
            )
}