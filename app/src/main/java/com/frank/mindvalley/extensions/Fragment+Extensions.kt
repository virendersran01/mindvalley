package com.frank.mindvalley.extensions

import androidx.fragment.app.Fragment
import com.frank.mindvalley.MindValleyApplication

val Fragment.mindValleyApplication: MindValleyApplication
    get() = requireActivity().mindValleyApplication