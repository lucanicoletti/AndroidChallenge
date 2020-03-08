package com.lnicolet.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_table")
data class CompanyEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String,
    var catchPhrase: String,
    var bs: String
)