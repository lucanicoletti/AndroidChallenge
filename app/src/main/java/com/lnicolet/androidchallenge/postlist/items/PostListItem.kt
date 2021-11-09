package com.lnicolet.androidchallenge.postlist.items

import android.os.Build
import android.view.View
import com.bumptech.glide.request.RequestOptions
import com.lnicolet.androidchallenge.R
import com.lnicolet.androidchallenge.core.GlideApp
import com.lnicolet.androidchallenge.core.gone
import com.lnicolet.androidchallenge.core.visible
import com.lnicolet.androidchallenge.databinding.ViewListItemPostBinding
import com.lnicolet.androidchallenge.postlist.models.Post
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder

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
    
    lateinit var binding: ViewListItemPostBinding

    override fun createViewHolder(itemView: View): GroupieViewHolder {
        binding = ViewListItemPostBinding.bind(itemView)
        return super.createViewHolder(itemView)
    }

    override fun getLayout() = R.layout.view_list_item_post

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        binding.tvTitle.text = post.title
        binding.tvPartialBody.text = post.body
        binding.flPostItemContainer.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                postClickListener.onPostClicked(
                    post,
                    imageView = binding.ivUserPicture,
                    title = binding.tvTitle,
                    body = binding.tvPartialBody
                )
            } else {
                postClickListener.onPostClicked(post)
            }
        }
        post.user?.let { postCreator ->
            binding.ivUserPicture.visible()
            GlideApp.with(viewHolder.itemView)
                .load(postCreator.imageUrl)
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivUserPicture)
        } ?: run {
            binding.ivUserPicture.gone()
        }
    }
}
