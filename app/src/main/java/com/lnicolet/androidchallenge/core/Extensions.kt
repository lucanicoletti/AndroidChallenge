package com.lnicolet.androidchallenge.core

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding

inline fun <reified T : ViewModel> Fragment.getViewModel(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val viewModel = ViewModelProvider(this, factory)[T::class.java]
    viewModel.body()
    return viewModel
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val viewModel = ViewModelProvider(this, factory)[T::class.java]
    viewModel.body()
    return viewModel
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}


infix fun <View, B> View.transitionTo(that: B): android.util.Pair<View, B> = android.util.Pair(this, that)

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * Layout inflater from Context.
 */
inline val Context.inflater: LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }