package com.lnicolet.domain.usecases

import com.lnicolet.domain.models.UserDomainModel
import com.lnicolet.domain.repositories.UsersRepository
import javax.inject.Inject

class UsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {

    suspend fun getUsers(): List<UserDomainModel> = usersRepository.getUsers()
}