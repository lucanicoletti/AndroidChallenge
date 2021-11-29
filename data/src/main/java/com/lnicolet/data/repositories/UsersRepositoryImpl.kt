package com.lnicolet.data.repositories

import com.lnicolet.data.UsersApi
import com.lnicolet.data.mappers.UserEntityMapper
import com.lnicolet.domain.models.UserDomainModel
import com.lnicolet.domain.repositories.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi,
    private val userEntityMapper: UserEntityMapper
) : UsersRepository {

    override suspend fun getUsers(): List<UserDomainModel> =
        usersApi.getUsers().map {
            userEntityMapper.mapToDomain(it)
        }

    override suspend fun getUsersById(userId: Int): UserDomainModel =
        userEntityMapper.mapToDomain(usersApi.getUsersById(userId))

}