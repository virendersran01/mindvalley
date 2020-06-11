package com.frank.mindvalley.extensions

import android.app.Activity
import com.frank.mindvalley.MindValleyApplication

val Activity.mindValleyApplication: MindValleyApplication
get() = application as MindValleyApplication