package com.lnicolet.babylonandroidchallenge.postlist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lnicolet.babylonandroidchallenge.core.BaseViewModel
import com.lnicolet.presentation.postlist.mapper.PostsMapper
import com.lnicolet.presentation.postlist.model.Post
import com.lnicolet.domain.model.PostDomainModel
import com.lnicolet.domain.usecase.PostsAndUsersUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsListViewModel @Inject constructor(
    private val postsAndUsersUseCase: PostsAndUsersUseCase,
    private val postsMapper: PostsMapper
) : BaseViewModel() {

    private val _postsListViewState = MutableLiveData<PostListViewState>()

    val postListViewState: LiveData<PostListViewState>
        get() = _postsListViewState

    init {
        fetchPosts()
    }

    override fun onCleared() {
        super.onCleared()
        disposeAll()
    }

    private fun fetchPosts() {
        lastDisposable = postsAndUsersUseCase.getPostsWithUsers()
            .doOnSubscribe {
                _postsListViewState.postValue(PostListViewState.Loading)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onPostsListSucceed(it)
                },
                {
                    onPostsListError(it)
                }
            )
    }

    private fun onPostsListSucceed(postList: List<PostDomainModel>) {
        _postsListViewState.postValue(
            PostListViewState.Success(postsMapper.mapToView(postList))
        )
    }

    private fun onPostsListError(error: Throwable) {
        _postsListViewState.postValue(PostListViewState.Error(error.localizedMessage))
    }

}

sealed class PostListViewState {
    object Loading : PostListViewState()
    data class Error(val reason: String) : PostListViewState()
    data class Success(val list: List<Post>) : PostListViewState()
}