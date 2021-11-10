package com.lnicolet.androidchallenge.di

import android.app.Application
import com.lnicolet.androidchallenge.ChallengeApp
import com.lnicolet.androidchallenge.di.modules.ActivitiesModule
import com.lnicolet.androidchallenge.di.modules.ApiModule
import com.lnicolet.androidchallenge.di.modules.AppModule
import com.lnicolet.androidchallenge.di.modules.CoreModule
import com.lnicolet.androidchallenge.di.modules.RepositoryModule
import com.lnicolet.androidchallenge.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Suppress("unused")
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivitiesModule::class,
        ViewModelModule::class,
        AppModule::class,
        ApiModule::class,
        RepositoryModule::class,
        CoreModule::class
    ]
)
interface Injector : AndroidInjector<ChallengeApp> {
    @Component.Builder
    interface Builder {
        fun build(): Injector

        @BindsInstance
        fun application(application: Application): Builder
    }

    fun inject(application: Application)
}