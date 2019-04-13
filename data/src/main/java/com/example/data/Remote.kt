package com.example.data

import com.example.data.entities.CommentEntity
import com.example.data.entities.PostEntity
import com.example.data.entities.UserEntity
import retrofit2.http.GET
import io.reactivex.Single

interface CommentsApi {

    @GET("/comments")
    fun getComments(): Single<ArrayList<CommentEntity>>
}

interface UsersApi {

    @GET("/users")
    fun getUsers(): Single<ArrayList<UserEntity>>
}

interface PostsApi {

    @GET("/posts")
    fun getPosts(): Single<ArrayList<PostEntity>>
}