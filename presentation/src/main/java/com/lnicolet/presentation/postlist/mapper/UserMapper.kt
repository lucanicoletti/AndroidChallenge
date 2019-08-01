package com.lnicolet.presentation.postlist.mapper

import com.lnicolet.domain.model.UserDomainModel
import com.lnicolet.presentation.base.mapper.Mapper
import com.lnicolet.presentation.postlist.model.User
import javax.inject.Inject

class UserMapper @Inject constructor() : Mapper<User, UserDomainModel> {

    override fun mapToView(domainModel: UserDomainModel): User =
        User(
            domainModel.id,
            domainModel.name,
            domainModel.userName,
            domainModel.email,
            domainModel.phone,
            domainModel.website,
            domainModel.imageUrl
        )
}