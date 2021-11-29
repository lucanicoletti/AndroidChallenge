package com.lnicolet.data_lib.mappers

import com.lnicolet.data_lib.entities.CompanyEntity
import com.lnicolet.domain_lib.models.CompanyDomainModel
import javax.inject.Inject

class CompanyEntityMapper @Inject constructor() {

    fun mapToDomain(entity: CompanyEntity?): CompanyDomainModel? =
            entity?.let {
                    CompanyDomainModel(
                            it.name,
                            it.catchPhrase,
                            it.bs
                    )
            }
}