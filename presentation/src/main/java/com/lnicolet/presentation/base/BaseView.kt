package com.lnicolet.presentation.base

import io.reactivex.Observable

interface BaseView<I : BaseIntent, in S : BaseViewState> {
    fun intents(): Observable<I>

    fun render(state: S)
}