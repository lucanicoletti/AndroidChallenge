package com.example.domain.repositories

import com.example.domain.models.UserDomainModel
import io.reactivex.Single

interface UsersRepository {
    fun getUsers(): Single<List<UserDomainModel>>
    fun getUsersById(userId: Int): Single<UserDomainModel>
}