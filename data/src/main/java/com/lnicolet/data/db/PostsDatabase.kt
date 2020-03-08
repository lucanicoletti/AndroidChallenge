package com.lnicolet.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lnicolet.data.dao.*
import com.lnicolet.data.entities.*

@Database(
    entities = [AddressEntity::class, CommentEntity::class, CompanyEntity::class, GeoEntity::class, PostEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
public abstract class PostsDatabase : RoomDatabase() {

    abstract fun addressDao(): AddressDao
    abstract fun commentsDao(): CommentsDao
    abstract fun companyDao(): CompanyDao
    abstract fun geoDao(): GeoDao
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: PostsDatabase? = null
        private const val DATABASE_NAME = "db_posts"

        fun getDatabase(context: Context): PostsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostsDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}