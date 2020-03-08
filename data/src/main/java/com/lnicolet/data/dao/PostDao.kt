package com.lnicolet.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lnicolet.data.entities.PostEntity
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface PostDao {
    @Query("SELECT * from post_table")
    fun getPosts(): Observable<List<PostEntity>>

    @Query("SELECT * from post_table WHERE userId = :id")
    fun getPostsByUserId(id: Int): Single<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(addressEntity: PostEntity)

    @Query("DELETE FROM post_table")
    fun deleteAll()
}