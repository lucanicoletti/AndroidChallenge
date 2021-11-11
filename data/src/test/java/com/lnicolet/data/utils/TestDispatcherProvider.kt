package com.lnicolet.data.utils

import com.lnicolet.data.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestDispatcherProvider : DispatcherProvider {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    override fun main(): CoroutineDispatcher = testCoroutineDispatcher

    override fun default(): CoroutineDispatcher = testCoroutineDispatcher

    override fun io(): CoroutineDispatcher = testCoroutineDispatcher

    override fun unconfined(): CoroutineDispatcher = testCoroutineDispatcher
}