package com.lnicolet.domain.repositories

import com.lnicolet.domain.models.UserDomainModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface UsersRepository {
    fun getUsers(): Observable<List<UserDomainModel>>
    fun getUsersById(userId: Int): Single<UserDomainModel>
}