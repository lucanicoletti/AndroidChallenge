package com.lnicolet.presentation.postlist

import com.lnicolet.presentation.base.BaseIntent

sealed class PostListIntent: BaseIntent {
    object InitialIntent: PostListIntent()
    object ReloadIntent: PostListIntent()
    object LoadPostListIntent: PostListIntent()
}