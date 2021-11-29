package com.lnicolet.domain_lib.usecases

import com.lnicolet.domain_lib.models.UserDomainModel
import com.lnicolet.domain_lib.repositories.UsersRepository
import javax.inject.Inject

class UsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {

    suspend fun getUsers(): List<UserDomainModel> = usersRepository.getUsers()
}