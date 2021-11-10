package com.lnicolet.androidchallenge.di.modules

import com.lnicolet.androidchallenge.core.DefaultDispatcherProvider
import com.lnicolet.androidchallenge.core.DispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}