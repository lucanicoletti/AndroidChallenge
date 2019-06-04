package com.lnicolet.domain.usecases

import com.lnicolet.domain.models.UserDomainModel
import com.lnicolet.domain.repositories.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class UsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {

    fun getUsers(): Single<List<UserDomainModel>> = usersRepository.getUsers()
}