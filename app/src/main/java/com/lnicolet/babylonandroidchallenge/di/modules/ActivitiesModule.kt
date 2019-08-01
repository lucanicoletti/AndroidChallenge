package com.lnicolet.babylonandroidchallenge.di.modules

import com.lnicolet.babylonandroidchallenge.postlist.activity.PostsListActivity
import com.lnicolet.babylonandroidchallenge.di.scopes.ActivityScope
import com.lnicolet.babylonandroidchallenge.postdetails.activities.PostDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun contributePostsListActivityInjector(): PostsListActivity

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun contributePostDetailsActivityInjector(): PostDetailsActivity

}