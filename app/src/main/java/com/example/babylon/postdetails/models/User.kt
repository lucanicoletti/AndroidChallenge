package com.example.babylon.postdetails.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val userName: String,
    val email: String,
    val phone: String,
    val website: String,
    val imageUrl: String
) : Parcelable