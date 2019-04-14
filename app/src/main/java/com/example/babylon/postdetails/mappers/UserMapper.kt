package com.example.babylon.postdetails.mappers

import com.example.babylon.postdetails.models.User
import com.example.domain.models.UserDomainModel
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