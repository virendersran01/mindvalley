package com.frank.mindvalley.di

import android.content.Context
import com.frank.mindvalley.ui.channels.ChannelsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(channelsFragment: ChannelsFragment)
}