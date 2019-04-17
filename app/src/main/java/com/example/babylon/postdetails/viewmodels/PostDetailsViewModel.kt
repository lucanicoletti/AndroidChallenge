package com.example.babylon.postdetails.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.babylon.postdetails.mappers.PostDetailMapper
import com.example.babylon.postdetails.models.Comment
import com.example.babylon.postdetails.models.User
import com.example.domain.models.PostDetailDomainModel
import com.example.domain.usecases.CommentsAndUserUseCase
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(
    private val commentsAndUserUseCase: CommentsAndUserUseCase,
    private val postDetailMapper: PostDetailMapper
) : ViewModel() {

    private val _postDetailsViewState = MutableLiveData<PostDetailsViewState>()

    val postDetailsViewState: LiveData<PostDetailsViewState>
        get() = _postDetailsViewState

    fun loadCommentsAndUserData(userId: Int, postId: Int) {
        _postDetailsViewState.postValue(PostDetailsViewState.Loading)
        commentsAndUserUseCase.execute(
            {
                onCommentsAndUserSucceed(it)
            },
            {
                onCommentsAndUserFailed(it)
            },
            params = CommentsAndUserUseCase.Params(userId, postId)
        )
    }

    private fun onCommentsAndUserFailed(throwable: Throwable) {
        _postDetailsViewState.postValue(PostDetailsViewState.Error(throwable.localizedMessage))
    }

    private fun onCommentsAndUserSucceed(postDetailDomainModel: PostDetailDomainModel) {
        val postDetail = postDetailMapper.mapToPresentation(postDetailDomainModel)
        _postDetailsViewState.postValue(
            PostDetailsViewState.Success(
                postDetail.user,
                postDetail.commentList
            )
        )
    }
}

sealed class PostDetailsViewState {
    object Loading : PostDetailsViewState()
    class Error(val reason: String) : PostDetailsViewState()
    class Success(val user: User, val commentList: List<Comment>) : PostDetailsViewState()
}