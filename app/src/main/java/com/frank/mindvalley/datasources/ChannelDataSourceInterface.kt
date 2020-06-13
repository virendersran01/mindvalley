package com.frank.mindvalley.datasources


import com.frank.mindvalley.models.CategoryModel
import com.frank.mindvalley.models.ChannelModel
import com.frank.mindvalley.models.CourseModel

interface ChannelDataSourceInterface {

    suspend fun getNewEpisodes(): List<CourseModel>?

    suspend fun getChannels(): List<ChannelModel>?

    suspend fun getCategories(): List<CategoryModel>?

}