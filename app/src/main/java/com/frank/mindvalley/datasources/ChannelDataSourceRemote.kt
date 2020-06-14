package com.frank.mindvalley.datasources

import com.frank.mindvalley.models.CategoryModel
import com.frank.mindvalley.models.ChannelModel
import com.frank.mindvalley.models.CourseModel
import com.frank.mindvalley.network.ChannelService
import com.frank.mindvalley.utils.toModel
import javax.inject.Inject

class ChannelDataSourceRemote @Inject constructor(val channelService: ChannelService) :
    ChannelDataSourceInterface {

    override suspend fun getNewEpisodes(): List<CourseModel>? {
        val result = channelService.getNewEpisodes().await()
        val courses = result.newEpisodesResponse?.courses
        return courses?.map { it.toModel() }
    }

    override suspend fun getChannels(): List<ChannelModel>? {
        val result = channelService.getChannels().await()
        val channels = result.channelResponse?.channels
        return channels?.map { it.toModel() }
    }

    override suspend fun getCategories(): List<CategoryModel>? {
        val result = channelService.getCategories().await()
        val categories = result.categoryResponse?.categories
        return categories?.map { it.toModel() }
    }
}