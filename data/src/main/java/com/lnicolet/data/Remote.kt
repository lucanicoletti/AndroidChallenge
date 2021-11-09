package com.lnicolet.data

import com.lnicolet.data.entities.CommentEntity
import com.lnicolet.data.entities.PostEntity
import com.lnicolet.data.entities.UserEntity
import io.reactivex.Flowable
import io.reactivex.Observable
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