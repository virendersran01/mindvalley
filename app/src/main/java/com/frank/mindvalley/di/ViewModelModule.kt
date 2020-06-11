package com.frank.mindvalley.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frank.mindvalley.ui.channels.ChannelsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ChannelsViewModel::class)
    abstract fun bindChannelsViewModel(channelsViewModel: ChannelsViewModel): ViewModel

}