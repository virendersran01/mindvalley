package com.frank.mindvalley.ui.channels

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frank.mindvalley.R
import com.frank.mindvalley.databinding.FragmentChannelsBinding
import com.frank.mindvalley.di.ViewModelFactory
import com.frank.mindvalley.extensions.mindValleyApplication
import com.frank.mindvalley.models.ChannelModel
import com.frank.mindvalley.ui.channels.adapters.CategoryAdapter
import com.frank.mindvalley.ui.channels.adapters.NewEpisodesAdapter
import com.frank.mindvalley.ui.channels.components.ChannelComponent
import javax.inject.Inject

class ChannelsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ChannelsViewModel

    private lateinit var dataBinding: FragmentChannelsBinding

    private lateinit var newEpisodesAdapter: NewEpisodesAdapter

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mindValleyApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ChannelsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = FragmentChannelsBinding.inflate(inflater)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = viewModel

        viewModel.listCategories.observe(viewLifecycleOwner, Observer {
            categoryAdapter.submitList(it)
        })

        viewModel.listChannels.observe(viewLifecycleOwner, Observer {
            it?.let {
                showListChannels(it)
            }
        })

        viewModel.listNewEpisodes.observe(viewLifecycleOwner, Observer {
            newEpisodesAdapter.submitList(it)
        })

        viewModel.fetchData()

        initNewEpisodesView()

        initCategoriesView()

        return dataBinding.root
    }

    private fun initNewEpisodesView() {
        val manager = LinearLayoutManager(requireContext())
        manager.orientation = LinearLayoutManager.HORIZONTAL
        dataBinding.rcvNewEpisodes.layoutManager = manager

        val verticalDividerItemDecoration =
            DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL)
        val drawable = AppCompatResources.getDrawable(requireContext(), R.drawable.line_divider)
        drawable?.let {
            verticalDividerItemDecoration.setDrawable(drawable)
        }
        dataBinding.rcvNewEpisodes.addItemDecoration(verticalDividerItemDecoration)


        newEpisodesAdapter = NewEpisodesAdapter()
        dataBinding.rcvNewEpisodes.adapter = newEpisodesAdapter

    }

    private fun initCategoriesView() {
        val manager = GridLayoutManager(requireContext(), 2)
        dataBinding.rcvCategories.layoutManager = manager


        val horizontalDividerItemDecoration =
            DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL)
        val verticalDividerItemDecoration =
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        val drawable = AppCompatResources.getDrawable(requireContext(), R.drawable.line_divider)
        drawable?.let {
            horizontalDividerItemDecoration.setDrawable(drawable)
            verticalDividerItemDecoration.setDrawable(drawable)
        }

        dataBinding.rcvCategories.addItemDecoration(verticalDividerItemDecoration)
        dataBinding.rcvCategories.addItemDecoration(horizontalDividerItemDecoration)

        categoryAdapter = CategoryAdapter()
        dataBinding.rcvCategories.adapter = categoryAdapter

    }

    private fun showListChannels(channels: List<ChannelModel>){
        dataBinding.llChannels.removeAllViews()
        for(channel in channels){
            val channelComponent = ChannelComponent(requireContext(),channel)
            dataBinding.llChannels.addView(channelComponent.createView())
        }
    }

}