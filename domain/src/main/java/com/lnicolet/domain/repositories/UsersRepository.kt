package com.lnicolet.domain.repositories

import com.lnicolet.domain.models.UserDomainModel

interface UsersRepository {
    suspend fun getUsers(): List<UserDomainModel>
    suspend fun getUsersById(userId: Int): UserDomainModel
}