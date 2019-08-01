package com.lnicolet.babylonandroidchallenge.postdetails.activities

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lnicolet.babylonandroidchallenge.R
import com.lnicolet.babylonandroidchallenge.core.*
import com.lnicolet.babylonandroidchallenge.core.views.TileSnackBar
import com.lnicolet.babylonandroidchallenge.idlingresources.EspressoIdlingResource
import com.lnicolet.presentation.postdetail.model.Comment
import com.lnicolet.presentation.postlist.model.User
import com.lnicolet.babylonandroidchallenge.postdetails.view.CommentView
import com.lnicolet.babylonandroidchallenge.postdetails.viewmodels.PostDetailsViewModel
import com.lnicolet.babylonandroidchallenge.postdetails.viewmodels.PostDetailsViewState
import com.lnicolet.presentation.postlist.model.Post
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_post_details.*
import javax.inject.Inject

/**
 * Created by Luca Nicoletti
 * on 20/04/2019
 */

class PostDetailsActivity : AppCompatActivity() {

    companion object {
        const val ARG_POST = "ARG_POST"

        fun getIntent(context: Context, post: Post): Intent =
            Intent(context, PostDetailsActivity::class.java).apply {
                putExtra(ARG_POST, post)
            }
    }

    @VisibleForTesting
    val userIdlingResource by lazy { EspressoIdlingResource("USER_IDLING") }
    @VisibleForTesting
    val commentsIdlingResource by lazy { EspressoIdlingResource("COMMENTS_IDLING") }
    @VisibleForTesting
    val userLoadingIdlingResource by lazy { EspressoIdlingResource("USER_LOADING_IDLING") }
    @VisibleForTesting
    val commentsLoadingIdlingResource by lazy { EspressoIdlingResource("COMMENTS_LOADING_IDLING") }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PostDetailsViewModel

    private var post: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        intent.extras?.let { extra ->
            if (intent.hasExtra(ARG_POST)) {
                post = extra.getParcelable(ARG_POST)
            } else {
                finish()
            }
        } ?: finish() // safely go back

        // for tests
        userIdlingResource.increment()
        userLoadingIdlingResource.increment()
        commentsIdlingResource.increment()
        commentsLoadingIdlingResource.increment()

        setContentView(R.layout.activity_post_details)
        setupViews()
        setupViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    private fun setupViews() {
        tv_body.text = post?.body.orEmpty()
        tv_title.text = post?.title.orEmpty()
    }

    private fun setupViewModel() {
        viewModel = getViewModel(viewModelFactory) {
            observe(postDetailsViewState, ::onPostDetailViewState)
            post?.user?.let {
                loadComments(post?.id ?: 1)
                loadUserData(it)
            } ?: run {
                supportPostponeEnterTransition()
                loadCommentsAndUserData(post?.userId ?: -1, post?.id ?: -1)
            }
        }
    }

    private fun onPostDetailViewState(postDetailsViewState: PostDetailsViewState?) {
        when (postDetailsViewState) {
            is PostDetailsViewState.SuccessBoth -> {
                manageViewsVisibilityForSuccessState()
                loadCommentsData(postDetailsViewState.commentList)
                loadUserData(postDetailsViewState.user)
            }
            is PostDetailsViewState.ErrorBoth -> {
                manageViewsVisibilityForErrorState()
                showErrorMessageWithRetryBoth()
            }
            is PostDetailsViewState.SuccessComments -> {
                manageViewsVisibilityForSuccessState()
                loadCommentsData(postDetailsViewState.commentList)
            }
            is PostDetailsViewState.ErrorComments -> {
                manageViewsVisibilityForErrorState()
                showErrorMessageWithRetryComments()
            }
            PostDetailsViewState.LoadingCommentsOnly -> {
                commentsLoadingIdlingResource.decrement()
                manageViewsVisibilityForLoadingCommentsOnlyState()
            }
            PostDetailsViewState.Loading -> {
                userLoadingIdlingResource.decrement()
                commentsLoadingIdlingResource.decrement()
                manageViewsVisibilityForLoadingState()
            }
        }
    }

    private fun manageViewsVisibilityForErrorState() {
        pb_user_loading.gone()
        tv_about_the_user.gone()
        tv_comments.gone()
        g_user_info.gone()
        pb_comments_loading.gone()
    }

    private fun manageViewsVisibilityForLoadingState() {
        pb_user_loading.visible()
        pb_comments_loading.visible()
        g_user_info.gone()
        tv_comments.visible()
        tv_about_the_user.visible()
    }

    private fun manageViewsVisibilityForLoadingCommentsOnlyState() {
        pb_comments_loading.visible()
        tv_comments.visible()
        tv_about_the_user.visible()
    }

    private fun manageViewsVisibilityForSuccessState() {
        pb_user_loading.gone()
        pb_comments_loading.gone()
        tv_comments.visible()
        g_user_info.visible()
    }

    private fun loadCommentsData(commentList: List<Comment>) {
        if (commentList.isEmpty()) {
            tv_no_comments.visible()
        } else {
            tv_no_comments.gone()
            commentList.forEach { singleComment ->
                val commentView = CommentView(this)
                cv_comments_container.addView(commentView)
                commentView.setupView(singleComment)
            }
        }
        commentsIdlingResource.decrement()
    }

    private fun loadUserData(user: User) {
        tv_name.text = user.name
        tv_username.text = user.userName
        tv_email.text = user.email
        tv_website.text = user.website
        g_user_info.visible()
        pb_user_loading.gone()
        GlideApp.with(this)
            .load(user.imageUrl)
            .centerCrop()
            .listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    supportStartPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    supportStartPostponedEnterTransition()
                    return false
                }

            })
            .into(iv_user_picture)
        userIdlingResource.decrement()
    }

    private fun showErrorMessageWithRetryBoth() {
        val errorSnackBar = TileSnackBar.make(
            view = cl_main_container,
            title = R.string.error_loading_details,
            mainButtonText = R.string.retry,
            duration = Snackbar.LENGTH_INDEFINITE,
            actionListener = View.OnClickListener {
                viewModel.loadCommentsAndUserData(
                    post?.userId ?: -1,
                    post?.id ?: -1
                )
            },
            type = TileSnackBar.TYPE_ERROR
        )
        errorSnackBar.showCloseIcon()
        errorSnackBar.show()
    }

    private fun showErrorMessageWithRetryComments() {
        val errorSnackBar = TileSnackBar.make(
            view = cl_main_container,
            title = R.string.error_loading_details,
            mainButtonText = R.string.retry,
            duration = Snackbar.LENGTH_INDEFINITE,
            actionListener = View.OnClickListener {
                // for tests
                userIdlingResource.increment()
                userLoadingIdlingResource.increment()
                commentsIdlingResource.increment()
                commentsLoadingIdlingResource.increment()

                viewModel.loadComments(post?.id ?: -1)
            },
            type = TileSnackBar.TYPE_ERROR
        )
        errorSnackBar.showCloseIcon()
        errorSnackBar.show()
    }

}