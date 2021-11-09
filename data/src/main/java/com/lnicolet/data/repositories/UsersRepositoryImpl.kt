package com.lnicolet.data.repositories

import com.lnicolet.data.UsersApi
import com.lnicolet.data.mappers.UserEntityMapper
import com.lnicolet.domain.models.UserDomainModel
import com.lnicolet.domain.repositories.UsersRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi,
    private val userEntityMapper: UserEntityMapper
) : UsersRepository {

    override fun getUsers(): Single<List<UserDomainModel>> =
        usersApi.getUsers().map { list ->
            list.map {
                userEntityMapper.mapToDomain(it)
            }
        }

    override fun getUsersById(userId: Int): Single<UserDomainModel> =
        usersApi.getUsersById(userId)
            .map {
                userEntityMapper.mapToDomain(it)
            }

}