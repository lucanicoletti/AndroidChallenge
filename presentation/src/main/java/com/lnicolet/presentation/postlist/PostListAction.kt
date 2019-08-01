package com.lnicolet.presentation.postlist

import com.lnicolet.presentation.base.BaseAction

sealed class PostListAction: BaseAction {
    object  LoadPostList: PostListAction()
}