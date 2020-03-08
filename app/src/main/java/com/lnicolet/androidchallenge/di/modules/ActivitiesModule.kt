package com.lnicolet.androidchallenge.di.modules

import com.lnicolet.androidchallenge.postlist.activities.PostsListActivity
import com.lnicolet.androidchallenge.di.scopes.ActivityScope
import com.lnicolet.androidchallenge.postdetails.activities.PostDetailsActivity
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