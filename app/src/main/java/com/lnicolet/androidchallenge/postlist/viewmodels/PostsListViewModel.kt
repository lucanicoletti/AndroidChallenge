package com.lnicolet.androidchallenge.postlist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lnicolet.data_lib.DispatcherProvider
import com.lnicolet.androidchallenge.postlist.mappers.PostsMapper
import com.lnicolet.androidchallenge.postlist.models.Post
import com.lnicolet.domain_lib.models.PostDomainModel
import com.lnicolet.domain_lib.usecases.PostsAndUsersUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostsListViewModel @Inject constructor(
    private val postsAndUsersUseCase: PostsAndUsersUseCase,
    private val postsMapper: PostsMapper,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _postsListViewState = MutableLiveData<PostListViewState>()

    val postListViewState: LiveData<PostListViewState>
        get() = _postsListViewState

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch(dispatcher.io()) {
            _postsListViewState.postValue(PostListViewState.Loading)
            try {
                val postAndUser = postsAndUsersUseCase.getPostsWithUsers()
                onPostsListSucceed(postAndUser)
            } catch (exception: Exception) {
                onPostsListError(exception)
            }
        }
    }

    private fun onPostsListSucceed(postList: List<PostDomainModel>) {
        _postsListViewState.postValue(
            PostListViewState.Success(postsMapper.mapToPresentation(postList))
        )
    }

    private fun onPostsListError(error: Throwable) {
        _postsListViewState.postValue(PostListViewState.Error(error.localizedMessage.orEmpty()))
    }

}

sealed class PostListViewState {
    object Loading : PostListViewState()
    data class Error(val reason: String) : PostListViewState()
    data class Success(val list: List<Post>) : PostListViewState()
}