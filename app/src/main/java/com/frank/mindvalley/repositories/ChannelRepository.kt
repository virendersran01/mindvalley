package com.frank.mindvalley.repositories

import com.frank.mindvalley.datasources.ChannelDataSourceLocal
import com.frank.mindvalley.datasources.ChannelDataSourceRemote
import com.frank.mindvalley.models.CategoryModel
import com.frank.mindvalley.models.ChannelModel
import com.frank.mindvalley.models.CourseModel
import com.frank.mindvalley.network.NetworkException
import javax.inject.Inject

class ChannelRepository @Inject constructor(
    private val remoteDataSource: ChannelDataSourceRemote,
    private val localDataSource: ChannelDataSourceLocal
) {

    suspend fun getNewEpisodesFromDBFirst(): List<CourseModel>? {
        val result = localDataSource.getNewEpisodes()
        return if (!result.isNullOrEmpty()) {
            result
        } else {
            remoteDataSource.getNewEpisodes()
        }
    }

    suspend fun getChannelsFromDbFirst(): List<ChannelModel>? {
        val result = localDataSource.getChannels()
        return if (!result.isNullOrEmpty()) {
            result
        } else {
            remoteDataSource.getChannels()
        }
    }

    suspend fun getCategoriesFromDbFirst(): List<CategoryModel>? {
        val result = localDataSource.getCategories()
        return if (!result.isNullOrEmpty()) {
            result
        } else {
            remoteDataSource.getCategories()
        }
    }

    suspend fun getNewEpisodes(): List<CourseModel>? {
        return try {
            val result = remoteDataSource.getNewEpisodes()
            result?.let { localDataSource.saveNewEpisodesToDB(it) }
            result
        } catch (networkException: NetworkException) {
            localDataSource.getNewEpisodes()
        }
    }

    suspend fun getChannels(): List<ChannelModel>? {
        return try {
            val result = remoteDataSource.getChannels()
            result?.let { localDataSource.saveListChannelToDB(it) }
            result
        } catch (networkException: NetworkException) {
            localDataSource.getChannels()
        }
    }

    suspend fun getCategories(): List<CategoryModel>? {
        return try {
            val result = remoteDataSource.getCategories()
            result?.let {
                localDataSource.saveListCategoriesToDB(it)
            }
            result
        } catch (networkException: NetworkException) {
            localDataSource.getCategories()
        }
    }


}