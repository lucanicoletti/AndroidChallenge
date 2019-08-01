package com.lnicolet.presentation.postdetail

import com.lnicolet.presentation.base.BaseViewState
import com.lnicolet.presentation.postdetail.model.PostDetail

sealed class PostDetailViewState(
    val inProgress: Boolean = false,
    val postDetail: PostDetail? = null
) : BaseViewState {

    object InProgressComments : PostDetailViewState(true, null)
    object InProgressBoth : PostDetailViewState(true, null)

    object FailedComments : PostDetailViewState()
    object FailedBoth : PostDetailViewState()

    data class SuccessComments(private val result: PostDetail) :
        PostDetailViewState(postDetail = result)

    data class SuccessBoth(private val result: PostDetail) :
        PostDetailViewState(postDetail = result)

    object Idle : PostDetailViewState(postDetail = null)

}