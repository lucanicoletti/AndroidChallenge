package com.lnicolet.babylonandroidchallenge.di.modules

import com.lnicolet.data.CommentsApi
import com.lnicolet.data.PostsApi
import com.lnicolet.data.UsersApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Suppress("unused")
@Module(includes = [NetworkModule::class])
class ApiModule {

    @Provides
    fun provideCommentsApi(retrofit: Retrofit): CommentsApi =
        retrofit.create(CommentsApi::class.java)

    @Provides
    fun providePostsApi(retrofit: Retrofit): PostsApi = retrofit.create(PostsApi::class.java)

    @Provides
    fun provideUsersApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)
}