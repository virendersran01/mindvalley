package com.frank.mindvalley.utils

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.frank.mindvalley.TestMindValleyApplication

class MindValleyCustomTestRunner: AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestMindValleyApplication::class.java.name, context)
    }

}