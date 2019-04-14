package com.example.babylon.postlist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.example.babylon.R
import com.example.babylon.core.*
import com.example.babylon.postlist.viewmodels.PostListViewState
import com.example.babylon.postlist.viewmodels.PostsListViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_posts_list.*
import javax.inject.Inject

class PostsListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: PostsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_posts_list, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(viewModelFactory) {
            observe(postListViewState, ::onPostListViewState)
        }
    }

    override fun onResume() {
        super.onResume()
        if (::viewModel.isInitialized) viewModel.fetchPosts()
    }

    private fun onPostListViewState(viewState: PostListViewState?) {
        when (viewState) {
            PostListViewState.Loading -> {
                pb_loading_list.visible()
            }
            is PostListViewState.Error -> {
                pb_loading_list.gone()
            }
            is PostListViewState.Success -> {
                pb_loading_list.gone()
            }
        }
    }
}