package com.lnicolet.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comment_table")
data class CommentEntity(
    @PrimaryKey var id: Int,
    var postId: Int,
    var name: String,
    var email: String,
    var body: String
)