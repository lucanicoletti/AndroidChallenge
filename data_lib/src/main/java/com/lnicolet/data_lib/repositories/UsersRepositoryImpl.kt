package com.lnicolet.data_lib.repositories

import com.lnicolet.data_lib.UsersApi
import com.lnicolet.data_lib.mappers.UserEntityMapper
import com.lnicolet.domain_lib.models.UserDomainModel
import com.lnicolet.domain_lib.repositories.UsersRepository
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