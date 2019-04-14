package com.example.babylon.di.modules

import com.example.babylon.MainActivity
import com.example.babylon.di.scopes.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    internal abstract fun contributeMainActivityInjector(): MainActivity

}