package com.lnicolet.babylonandroidchallenge.postdetails.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lnicolet.babylonandroidchallenge.core.BaseViewModel
import com.lnicolet.presentation.postdetail.mapper.CommentMapper
import com.lnicolet.presentation.postdetail.mapper.PostDetailMapper
import com.lnicolet.presentation.postdetail.model.Comment
import com.lnicolet.presentation.postlist.model.User
import com.lnicolet.domain.model.CommentDomainModel
import com.lnicolet.domain.model.PostDetailDomainModel
import com.lnicolet.domain.usecase.CommentsAndUserUseCase
import com.lnicolet.domain.usecase.CommentsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(
    private val commentsAndUserUseCase: CommentsAndUserUseCase,
    private val commentsUseCase: CommentsUseCase,
    private val postDetailMapper: PostDetailMapper,
    private val commentMapper: CommentMapper
) : BaseViewModel() {

    private val _postDetailsViewState = MutableLiveData<PostDetailsViewState>()

    val postDetailsViewState: LiveData<PostDetailsViewState>
        get() = _postDetailsViewState

    override fun onCleared() {
        super.onCleared()
        disposeAll()
    }

    fun loadCommentsAndUserData(userId: Int, postId: Int) {
        lastDisposable =
            commentsAndUserUseCase.getCommentsAndUser(CommentsAndUserUseCase.Params(userId, postId))
                .doOnSubscribe {
                    _postDetailsViewState.postValue(PostDetailsViewState.Loading)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.delay(3, TimeUnit.SECONDS)
                .subscribe(
                    {
                        onCommentsAndUserSucceed(it)
                    },
                    {
                        onCommentsAndUserFailed(it)
                    }
                )
    }

    fun loadComments(postId: Int) {
        lastDisposable =
            commentsUseCase.getCommentsByPostId(postId)
                .doOnSubscribe {
                    _postDetailsViewState.postValue(PostDetailsViewState.LoadingCommentsOnly)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.delay(3, TimeUnit.SECONDS)
                .subscribe(
                    {
                        onCommentsSucceed(it)
                    },
                    {
                        onCommentsFailed(it)
                    }
                )
    }

    private fun onCommentsFailed(throwable: Throwable) {
        _postDetailsViewState.postValue(PostDetailsViewState.ErrorComments(throwable.localizedMessage))
    }

    private fun onCommentsSucceed(commentsList: List<CommentDomainModel>) {
        val comments = commentsList.map {
            commentMapper.mapToView(it)
        }
        _postDetailsViewState.postValue(
            PostDetailsViewState.SuccessComments(comments)
        )
    }

    private fun onCommentsAndUserFailed(throwable: Throwable) {
        _postDetailsViewState.postValue(PostDetailsViewState.ErrorBoth(throwable.localizedMessage))
    }

    private fun onCommentsAndUserSucceed(postDetailDomainModel: PostDetailDomainModel) {
        val postDetail = postDetailMapper.mapToView(postDetailDomainModel)
        _postDetailsViewState.postValue(
            PostDetailsViewState.SuccessBoth(
                postDetail.user,
                postDetail.commentList
            )
        )
    }
}

sealed class PostDetailsViewState {
    object Loading : PostDetailsViewState()
    object LoadingCommentsOnly : PostDetailsViewState()
    data class ErrorBoth(val reason: String) : PostDetailsViewState()
    data class SuccessBoth(val user: User, val commentList: List<Comment>) : PostDetailsViewState()
    data class ErrorComments(val reason: String) : PostDetailsViewState()
    data class SuccessComments(val commentList: List<Comment>) : PostDetailsViewState()
}