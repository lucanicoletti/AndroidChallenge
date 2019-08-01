package com.lnicolet.babylonandroidchallenge.postlist.activity

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
import com.lnicolet.babylonandroidchallenge.core.getViewModel
import com.lnicolet.babylonandroidchallenge.core.gone
import com.lnicolet.babylonandroidchallenge.core.transitionTo
import com.lnicolet.babylonandroidchallenge.core.views.TileSnackBar
import com.lnicolet.babylonandroidchallenge.core.visible
import com.lnicolet.babylonandroidchallenge.idlingresources.EspressoIdlingResource
import com.lnicolet.babylonandroidchallenge.postdetails.activities.PostDetailsActivity
import com.lnicolet.babylonandroidchallenge.postlist.items.PostListItem
import com.lnicolet.presentation.base.BaseView
import com.lnicolet.presentation.postlist.PostListIntent
import com.lnicolet.presentation.postlist.PostListViewModel
import com.lnicolet.presentation.postlist.PostListViewState
import com.lnicolet.presentation.postlist.model.Post
import com.xwray.groupie.GroupAdapter
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.posts_list_activity.*
import javax.inject.Inject


class PostsListActivity : AppCompatActivity(), BaseView<PostListIntent, PostListViewState> {


    @VisibleForTesting
    val loadingIdlingResource: EspressoIdlingResource by lazy { EspressoIdlingResource("LOADING_LIST") }
    @VisibleForTesting
    val postsListIdlingResource: EspressoIdlingResource by lazy { EspressoIdlingResource("LOADING_VISIBILITY") }

    private val loadPostListIntentPublisher =
        BehaviorSubject.create<PostListIntent.LoadPostListIntent>()
    private val reloadPostListIntentPublisher =
        BehaviorSubject.create<PostListIntent.ReloadIntent>()
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PostListViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun intents(): Observable<PostListIntent> {
        return Observable.merge(
            initialIntent(), loadPostListIntentPublisher,
            reloadPostListIntentPublisher
        )
    }

    override fun render(state: PostListViewState) {
        when {
            state.inProgress -> {
                loadingIdlingResource.decrement()
                pb_loading_list.visible()
                rv_posts.gone()
            }
            state is PostListViewState.Failed -> {
                pb_loading_list.gone()
                rv_posts.gone()
                showErrorMessage()
            }
            state is PostListViewState.Success -> {
                pb_loading_list.gone()
                rv_posts.visible()
                state.postList?.forEach {
                    adapter.add(
                        PostListItem(
                            it,
                            onPostClickListener
                        )
                    )
                }
                postsListIdlingResource.decrement()
            }
        }
    }


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

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun setupRecyclerView() {
        rv_posts.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_posts.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = getViewModel(viewModelFactory) {}
        compositeDisposable.add(viewModel.states().subscribe { render(it) })
        viewModel.processIntents(intents())
    }

    private fun initialIntent(): Observable<PostListIntent.InitialIntent> {
        return Observable.just(PostListIntent.InitialIntent)
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
                reloadPostListIntentPublisher.onNext(PostListIntent.ReloadIntent)
            },
            type = TileSnackBar.TYPE_ERROR
        )
        tileSnackbar.hideCloseIcon()
        tileSnackbar.show()
    }
}
