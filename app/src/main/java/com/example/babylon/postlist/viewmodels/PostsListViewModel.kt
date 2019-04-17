package com.example.babylon.postlist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.babylon.postlist.mappers.PostsMapper
import com.example.babylon.postlist.models.Post
import com.example.domain.models.PostDomainModel
import com.example.domain.usecases.PostsUseCase
import javax.inject.Inject

class PostsListViewModel @Inject constructor(
    private val postsUseCase: PostsUseCase,
    private val postsMapper: PostsMapper
) : ViewModel() {

    private val _postsListViewState = MutableLiveData<PostListViewState>()

    val postListViewState: LiveData<PostListViewState>
        get() = _postsListViewState

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        _postsListViewState.postValue(PostListViewState.Loading)
        postsUseCase.execute(
            {
                onPostsListSucceed(it)
            },
            {
                onPostsListError(it)
            },
            params = Unit
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
    class Error(val reason: String) : PostListViewState()
    class Success(val list: List<Post>) : PostListViewState()
}