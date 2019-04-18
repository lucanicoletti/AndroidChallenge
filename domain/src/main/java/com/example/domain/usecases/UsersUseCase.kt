package com.example.domain.usecases

import com.example.domain.models.UserDomainModel
import com.example.domain.repositories.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class UsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {

    fun getUsers(): Single<List<UserDomainModel>> = usersRepository.getUsers()
}