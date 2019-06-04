package com.lnicolet.babylonandroidchallenge.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lnicolet.babylonandroidchallenge.core.ViewModelFactory
import com.lnicolet.babylonandroidchallenge.core.ViewModelKey
import com.lnicolet.babylonandroidchallenge.postdetails.viewmodels.PostDetailsViewModel
import com.lnicolet.babylonandroidchallenge.postlist.viewmodels.PostsListViewModel
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