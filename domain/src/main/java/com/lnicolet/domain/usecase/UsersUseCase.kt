package com.lnicolet.domain.usecase

import com.lnicolet.domain.model.UserDomainModel
import com.lnicolet.domain.repository.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class UsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {

    fun getUsers(): Single<List<UserDomainModel>> = usersRepository.getUsers()
}