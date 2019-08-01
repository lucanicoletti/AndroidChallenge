package com.lnicolet.presentation.postdetail

import com.lnicolet.presentation.base.BaseIntent
import com.lnicolet.presentation.postlist.model.User

sealed class PostDetailIntent : BaseIntent {
    object InitialIntent: PostDetailIntent()
    class LoadComments(val withUserLoaded: User): PostDetailIntent()
    object LoadEverything: PostDetailIntent()
}