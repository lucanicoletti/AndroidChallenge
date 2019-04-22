package com.example.babylon.di.modules

import com.example.babylon.postlist.activities.PostsListActivity
import com.example.babylon.di.scopes.ActivityScope
import com.example.babylon.postdetails.activities.PostDetailsActivity
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