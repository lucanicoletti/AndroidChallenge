package com.lnicolet.domain.repository

import com.lnicolet.domain.model.UserDomainModel
import io.reactivex.Single

interface UsersRepository {
    fun getUsers(): Single<List<UserDomainModel>>
    fun getUsersById(userId: Int): Single<UserDomainModel>
}