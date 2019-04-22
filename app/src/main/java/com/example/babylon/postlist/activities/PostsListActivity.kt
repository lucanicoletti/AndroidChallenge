package com.example.babylon.postlist.activities

import android.app.ActivityOptions
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.babylon.R
import com.example.babylon.core.*
import com.example.babylon.core.views.TileSnackBar
import com.example.babylon.idlingresources.EspressoIdlingResource
import com.example.babylon.postdetails.activities.PostDetailsActivity
import com.example.babylon.postlist.adapter.PostListItemAdapter
import com.example.babylon.postlist.models.Post
import com.example.babylon.postlist.viewmodels.PostListViewState
import com.example.babylon.postlist.viewmodels.PostsListViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.posts_list_activity.*
import javax.inject.Inject


class PostsListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PostsListViewModel

    @VisibleForTesting
    val loadingIdlingResource: EspressoIdlingResource by lazy { EspressoIdlingResource("LOADING_LIST") }
    @VisibleForTesting
    val postsListIdlingResource: EspressoIdlingResource by lazy { EspressoIdlingResource("LOADING_VISIBILITY") }


    private var onPostClickListener = object : PostListItemAdapter.OnPostClickListener {
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

    private val adapter = PostListItemAdapter(listOf(), onPostClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        loadingIdlingResource.increment()
        postsListIdlingResource.increment()
        setContentView(com.example.babylon.R.layout.posts_list_activity)
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rv_posts.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_posts.adapter = adapter
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
                pb_loading_list.visible()
                rv_posts.gone()
            }
            is PostListViewState.Success -> {
                pb_loading_list.gone()
                rv_posts.visible()
                adapter.updatePosts(viewState.list)
                postsListIdlingResource.decrement()
            }
            is PostListViewState.Error -> {
                pb_loading_list.gone()
                rv_posts.gone()
                showErrorMessage()
            }
        }
    }

    private fun showErrorMessage() {
        val tileSnackbar = TileSnackBar.make(
            view = fl_container,
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
