package com.lnicolet.androidchallenge.di.modules

import com.lnicolet.data_lib.DefaultDispatcherProvider
import com.lnicolet.data_lib.DispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}