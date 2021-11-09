package com.lnicolet.androidchallenge.postlist.models

import android.os.Parcelable
import com.lnicolet.androidchallenge.postdetails.models.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    var user: User? = null
) : Parcelable