package com.lnicolet.androidchallenge.postdetails.mappers

import com.lnicolet.androidchallenge.postdetails.models.User
import com.lnicolet.domain.models.UserDomainModel
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun mapToPresentation(domainModel: UserDomainModel): User =
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