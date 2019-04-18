package com.example.babylon.postdetails.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babylon.core.BaseViewModel
import com.example.babylon.postdetails.mappers.PostDetailMapper
import com.example.babylon.postdetails.models.Comment
import com.example.babylon.postdetails.models.User
import com.example.domain.models.PostDetailDomainModel
import com.example.domain.usecases.CommentsAndUserUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(
    private val commentsAndUserUseCase: CommentsAndUserUseCase,
    private val postDetailMapper: PostDetailMapper
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
    data class Error(val reason: String) : PostDetailsViewState()
    data class Success(val user: User, val commentList: List<Comment>) : PostDetailsViewState()
}