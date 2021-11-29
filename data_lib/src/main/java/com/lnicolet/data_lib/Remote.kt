package com.lnicolet.data_lib

import com.lnicolet.data_lib.entities.CommentEntity
import com.lnicolet.data_lib.entities.PostEntity
import com.lnicolet.data_lib.entities.UserEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentsApi {

    @GET("/comments")
    suspend fun getComments(): ArrayList<CommentEntity>

    @GET("/comments")
    suspend fun getCommentsByPost(@Query("postId") postId: Int): ArrayList<CommentEntity>
}

interface UsersApi {

    @GET("/users")
    suspend fun getUsers(): ArrayList<UserEntity>

    @GET("/users/{userId}")
    suspend fun getUsersById(@Path("userId") userId: Int): UserEntity
}

interface PostsApi {

    @GET("/posts")
    suspend fun getPosts(): ArrayList<PostEntity>
}