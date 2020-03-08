package com.lnicolet.androidchallenge.di.modules

import android.content.Context
import androidx.room.Room
import com.lnicolet.data.dao.PostDao
import com.lnicolet.data.dao.UserDao
import com.lnicolet.data.db.PostsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Suppress("unused")
@Module
class RoomModule {
    @Provides
    @Singleton
    fun providePostsDatabase(context: Context): PostsDatabase {
        return PostsDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun providesPostDao(database: PostsDatabase): PostDao = database.postDao()

    @Provides
    @Singleton
    fun providesUserDao(database: PostsDatabase): UserDao = database.userDao()
}