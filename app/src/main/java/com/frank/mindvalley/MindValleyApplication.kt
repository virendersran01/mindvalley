package com.frank.mindvalley

import android.app.Application
import com.frank.mindvalley.di.DaggerAppComponent

open class MindValleyApplication: Application() {

    open val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}