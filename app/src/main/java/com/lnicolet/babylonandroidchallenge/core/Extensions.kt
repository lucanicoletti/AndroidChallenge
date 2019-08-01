package com.lnicolet.babylonandroidchallenge.core

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*

inline fun <reified T : ViewModel> Fragment.getViewModel(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val viewModel = ViewModelProviders.of(this, factory)[T::class.java]
    viewModel.body()
    return viewModel
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val viewModel = ViewModelProviders.of(this, factory)[T::class.java]
    viewModel.body()
    return viewModel
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}


infix fun <View, B> View.transitionTo(that: B): android.util.Pair<View, B> =
    android.util.Pair(this, that)

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}