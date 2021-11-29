package com.lnicolet.androidchallenge.postlist.activities

import android.app.ActivityOptions
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lnicolet.androidchallenge.R
import com.lnicolet.androidchallenge.core.*
import com.lnicolet.androidchallenge.core.views.TileSnackBar
import com.lnicolet.androidchallenge.databinding.PostsListActivityBinding
import com.lnicolet.androidchallenge.idlingresources.EspressoIdlingResource
import com.lnicolet.androidchallenge.postdetails.activities.PostDetailsActivity
import com.lnicolet.androidchallenge.postlist.items.PostListAdapter
import com.lnicolet.androidchallenge.postlist.items.PostListViewHolder
import com.lnicolet.androidchallenge.postlist.models.Post
import com.lnicolet.androidchallenge.postlist.viewmodels.PostListViewState
import com.lnicolet.androidchallenge.postlist.viewmodels.PostsListViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject


class PostsListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PostsListViewModel

    @VisibleForTesting
    val loadingIdlingResource: EspressoIdlingResource by lazy { EspressoIdlingResource("LOADING_LIST") }

    @VisibleForTesting
    val postsListIdlingResource: EspressoIdlingResource by lazy { EspressoIdlingResource("LOADING_VISIBILITY") }


    private val binding: PostsListActivityBinding by viewBinding(PostsListActivityBinding::inflate)


    private var onPostClickListener = object : PostListViewHolder.OnPostClickListener {
        override fun onPostClicked(post: Post, imageView: View?, title: View?, body: View?) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    this@PostsListActivity,
                    imageView transitionTo imageView?.transitionName.orEmpty(),
                    title transitionTo title?.transitionName.orEmpty(),
                    body transitionTo body?.transitionName.orEmpty()
                )
                startActivity(
                    PostDetailsActivity.getIntent(this@PostsListActivity, post),
                    activityOptions.toBundle()
                )
            } else {
                startActivity(
                    PostDetailsActivity.getIntent(this@PostsListActivity, post)
                )
            }
        }

    }

    private val adapter = PostListAdapter(mutableListOf(), onPostClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        loadingIdlingResource.increment()
        postsListIdlingResource.increment()
        setContentView(binding.root)
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvPosts.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvPosts.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = getViewModel(viewModelFactory) {
            observe(postListViewState, ::onPostListViewState)
        }
    }

    private fun onPostListViewState(viewState: PostListViewState?) {
        when (viewState) {
            PostListViewState.Loading -> {
                loadingIdlingResource.decrement()
                binding.pbLoadingList.visible()
                binding.rvPosts.gone()
            }
            is PostListViewState.Success -> {
                binding.pbLoadingList.gone()
                binding.rvPosts.visible()
                adapter.addAll(viewState.list)
                postsListIdlingResource.decrement()
            }
            is PostListViewState.Error -> {
                binding.pbLoadingList.gone()
                binding.rvPosts.gone()
                showErrorMessage()
            }
        }
    }

    private fun showErrorMessage() {
        val tileSnackbar = TileSnackBar.make(
            view = binding.flContainer,
            title = R.string.error_loading_list_title,
            message = R.string.error_loading_list_message,
            mainButtonText = R.string.retry,
            duration = TileSnackBar.INDEFINITE,
            actionListener = View.OnClickListener {
                loadingIdlingResource.increment()
                postsListIdlingResource.increment()
                viewModel.fetchPosts()
            },
            type = TileSnackBar.TYPE_ERROR
        )
        tileSnackbar.hideCloseIcon()
        tileSnackbar.show()
    }
}
