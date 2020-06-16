package com.frank.mindvalley

import com.frank.mindvalley.di.AppComponent
import com.frank.mindvalley.di.DaggerAppComponent
import org.mockito.Mockito


class TestMindValleyApplication : MindValleyApplication() {
    override val appComponent by lazy {
        Mockito.mock(AppComponent::class.java)
    }
}