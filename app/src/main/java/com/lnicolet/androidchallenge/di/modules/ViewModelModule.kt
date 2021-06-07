package com.lnicolet.androidchallenge.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lnicolet.androidchallenge.core.ViewModelFactory
import com.lnicolet.androidchallenge.core.ViewModelKey
import com.lnicolet.presentation.postdetail.PostDetailViewModel
import com.lnicolet.presentation.postlist.PostListViewModel
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
    @ViewModelKey(PostListViewModel::class)
    internal abstract fun bindPostListViewModel(viewModel: PostListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailViewModel::class)
    internal abstract fun bindPostDetailsViewModule(viewModel: PostDetailViewModel): ViewModel
}