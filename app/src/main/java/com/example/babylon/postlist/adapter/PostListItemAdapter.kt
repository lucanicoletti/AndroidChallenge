package com.example.babylon.postlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.babylon.R
import com.example.babylon.postlist.models.Post

/**
 * Created by Luca Nicoletti
 * on 15/04/2019
 * Monese® All rights reserved
 */

class PostListItemAdapter(
        private var postList: List<Post>,
        private val postClickListener: OnPostClickListener
) : RecyclerView.Adapter<PostListItemAdapter.ViewHolder>() {

    interface OnPostClickListener {
        fun onPostClicked(post: Post, title: View, body: View)
    }

    fun updatePosts(newList: List<Post>) {
        this.postList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_list_item_post, parent, false))

    override fun getItemCount(): Int = postList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(postList[position], postClickListener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.tv_title)
        private val body: TextView = view.findViewById(R.id.tv_partial_body)
        private val readMore: Button = view.findViewById(R.id.btn_read_more)

        fun bindView(post: Post, clickListener: OnPostClickListener) {
            title.text = post.title
            body.text = post.title
            readMore.setOnClickListener {
                clickListener.onPostClicked(post, title, body)
            }
        }
    }
}