package com.lnicolet.babylonandroidchallenge.postlist.activities

import android.app.ActivityOptions
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lnicolet.babylonandroidchallenge.R
import com.lnicolet.babylonandroidchallenge.core.*
import com.lnicolet.babylonandroidchallenge.core.views.TileSnackBar
import com.lnicolet.babylonandroidchallenge.idlingresources.EspressoIdlingResource
import com.lnicolet.babylonandroidchallenge.postdetails.activities.PostDetailsActivity
import com.lnicolet.babylonandroidchallenge.postlist.items.PostListItem
import com.lnicolet.babylonandroidchallenge.postlist.models.Post
import com.lnicolet.babylonandroidchallenge.postlist.viewmodels.PostListViewState
import com.lnicolet.babylonandroidchallenge.postlist.viewmodels.PostsListViewModel
import com.xwray.groupie.GroupAdapter
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


    private var onPostClickListener = object : PostListItem.OnPostClickListener {
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

    private val adapter = GroupAdapter<com.xwray.groupie.kotlinandroidextensions.ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        loadingIdlingResource.increment()
        postsListIdlingResource.increment()
        setContentView(R.layout.posts_list_activity)
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
                viewState.list.forEach {
                    adapter.add(
                        PostListItem(
                            it,
                            onPostClickListener
                        )
                    )
                }
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
