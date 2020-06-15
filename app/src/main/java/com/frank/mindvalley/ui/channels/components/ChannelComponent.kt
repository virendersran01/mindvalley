package com.frank.mindvalley.ui.channels.components

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.frank.mindvalley.R
import com.frank.mindvalley.models.ChannelModel
import com.frank.mindvalley.models.CourseModel
import com.frank.mindvalley.ui.channels.adapters.NewEpisodesAdapter
import de.hdodenhof.circleimageview.CircleImageView

class ChannelComponent(context: Context, val channelModel: ChannelModel) : BaseComponent(context) {


    override fun getLayoutId(): Int {
        return R.layout.component_channel
    }

    override fun initView() {
        initChannelInfo()

        initRecycleView()

    }

    private fun initChannelInfo() {
        val imgIconChannel = rootView.findViewById<CircleImageView>(R.id.imgIconChannel)
        channelModel.iconAssetUrl?.let {
            Glide.with(context).load(it).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_mindvalley).into(imgIconChannel)
        }

        val tvTitleChannel = rootView.findViewById<TextView>(R.id.tvTitleChannel)
        tvTitleChannel.setText(channelModel.title ?: "")

        val tvNumberCourses = rootView.findViewById<TextView>(R.id.tvNumberCourses)
        var count = 0
        var courseText = ""
        if (channelModel.series.isNullOrEmpty()) {
            count = channelModel.courses?.size ?: 0
            courseText = if (count > 1) "episodes" else "episode"
        } else {
            count = channelModel.series?.size ?: 0
            courseText = "series"
        }
        tvNumberCourses.text = "$count $courseText"

    }

    private fun initRecycleView() {

        val rcvCourses = rootView.findViewById<RecyclerView>(R.id.rcvCourses)
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        rcvCourses.layoutManager = manager

        val verticalDividerItemDecoration =
            DividerItemDecoration(context, RecyclerView.HORIZONTAL)
        val drawable = AppCompatResources.getDrawable(context, R.drawable.line_divider)
        drawable?.let {
            verticalDividerItemDecoration.setDrawable(drawable)
        }
        rcvCourses.addItemDecoration(verticalDividerItemDecoration)

        val adapter = NewEpisodesAdapter()
        var listCourse: List<CourseModel>?
        if (!channelModel.series.isNullOrEmpty()) {
            adapter.isForSeries = true
            listCourse  = channelModel.series
        }
        else {
            listCourse = channelModel.courses
        }
        rcvCourses.adapter = adapter

        adapter.submitList(listCourse)
    }


}