package com.example.babylon.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.babylon.MainViewModel
import com.example.babylon.core.ViewModelFactory
import com.example.babylon.core.ViewModelKey
import com.example.babylon.postdetails.viewmodels.PostDetailsViewModel
import com.example.babylon.postlist.viewmodels.PostsListViewModel
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
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostsListViewModel::class)
    internal abstract fun bindPostListViewModel(viewModel: PostsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailsViewModel::class)
    internal abstract fun bindPostDetailsViewModule(viewModel: PostDetailsViewModel): ViewModel
}