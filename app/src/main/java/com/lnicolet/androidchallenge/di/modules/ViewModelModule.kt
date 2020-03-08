package com.lnicolet.androidchallenge.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lnicolet.androidchallenge.core.ViewModelFactory
import com.lnicolet.androidchallenge.core.ViewModelKey
import com.lnicolet.androidchallenge.postdetails.viewmodels.PostDetailsViewModel
import com.lnicolet.androidchallenge.postlist.viewmodels.PostsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PostsListViewModel::class)
    internal abstract fun bindPostListViewModel(viewModel: PostsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailsViewModel::class)
    internal abstract fun bindPostDetailsViewModule(viewModel: PostDetailsViewModel): ViewModel
}