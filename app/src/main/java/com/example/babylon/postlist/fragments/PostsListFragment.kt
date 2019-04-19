package com.example.babylon.postlist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.babylon.R
import com.example.babylon.core.*
import com.example.babylon.core.views.TileSnackBar
import com.example.babylon.postlist.adapter.PostListItemAdapter
import com.example.babylon.postlist.models.Post
import com.example.babylon.postlist.viewmodels.PostListViewState
import com.example.babylon.postlist.viewmodels.PostsListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_posts_list.*
import javax.inject.Inject


class PostsListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: PostsListViewModel

    private var adapter: PostListItemAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_posts_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = getViewModel(viewModelFactory) {
            observe(postListViewState, ::onPostListViewState)
            //fetchPosts()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private var postAdapterClickListener = object : PostListItemAdapter.OnPostClickListener {
        override fun onPostClicked(post: Post) {
            val navDirections = PostsListFragmentDirections
                .actionPostsListFragmentToPostDetailFragment(post)
            navigateTo(navDirections)
        }
    }

    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rv_posts.layoutManager = layoutManager

        adapter = PostListItemAdapter(emptyList(), postAdapterClickListener)
        rv_posts.adapter = adapter
    }

    private fun onPostListViewState(viewState: PostListViewState?) {
        when (viewState) {
            PostListViewState.Loading -> {
                pb_loading_list.visible()
            }
            is PostListViewState.Error -> {
                pb_loading_list.gone()
                showErrorMessageWithRetry()
            }
            is PostListViewState.Success -> {
                pb_loading_list.gone()
                adapter?.updatePosts(viewState.list)
            }
        }
    }

    private fun showErrorMessageWithRetry() {
        val errorSnackBar = TileSnackBar.make(
            view = fl_container,
            title = R.string.error_loading_list_title,
            message = R.string.error_loading_list_message,
            mainButtonText = R.string.retry,
            duration = Snackbar.LENGTH_INDEFINITE,
            actionListener = View.OnClickListener {
                viewModel.fetchPosts()
            },
            type = TileSnackBar.TYPE_ERROR
        )
        errorSnackBar.hideCloseIcon()
        errorSnackBar.show()
    }
}