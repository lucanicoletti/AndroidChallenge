package com.lnicolet.presentation.postdetail

import com.lnicolet.presentation.base.BaseAction
import com.lnicolet.presentation.postlist.model.User

sealed class PostDetailAction: BaseAction {

    class LoadCommentsAndUser(val userId: Int, val postId: Int): PostDetailAction()
    class LoadCommentsOnly(val postId: Int, val user: User): PostDetailAction()
}