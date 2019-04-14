package com.example.babylon.di.modules

import com.example.data.repositories.CommentsRepositoryImpl
import com.example.data.repositories.PostsRepositoryImpl
import com.example.data.repositories.UsersRepositoryImpl
import com.example.domain.repositories.CommentsRepository
import com.example.domain.repositories.PostsRepository
import com.example.domain.repositories.UsersRepository
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module(includes = [ApiModule::class])
abstract class RepositoryModule  {

    @Binds
    abstract fun bindCommentsRepository(repository: CommentsRepositoryImpl): CommentsRepository

    @Binds
    abstract fun bindUsersRepository(repository: UsersRepositoryImpl): UsersRepository

    @Binds
    abstract fun bindPostsRepository(repository: PostsRepositoryImpl): PostsRepository
}