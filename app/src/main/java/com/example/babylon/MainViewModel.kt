package com.example.babylon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject


class MainViewModel @Inject constructor() :
    ViewModel() {

    private val _mainViewState = MutableLiveData<MainViewState>()

    val mainViewState: LiveData<MainViewState>
        get() = _mainViewState

    init {
        _mainViewState.postValue(MainViewState.PostsList)
    }

    fun onPostClicked(postId: Int) {
        _mainViewState.postValue(MainViewState.PostDetail(postId))
    }

    fun onBackPressed() {
        if (_mainViewState.value is MainViewState.PostDetail) {
            _mainViewState.postValue(MainViewState.PostsList)
        } else {
            _mainViewState.postValue(MainViewState.Quit)
        }
    }

}

sealed class MainViewState {
    object Quit : MainViewState()
    object PostsList : MainViewState()
    class PostDetail(val postId: Int) : MainViewState()
}