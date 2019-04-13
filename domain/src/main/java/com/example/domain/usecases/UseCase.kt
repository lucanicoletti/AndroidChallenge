package com.example.domain.usecases

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Luca Nicoletti
 * on 11/04/2019
 */


abstract class UseCase {

    protected var lastDisposable: Disposable? = null
    protected val compositeDisposable = CompositeDisposable()

    fun disposeLast() {
        lastDisposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }

    fun dispose() {
        compositeDisposable.clear()
    }
}