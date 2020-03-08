package com.lnicolet.androidchallenge.postlist.items

import android.os.Build
import android.view.View
import com.bumptech.glide.request.RequestOptions
import com.lnicolet.androidchallenge.R
import com.lnicolet.androidchallenge.core.GlideApp
import com.lnicolet.androidchallenge.core.gone
import com.lnicolet.androidchallenge.core.visible
import com.lnicolet.androidchallenge.postlist.models.Post
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_post_details.view.*
import kotlinx.android.synthetic.main.view_comment.view.*
import kotlinx.android.synthetic.main.view_list_item_post.view.*
import kotlinx.android.synthetic.main.view_list_item_post.view.iv_user_picture
import kotlinx.android.synthetic.main.view_list_item_post.view.tv_title

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

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tv_title.text = post.title
        viewHolder.itemView.tv_partial_body.text = post.body
        viewHolder.itemView.fl_post_item_container.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                postClickListener.onPostClicked(
                    post,
                    imageView = viewHolder.itemView.iv_user_picture,
                    title = viewHolder.itemView.tv_title,
                    body = viewHolder.itemView.tv_partial_body
                )
            } else {
                postClickListener.onPostClicked(post)
            }
        }
        post.user?.let { postCreator ->
            viewHolder.itemView.iv_user_picture.visible()
            GlideApp.with(viewHolder.itemView)
                .load(postCreator.imageUrl)
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .into(viewHolder.itemView.iv_user_picture)
        } ?: run {
            viewHolder.itemView.iv_user_picture.gone()
        }
    }
}
