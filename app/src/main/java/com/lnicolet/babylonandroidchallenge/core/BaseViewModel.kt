package com.lnicolet.babylonandroidchallenge.core

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Luca Nicoletti
 * on 18/04/2019
 */

abstract class BaseViewModel : ViewModel() {

    internal var lastDisposable: Disposable? = null
    internal val disposables = CompositeDisposable()

    fun disposeLast() {
        lastDisposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }

    fun disposeAll() {
        disposables.clear()
    }
}