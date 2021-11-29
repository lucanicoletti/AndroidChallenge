package com.lnicolet.androidchallenge.postlist.items

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.lnicolet.androidchallenge.core.GlideApp
import com.lnicolet.androidchallenge.core.gone
import com.lnicolet.androidchallenge.core.inflater
import com.lnicolet.androidchallenge.core.visible
import com.lnicolet.androidchallenge.databinding.ViewListItemPostBinding
import com.lnicolet.androidchallenge.postlist.models.Post

class PostListAdapter(
    private val postList: MutableList<Post>,
    private val postClickListener: PostListViewHolder.OnPostClickListener
) : RecyclerView.Adapter<PostListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val binding = ViewListItemPostBinding.inflate(parent.context.inflater, parent, false)
        return PostListViewHolder(binding, postClickListener)
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int = postList.size

    fun addAll(posts: List<Post>) {
        this.postList.addAll(posts)
        notifyDataSetChanged()
    }

}

class PostListViewHolder(
    private val binding: ViewListItemPostBinding,
    private val postClickListener: OnPostClickListener
) : RecyclerView.ViewHolder(binding.root) {

    interface OnPostClickListener {
        fun onPostClicked(
            post: Post,
            imageView: View? = null,
            title: View? = null,
            body: View? = null
        )
    }

    fun bind(post: Post) {
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
            GlideApp.with(binding.ivUserPicture)
                .load(postCreator.imageUrl)
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivUserPicture)
        } ?: run {
            binding.ivUserPicture.gone()
        }
    }
}
