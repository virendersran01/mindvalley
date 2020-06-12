package com.frank.mindvalley.ui.channels

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.frank.mindvalley.R
import com.frank.mindvalley.di.ViewModelFactory
import com.frank.mindvalley.extensions.mindValleyApplication
import javax.inject.Inject

class ChannelsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    private lateinit var viewModel: ChannelsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mindValleyApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ChannelsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_channels, container, false)
    }

}