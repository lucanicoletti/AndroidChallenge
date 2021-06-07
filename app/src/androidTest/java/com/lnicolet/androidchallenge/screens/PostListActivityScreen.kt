package com.lnicolet.androidchallenge.screens

import android.view.View
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.progress.KProgressBar
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.lnicolet.androidchallenge.R
import org.hamcrest.Matcher

class PostListActivityScreen : Screen<PostListActivityScreen>() {
    val postListRecycler = KRecyclerView({
        withId(R.id.rv_posts)
    }, itemTypeBuilder = {
        itemType(::Item)
    })

    val loader = KProgressBar {
        withId(R.id.pb_loading_list)
    }

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val title = KTextView(parent) { withId(R.id.tv_title) }
        val partialBody = KTextView(parent) { withId(R.id.tv_partial_body) }
        val userName = KTextView(parent) { withId(R.id.tv_user_name) }
        val userImage = KImageView(parent) { withId(R.id.iv_user_picture) }
    }
}
