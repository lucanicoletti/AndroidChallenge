package com.example.babylon.core

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment

inline fun <reified T : ViewModel> Fragment.getViewModel(
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

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Fragment.navigateTo(navDirections: NavDirections) {
    NavHostFragment.findNavController(this).navigate(navDirections)
}

fun Fragment.navigateWithAnimations(navDirections: NavDirections, extras: Navigator.Extras) {
    NavHostFragment.findNavController(this).navigate(navDirections, extras)
}