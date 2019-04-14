package com.example.data

import com.example.data.entities.CommentEntity
import com.example.data.entities.PostEntity
import com.example.data.entities.UserEntity
import retrofit2.http.GET
import io.reactivex.Single
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentsApi {

    @GET("/comments")
    fun getComments(): Single<ArrayList<CommentEntity>>

    @GET("/comments")
    fun getCommentsByPost(@Query("postId") postId: Int): Single<ArrayList<CommentEntity>>
}

interface UsersApi {

    @GET("/users")
    fun getUsers(): Single<ArrayList<UserEntity>>

    @GET("/users/{userId}")
    fun getUsersById(@Path("userId") userId: Int): Single<UserEntity>
}

interface PostsApi {

    @GET("/posts")
    fun getPosts(): Single<ArrayList<PostEntity>>
}