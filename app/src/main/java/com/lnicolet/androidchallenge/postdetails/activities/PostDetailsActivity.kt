package com.lnicolet.androidchallenge.postdetails.activities

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
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.lnicolet.androidchallenge.R
import com.lnicolet.androidchallenge.core.*
import com.lnicolet.androidchallenge.core.views.TileSnackBar
import com.lnicolet.androidchallenge.idlingresources.EspressoIdlingResource
import com.lnicolet.androidchallenge.postdetails.models.Comment
import com.lnicolet.androidchallenge.postdetails.models.User
import com.lnicolet.androidchallenge.postdetails.view.CommentView
import com.lnicolet.androidchallenge.postdetails.viewmodels.PostDetailsViewModel
import com.lnicolet.androidchallenge.postdetails.viewmodels.PostDetailsViewState
import com.lnicolet.androidchallenge.postlist.models.Post
import com.google.android.material.snackbar.Snackbar
import com.lnicolet.androidchallenge.databinding.ActivityPostDetailsBinding
import dagger.android.AndroidInjection
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
    private val binding: ActivityPostDetailsBinding by viewBinding(ActivityPostDetailsBinding::inflate)
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

        setContentView(binding.root)
        setupViews()
        setupViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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
        binding.tvBody.text = post?.body.orEmpty()
        binding.tvTitle.text = post?.title.orEmpty()
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
                manageViewsVisibilityForErrorStateOnCommentsOnly()
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
        binding.pbUserLoading.invisible()
        binding.tvAboutTheUser.gone()
        binding.tvComments.gone()
        binding.gUserInfo.gone()
        binding.pbCommentsLoading.gone()
    }

    private fun manageViewsVisibilityForErrorStateOnCommentsOnly() {
        binding.tvComments.gone()
        binding.pbCommentsLoading.gone()
    }

    private fun manageViewsVisibilityForLoadingState() {
        binding.pbUserLoading.invisible()
        binding.pbCommentsLoading.visible()
        binding.gUserInfo.gone()
        binding.tvComments.visible()
        binding.tvAboutTheUser.visible()
    }

    private fun manageViewsVisibilityForLoadingCommentsOnlyState() {
        binding.pbCommentsLoading.visible()
        binding.tvComments.visible()
        binding.tvAboutTheUser.visible()
    }

    private fun manageViewsVisibilityForSuccessState() {
        binding.pbUserLoading.invisible()
        binding.pbCommentsLoading.gone()
        binding.tvComments.visible()
        binding.gUserInfo.visible()
    }

    private fun loadCommentsData(commentList: List<Comment>) {
        if (commentList.isEmpty()) {
            binding.tvNoComments.visible()
        } else {
            binding.tvNoComments.gone()
            commentList.forEach { singleComment ->
                val commentView = CommentView(this)
                binding.cvCommentsContainer.addView(commentView)
                commentView.setupView(singleComment)
            }
        }
        commentsIdlingResource.decrement()
    }

    private fun loadUserData(user: User) {
        binding.tvName.text = user.name
        binding.tvUsername.text = user.userName
        binding.tvEmail.text = user.email
        binding.tvWebsite.text = user.website
        binding.gUserInfo.visible()
        binding.pbUserLoading.invisible()
        GlideApp.with(this)
            .load(user.imageUrl)
            .centerCrop()
            .apply(RequestOptions.circleCropTransform())
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
            .into(binding.ivUserPicture)
        userIdlingResource.decrement()
    }

    private fun showErrorMessageWithRetryBoth() {
        val errorSnackBar = TileSnackBar.make(
            view = binding.clMainContainer,
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
            view = binding.clMainContainer,
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