package com.lnicolet.presentation.postlist

import com.lnicolet.presentation.base.BaseResult
import com.lnicolet.presentation.base.TaskStatus
import com.lnicolet.presentation.postlist.model.Post

sealed class PostListResult : BaseResult {

    class LoadPostListTask(
        val status: TaskStatus,
        val postList: List<Post>? = null
    ) : PostListResult() {

        companion object {
            internal fun success(postList: List<Post>) =
                LoadPostListTask(TaskStatus.SUCCESS, postList)

            internal fun failure() =
                LoadPostListTask(TaskStatus.FAILURE)

            internal fun loading() =
                LoadPostListTask(TaskStatus.LOADING)
        }
    }
}