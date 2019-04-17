package com.example.babylon.postdetails.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.babylon.R
import com.example.babylon.core.*
import com.example.babylon.postdetails.models.Comment
import com.example.babylon.postdetails.models.User
import com.example.babylon.postdetails.view.CommentView
import com.example.babylon.postdetails.viewmodels.PostDetailsViewModel
import com.example.babylon.postdetails.viewmodels.PostDetailsViewState
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_post_detail.*
import javax.inject.Inject

class PostDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: PostDetailsViewModel


    private val args: PostDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_post_detail, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel(viewModelFactory) {
            observe(postDetailsViewState, ::onPostDetailViewState)
            loadCommentsAndUserData(
                args.post?.userId ?: -1,
                args.post?.id ?: -1
            )
        }
    }

    private fun onPostDetailViewState(postDetailsViewState: PostDetailsViewState?) {
        when (postDetailsViewState) {
            PostDetailsViewState.Loading -> {
                pb_user_loading.visible()
                pb_comments_loading.visible()
            }
            is PostDetailsViewState.Error -> {
                pb_user_loading.gone()
                pb_comments_loading.gone()
            }
            is PostDetailsViewState.Success -> {
                pb_user_loading.gone()
                pb_comments_loading.gone()
                loadUserData(postDetailsViewState.user)
                loadCommentsData(postDetailsViewState.commentList)
            }
        }
    }

    private fun loadCommentsData(commentList: List<Comment>) {
        if (commentList.isEmpty()) {
            tv_no_comments.visible()
        } else {
            tv_no_comments.gone()
            commentList.forEach { singleComment ->
                val commentView = CommentView(requireContext())
                cv_comments_container.addView(commentView)
                commentView.setupView(singleComment)
            }
        }
    }

    private fun loadUserData(user: User) {
        tv_name.text = user.name
        tv_username.text = user.userName
        tv_email.text = user.email
        tv_website.text = user.website

        GlideApp.with(this)
            .load(user.imageUrl)
            .centerCrop()
            .into(iv_user_picture)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.post?.let { post ->
            tv_title.text = post.title
            tv_body.text = post.title
        }
    }
}