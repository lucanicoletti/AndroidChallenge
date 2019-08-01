package com.lnicolet.presentation.postlist

import com.lnicolet.presentation.base.BaseViewState
import com.lnicolet.presentation.postlist.model.Post

sealed class PostListViewState(
    val inProgress: Boolean = false,
    val postList: List<Post>? = null
) : BaseViewState {

    object InProgress : PostListViewState(true, null)

    object Failed : PostListViewState()

    data class Success(private val result: List<Post>) :
        PostListViewState(postList = result)

    object Idle : PostListViewState(postList = null)
}