package com.lnicolet.data.repositories

import com.lnicolet.data.UsersApi
import com.lnicolet.data.dao.UserDao
import com.lnicolet.data.entities.UserEntity
import com.lnicolet.data.mappers.UserEntityMapper
import com.lnicolet.domain.models.UserDomainModel
import com.lnicolet.domain.repositories.UsersRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi,
    private val userEntityMapper: UserEntityMapper,
    private val userDao: UserDao
) : UsersRepository {

    override fun getUsers(): Observable<List<UserDomainModel>> =

        usersApi.getUsers().map { list ->
            list.map {
                cacheUser(it)
                userEntityMapper.mapToDomain(it)
            }
        }.onErrorResumeNext(getCachedUsers())

    override fun getUsersById(userId: Int): Single<UserDomainModel> =
        usersApi.getUsersById(userId)
            .map {
                userEntityMapper.mapToDomain(it)
            }

    private fun cacheUser(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }

    private fun getCachedUsers(): Observable<List<UserDomainModel>> =
        userDao.getUsers().map { list ->
            if (list.isEmpty()) throw Throwable()
            list.map {
                userEntityMapper.mapToDomain(it)
            }
        }

}