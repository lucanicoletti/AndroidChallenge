package com.example.babylon.postlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.babylon.R
import com.example.babylon.core.GlideApp
import com.example.babylon.core.gone
import com.example.babylon.core.visible
import com.example.babylon.postlist.models.Post
import kotlinx.android.synthetic.main.view_list_item_post.view.*

/**
 * Created by Luca Nicoletti
 * on 15/04/2019
 */

class PostListItemAdapter(
    private var postList: List<Post>,
    private val postClickListener: OnPostClickListener
) : RecyclerView.Adapter<PostListItemAdapter.ViewHolder>() {

    interface OnPostClickListener {
        fun onPostClicked(post: Post)
    }

    fun updatePosts(newList: List<Post>) {
        this.postList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_list_item_post, parent, false)
        )

    override fun getItemCount(): Int = postList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = postList[position]
        holder.itemView.tv_title.text = post.title
        holder.itemView.tv_partial_body.text = post.body
        holder.itemView.fl_post_item_container.setOnClickListener {
            postClickListener.onPostClicked(post)
        }
        post.user?.let { postCreator ->
            holder.itemView.g_user_info.visible()
            GlideApp.with(holder.itemView)
                .load(postCreator.imageUrl)
                .into(holder.itemView.iv_user_picture)
            holder.itemView.tv_user_name.text = postCreator.userName
        } ?: run {
            holder.itemView.g_user_info.gone()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}