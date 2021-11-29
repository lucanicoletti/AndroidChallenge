package com.lnicolet.domain_lib.repositories

import com.lnicolet.domain_lib.models.UserDomainModel

interface UsersRepository {
    suspend fun getUsers(): List<UserDomainModel>
    suspend fun getUsersById(userId: Int): UserDomainModel
}