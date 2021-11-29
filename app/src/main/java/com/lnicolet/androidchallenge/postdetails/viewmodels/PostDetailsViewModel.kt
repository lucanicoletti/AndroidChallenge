package com.lnicolet.androidchallenge.postdetails.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lnicolet.data_lib.DispatcherProvider
import com.lnicolet.androidchallenge.postdetails.mappers.CommentMapper
import com.lnicolet.androidchallenge.postdetails.mappers.PostDetailMapper
import com.lnicolet.androidchallenge.postdetails.models.Comment
import com.lnicolet.androidchallenge.postdetails.models.User
import com.lnicolet.domain_lib.models.CommentDomainModel
import com.lnicolet.domain_lib.models.PostDetailDomainModel
import com.lnicolet.domain_lib.usecases.CommentsAndUserUseCase
import com.lnicolet.domain_lib.usecases.CommentsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(
    private val commentsAndUserUseCase: CommentsAndUserUseCase,
    private val commentsUseCase: CommentsUseCase,
    private val postDetailMapper: PostDetailMapper,
    private val commentMapper: CommentMapper,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _postDetailsViewState = MutableLiveData<PostDetailsViewState>()

    val postDetailsViewState: LiveData<PostDetailsViewState>
        get() = _postDetailsViewState

    fun loadCommentsAndUserData(userId: Int, postId: Int) {
        viewModelScope.launch(dispatcher.io()) {
            _postDetailsViewState.postValue(PostDetailsViewState.Loading)
            try {
                val commentsAndUser = commentsAndUserUseCase.getCommentsAndUser(CommentsAndUserUseCase.Params(userId, postId))
                onCommentsAndUserSucceed(commentsAndUser)
            } catch (exception: Exception) {
                onCommentsAndUserFailed(exception)
            }
        }
    }

    fun loadComments(postId: Int) {
        viewModelScope.launch(dispatcher.io()) {
            _postDetailsViewState.postValue(PostDetailsViewState.LoadingCommentsOnly)
            try {
                val comments = commentsUseCase.getCommentsByPostId(postId)
                onCommentsSucceed(comments)
            } catch (exception: Exception) {
                onCommentsFailed(exception)
            }
        }
    }

    private fun onCommentsFailed(throwable: Throwable) {
        _postDetailsViewState.postValue(PostDetailsViewState.ErrorComments(throwable.localizedMessage.orEmpty()))
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
        _postDetailsViewState.postValue(PostDetailsViewState.ErrorBoth(throwable.localizedMessage.orEmpty()))
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