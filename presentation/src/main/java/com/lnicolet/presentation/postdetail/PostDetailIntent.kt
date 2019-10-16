package com.lnicolet.presentation.postdetail

import com.lnicolet.presentation.base.BaseIntent
import com.lnicolet.presentation.postlist.model.User

sealed class PostDetailIntent : BaseIntent {
    class LoadComments(val postId: Int, val user: User): PostDetailIntent()
    class LoadEverything(val userId: Int, val postId: Int): PostDetailIntent()
}