package com.example.babylon.di.modules

import com.example.babylon.di.scopes.FragmentScope
import com.example.babylon.postdetails.fragments.PostDetailsFragment
import com.example.babylon.postlist.fragments.PostsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun contributePostsListFragment(): PostsListFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun contributePostDetailsFragment(): PostDetailsFragment

}