package com.lnicolet.babylonandroidchallenge.postlist.items

import android.os.Build
import android.view.View
import com.lnicolet.babylonandroidchallenge.R
import com.lnicolet.babylonandroidchallenge.core.GlideApp
import com.lnicolet.babylonandroidchallenge.core.gone
import com.lnicolet.babylonandroidchallenge.core.visible
import com.lnicolet.babylonandroidchallenge.postlist.models.Post
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.view_list_item_post.view.*

class PostListItem(
    private val post: Post,
    private val postClickListener: OnPostClickListener
) : Item() {

    interface OnPostClickListener {
        fun onPostClicked(
            post: Post,
            imageView: View? = null,
            title: View? = null,
            body: View? = null
        )
    }

    override fun getLayout() = R.layout.view_list_item_post

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tv_title.text = post.title
        viewHolder.itemView.tv_partial_body.text = post.body
        viewHolder.itemView.fl_post_item_container.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                postClickListener.onPostClicked(
                    post,
                    imageView = viewHolder.itemView.cv_image_container,
                    title = viewHolder.itemView.tv_title,
                    body = viewHolder.itemView.tv_partial_body
                )
            } else {
                postClickListener.onPostClicked(post)
            }
        }
        post.user?.let { postCreator ->
            viewHolder.itemView.g_user_info.visible()
            GlideApp.with(viewHolder.itemView)
                .load(postCreator.imageUrl)
                .into(viewHolder.itemView.iv_user_picture)
            viewHolder.itemView.tv_user_name.text = postCreator.userName
        } ?: run {
            viewHolder.itemView.g_user_info.gone()
        }
    }
}
