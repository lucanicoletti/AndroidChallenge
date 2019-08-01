package com.lnicolet.presentation.postdetail

import com.lnicolet.presentation.base.BaseAction
import com.lnicolet.presentation.postlist.model.User

sealed class PostDetailAction: BaseAction {

    object LoadCommentsAndUser: PostDetailAction()
    class LoadCommentsOnly(val user: User): PostDetailAction()
}