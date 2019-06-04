package com.lnicolet.babylonandroidchallenge.postlist.models

import android.os.Parcelable
import com.lnicolet.babylonandroidchallenge.postdetails.models.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    var user: User? = null
) : Parcelable