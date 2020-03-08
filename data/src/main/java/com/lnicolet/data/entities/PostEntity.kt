package com.lnicolet.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class PostEntity(
    @PrimaryKey var id: Int,
    var userId: Int,
    var title: String,
    var body: String
)