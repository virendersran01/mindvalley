package com.frank.mindvalley.ui.channels.components

import android.content.Context
import android.view.View

open class BaseComponent(val context: Context) {

    var idComponent: String? = null

    lateinit var rootView: View

    open fun createView(): View {
        rootView = View.inflate(context, getLayoutId(), null)
        initView()
        return rootView
    }

    open fun getLayoutId(): Int {
        return 0
    }

    open fun initView() {

    }

}