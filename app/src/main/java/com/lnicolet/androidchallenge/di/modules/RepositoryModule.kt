package com.lnicolet.androidchallenge.di.modules

import com.lnicolet.data_lib.repositories.CommentsRepositoryImpl
import com.lnicolet.data_lib.repositories.PostsRepositoryImpl
import com.lnicolet.data_lib.repositories.UsersRepositoryImpl
import com.lnicolet.domain_lib.repositories.CommentsRepository
import com.lnicolet.domain_lib.repositories.PostsRepository
import com.lnicolet.domain_lib.repositories.UsersRepository
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module(includes = [ApiModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun bindCommentsRepository(repository: CommentsRepositoryImpl): CommentsRepository

    @Binds
    abstract fun bindUsersRepository(repository: UsersRepositoryImpl): UsersRepository

    @Binds
    abstract fun bindPostsRepository(repository: PostsRepositoryImpl): PostsRepository
}