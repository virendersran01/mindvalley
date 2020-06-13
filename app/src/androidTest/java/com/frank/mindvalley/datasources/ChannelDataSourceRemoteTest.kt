package com.frank.mindvalley.datasources

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.mindvalley.network.ChannelService
import com.frank.mindvalley.network.response.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNull.notNullValue
import org.hamcrest.core.IsNull.nullValue

@RunWith(AndroidJUnit4::class)
class ChannelDataSourceRemoteTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var channelService: ChannelService

    lateinit var channelDataSourceRemote: ChannelDataSourceRemote

    @Before
    fun setup() {
        channelService = mock(ChannelService::class.java)

        channelDataSourceRemote = ChannelDataSourceRemote(channelService)
    }

    @Test
    fun getCategories_return_empty(){

        val categoryResponse = CategoryResponse(listOf<CategoryNetwork>())
        val categoryResponseData = CategoryResponseData(categoryResponse)

        Mockito.`when`(channelService.getCategories()).thenReturn(CompletableDeferred(categoryResponseData))

        val result = runBlocking { channelDataSourceRemote.getCategories() }

        assertThat(result, notNullValue())
        val size = result?.size
        assertThat(size,IsEqual(0))
    }

    @Test
    fun getCategories_return_one_categoryModel(){

        val categoryNetwork = CategoryNetwork("frank")
        val categoryResponse = CategoryResponse(listOf<CategoryNetwork>(categoryNetwork))
        val categoryResponseData = CategoryResponseData(categoryResponse)

        Mockito.`when`(channelService.getCategories()).thenReturn(CompletableDeferred(categoryResponseData))

        val result = runBlocking { channelDataSourceRemote.getCategories() }

        assertThat(result, notNullValue())
        val size = result?.size
        assertThat(size,IsEqual(1))

        val categoryModel = result?.get(0)
        assertThat(categoryModel, notNullValue())
        val categoryName = categoryModel?.name
        assertThat(categoryName,IsEqual("frank"))
    }

    @Test
    fun getCategories_return_two_categoryModels(){
        val categoryNetwork = CategoryNetwork("frank")
        val categoryNetwork2 = CategoryNetwork("mindvalley")
        val categoryResponse = CategoryResponse(listOf<CategoryNetwork>(categoryNetwork,categoryNetwork2))
        val categoryResponseData = CategoryResponseData(categoryResponse)

        Mockito.`when`(channelService.getCategories()).thenReturn(CompletableDeferred(categoryResponseData))

        val result = runBlocking { channelDataSourceRemote.getCategories() }

        assertThat(result, notNullValue())
        val size = result?.size
        assertThat(size,IsEqual(2))
    }

    @Test
    fun getNewEpisodes_return_empty_course() {
        val listCourseNetwork = listOf<CourseNetwork>()
        val newEpisodesResponse = NewEpisodesResponse(listCourseNetwork)
        val newEpisodesResponseData = NewEpisodesResponseData(newEpisodesResponse)

        Mockito.`when`(channelService.getNewEpisodes())
            .thenReturn(CompletableDeferred(newEpisodesResponseData))

        val listCourseModels = runBlocking { channelDataSourceRemote.getNewEpisodes() }

        assertThat(listCourseModels, notNullValue())
        val size = listCourseModels?.size
        assertThat(size, IsEqual(0))

    }

    @Test
    fun getNewEpisodes_return_one_course() {
        val coverAssetNetwork =
            CoverAssetNetwork("https://assets.mindvalley.com/api/v1/assets/5bdbdd0e-3bd3-432b-b8cb-3d3556c58c94.jpg?transform=w_1080")
        val channelTitleNetwork = ChannelTitleNetwork("Frank Channel")
        val courseNetwork = CourseNetwork(
            type = "course",
            title = "Frank with MindValley's Channel",
            coverAsset = coverAssetNetwork,
            id="11",
            channelTitle = channelTitleNetwork
        )

        val listCourseNetwork = listOf<CourseNetwork>(courseNetwork)
        val newEpisodesResponse = NewEpisodesResponse(listCourseNetwork)
        val newEpisodesResponseData = NewEpisodesResponseData(newEpisodesResponse)

        Mockito.`when`(channelService.getNewEpisodes())
            .thenReturn(CompletableDeferred(newEpisodesResponseData))

        val listCourseModels = runBlocking { channelDataSourceRemote.getNewEpisodes() }

        assertThat(listCourseModels, notNullValue())
        val size = listCourseModels?.size
        assertThat(size, IsEqual(1))

        val courseModel = listCourseModels?.get(0)
        assertThat(courseModel, notNullValue())
        assertThat(courseModel?.type,IsEqual("course"))
        assertThat(courseModel?.title,IsEqual("Frank with MindValley's Channel"))
        assertThat(courseModel?.coverAssetUrl,IsEqual("https://assets.mindvalley.com/api/v1/assets/5bdbdd0e-3bd3-432b-b8cb-3d3556c58c94.jpg?transform=w_1080"))
        assertThat(courseModel?.id,IsEqual("11"))
        assertThat(courseModel?.channelTitle,IsEqual("Frank Channel"))

    }

    @Test
    fun getNewEpisodes_return_two_courses(){
        val coverAssetNetwork =
            CoverAssetNetwork("https://assets.mindvalley.com/api/v1/assets/5bdbdd0e-3bd3-432b-b8cb-3d3556c58c94.jpg?transform=w_1080")
        val channelTitleNetwork = ChannelTitleNetwork("Frank Channel")
        val courseNetwork = CourseNetwork(
            type = "course",
            title = "Frank with MindValley's Channel",
            coverAsset = coverAssetNetwork,
            id="11",
            channelTitle = channelTitleNetwork
        )

        val courseNetwork2 = CourseNetwork(
            type = "course",
            title = "Frank with MindValley's Channel",
            coverAsset = coverAssetNetwork,
            id="11",
            channelTitle = channelTitleNetwork
        )

        val listCourseNetwork = listOf<CourseNetwork>(courseNetwork,courseNetwork2)
        val newEpisodesResponse = NewEpisodesResponse(listCourseNetwork)
        val newEpisodesResponseData = NewEpisodesResponseData(newEpisodesResponse)

        Mockito.`when`(channelService.getNewEpisodes())
            .thenReturn(CompletableDeferred(newEpisodesResponseData))

        val listCourseModels = runBlocking { channelDataSourceRemote.getNewEpisodes() }

        assertThat(listCourseModels, notNullValue())
        val size = listCourseModels?.size
        assertThat(size, IsEqual(2))
    }

    @Test
    fun getChannels_return_empty_channel() {
        val listChannelNetwork = listOf<ChannelNetwork>()
        val channelResponse = ChannelResponse(listChannelNetwork)
        val channelResponseData = ChannelResponseData(channelResponse)

        Mockito.`when`(channelService.getChannels())
            .thenReturn(CompletableDeferred(channelResponseData))

        val listChannelModel = runBlocking { channelDataSourceRemote.getChannels() }
        assertThat(listChannelModel, notNullValue())
        assertThat(listChannelModel?.size, IsEqual(0))

    }

    @Test
    fun getChannels_return_one_channel() {
        val iconAssetNetwork =
            IconAssetNetwork("https://assets.mindvalley.com/api/v1/assets/5bdbdd0e")
        val coverAssetNetwork =
            CoverAssetNetwork("https://assets.mindvalley.com/api/v1/assets/5bdbdd0e-3bd3-432b-b8cb-3d3556c58c94.jpg?transform=w_1080")
        val channelTitleNetwork = ChannelTitleNetwork("Frank Channel")
        val courseNetwork = CourseNetwork(
            type = "course",
            title = "Frank with MindValley's Channel",
            coverAsset = coverAssetNetwork,
            id = "11",
            channelTitle = channelTitleNetwork
        )
        val listCourses = listOf<CourseNetwork>(courseNetwork)

        val seriesNetwork = CourseNetwork(
            title = "Frank with MindValley's Channel",
            coverAsset = coverAssetNetwork
        )
        val listSeries = listOf<CourseNetwork>(seriesNetwork)

        val channelNetwork = ChannelNetwork(
            title = "frank channel",
            series = listSeries,
            mediaCount = 1,
            courses = listCourses,
            id = "10",
            iconAsset = iconAssetNetwork
        )

        val listOfChannels = listOf<ChannelNetwork>(channelNetwork)
        val channelResponse = ChannelResponse(listOfChannels)
        val channelResponseData = ChannelResponseData(channelResponse)
        Mockito.`when`(channelService.getChannels())
            .thenReturn(CompletableDeferred(channelResponseData))

        val listChannelModel = runBlocking { channelDataSourceRemote.getChannels() }
        assertThat(listChannelModel, notNullValue())
        assertThat(listChannelModel?.size, IsEqual(1))

        val channelModel = listChannelModel?.get(0)
        assertThat(channelModel, notNullValue())
        assertThat(channelModel?.title,IsEqual("frank channel"))
        assertThat(channelModel?.mediaCount,IsEqual(1))
        assertThat(channelModel?.coverAssetUrl, nullValue())
        assertThat(channelModel?.slug, nullValue())

        val listSeriesModel = channelModel?.series
        assertThat(listChannelModel, notNullValue())
        assertThat(listChannelModel?.size,IsEqual(1))
        val seriesModel = listSeriesModel?.get(0)
        assertThat(seriesModel, notNullValue())
        assertThat(seriesModel?.title,IsEqual("Frank with MindValley's Channel"))


        val listCourseModels = channelModel?.courses
        assertThat(listCourses, notNullValue())
        assertThat(listCourses?.size,IsEqual(1))
        val courseModel = listCourseModels?.get(0)
        assertThat(courseModel, notNullValue())
        assertThat(courseModel?.type,IsEqual("course"))

    }

}