package com.example.babylon.postdetails.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babylon.core.BaseViewModel
import com.example.babylon.postdetails.mappers.CommentMapper
import com.example.babylon.postdetails.mappers.PostDetailMapper
import com.example.babylon.postdetails.models.Comment
import com.example.babylon.postdetails.models.User
import com.example.domain.models.CommentDomainModel
import com.example.domain.models.PostDetailDomainModel
import com.example.domain.usecases.CommentsAndUserUseCase
import com.example.domain.usecases.CommentsUseCase
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
            commentMapper.mapToPresentation(it)
        }
        _postDetailsViewState.postValue(
            PostDetailsViewState.SuccessComments(comments)
        )
    }

    private fun onCommentsAndUserFailed(throwable: Throwable) {
        _postDetailsViewState.postValue(PostDetailsViewState.ErrorBoth(throwable.localizedMessage))
    }

    private fun onCommentsAndUserSucceed(postDetailDomainModel: PostDetailDomainModel) {
        val postDetail = postDetailMapper.mapToPresentation(postDetailDomainModel)
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