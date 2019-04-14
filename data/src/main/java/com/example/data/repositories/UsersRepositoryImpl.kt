package com.example.data.repositories

import com.example.data.UsersApi
import com.example.data.mappers.UserEntityMapper
import com.example.domain.models.UserDomainModel
import com.example.domain.repositories.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
        private val usersApi: UsersApi,
        private val userEntityMapper: UserEntityMapper
) : UsersRepository {

    override fun getUsers(): Single<List<UserDomainModel>> =
            usersApi.getUsers()
                    .map { list ->
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