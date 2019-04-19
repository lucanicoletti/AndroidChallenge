package com.example.babylon.postlist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.babylon.core.BaseViewModel
import com.example.babylon.postlist.mappers.PostsMapper
import com.example.babylon.postlist.models.Post
import com.example.domain.models.PostDomainModel
import com.example.domain.usecases.PostsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsListViewModel @Inject constructor(
    private val postsUseCase: PostsUseCase,
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
        lastDisposable = postsUseCase.getPots()
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