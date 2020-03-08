package com.lnicolet.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lnicolet.data.entities.CommentEntity

@Dao
interface CommentsDao {
    @Query("SELECT * from comment_table WHERE postId = :id")
    fun getCommentsByPostId(id: Int): List<CommentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(addressEntity: CommentEntity)

    @Query("DELETE FROM comment_table")
    fun deleteAll()

}