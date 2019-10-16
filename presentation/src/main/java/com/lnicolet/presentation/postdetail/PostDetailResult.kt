package com.lnicolet.presentation.postdetail

import com.lnicolet.presentation.base.BaseResult
import com.lnicolet.presentation.base.TaskStatus
import com.lnicolet.presentation.postdetail.model.Comment
import com.lnicolet.presentation.postdetail.model.PostDetail
import com.lnicolet.presentation.postlist.model.User

sealed class PostDetailResult : BaseResult {

    class LoadPostDetailTask(
        val status: TaskStatus,
        val user: User? = null,
        val commentList: List<Comment>? = null
    ) : PostDetailResult() {

        companion object {
            internal fun bothFetched(postDetail: PostDetail) =
                LoadPostDetailTask(
                    TaskStatus.SUCCESS,
                    postDetail.user,
                    postDetail.commentList
                )

            internal fun commentsFetched(user: User, commentList: List<Comment>) =
                LoadPostDetailTask(TaskStatus.SUCCESS, commentList = commentList, user = user)

            internal fun failedComments() =
                LoadPostDetailTask(TaskStatus.FAILURE, user = null)

            internal fun failedBoth() =
                LoadPostDetailTask(TaskStatus.FAILURE, user = null)

            internal fun loadingComments(user: User) =
                LoadPostDetailTask(TaskStatus.LOADING, user = user)

            internal fun loadingBoth() =
                LoadPostDetailTask(TaskStatus.LOADING, user = null)
        }
    }
}