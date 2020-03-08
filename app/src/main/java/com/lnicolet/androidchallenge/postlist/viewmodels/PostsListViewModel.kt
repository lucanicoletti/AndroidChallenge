package com.lnicolet.androidchallenge.postlist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lnicolet.androidchallenge.core.BaseViewModel
import com.lnicolet.androidchallenge.postlist.mappers.PostsMapper
import com.lnicolet.androidchallenge.postlist.models.Post
import com.lnicolet.domain.models.PostDomainModel
import com.lnicolet.domain.usecases.PostsAndUsersUseCase
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

    fun fetchPosts() {
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
            PostListViewState.Success(postsMapper.mapToPresentation(postList))
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