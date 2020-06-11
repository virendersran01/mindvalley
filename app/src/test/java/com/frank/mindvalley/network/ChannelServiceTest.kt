package com.frank.mindvalley.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.core.IsEqual
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import org.junit.After
import org.junit.Assert.assertThat
import org.hamcrest.core.IsNull.notNullValue
import org.hamcrest.core.IsNull.nullValue


@RunWith(JUnit4::class)
class ChannelServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockServer: MockWebServer

    private lateinit var channelService: ChannelService

    @Before
    fun createService() {
        mockServer = MockWebServer()

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val moshiConverter = MoshiConverterFactory.create(moshi)

        val retrofit = Retrofit.Builder().addConverterFactory(moshiConverter)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(mockServer.url("/"))
            .build()

        channelService = retrofit.create(ChannelService::class.java)
    }

    @After
    fun stopService() {
        mockServer.shutdown()
    }

    @Test
    fun getCategories_success_with_null() {
        enqueueResponse("categories_success_null.json")

        val categoryResponseData = runBlocking { channelService.getCategories().await() }
        assertThat(categoryResponseData, notNullValue())

        val categoryResponse = categoryResponseData.categoryResponse
        assertThat(categoryResponse, notNullValue())

        val categories = categoryResponse?.categories
        assertThat(categories, nullValue())
    }

    @Test
    fun getCategories_success_return_none_category() {
        enqueueResponse("categories_success_0.json")

        val categoryResponseData = runBlocking { channelService.getCategories().await() }
        assertThat(categoryResponseData, notNullValue())

        val categoryResponse = categoryResponseData.categoryResponse
        assertThat(categoryResponse, notNullValue())

        val categories = categoryResponse?.categories
        assertThat(categories, notNullValue())
        val categoriesSize = categories?.size
        assertThat(categoriesSize, IsEqual(0))
    }

    @Test
    fun getCategories_success_return_one_category() {
        enqueueResponse("categories_success_1.json")

        val categoryResponseData = runBlocking { channelService.getCategories().await() }
        assertThat(categoryResponseData, notNullValue())

        val categoryResponse = categoryResponseData.categoryResponse
        assertThat(categoryResponse, notNullValue())

        val categories = categoryResponse?.categories
        assertThat(categories, notNullValue())
        val categoriesSize = categories?.size
        assertThat(categoriesSize, IsEqual(1))

        val category = categories?.get(0)
        assertThat(category, notNullValue())
        val categoryName = category?.name
        assertThat(categoryName, IsEqual("Frank"))

    }

    @Test
    fun getCategories_success_return_two_categories() {
        enqueueResponse("categories_success_2.json")

        val categoryResponseData = runBlocking { channelService.getCategories().await() }
        assertThat(categoryResponseData, notNullValue())

        val categoryResponse = categoryResponseData.categoryResponse
        assertThat(categoryResponse, notNullValue())

        val categories = categoryResponse?.categories
        assertThat(categories, notNullValue())
        val categoriesSize = categories?.size
        assertThat(categoriesSize, IsEqual(2))
    }

    @Test
    fun getNewEpisodes_success_with_null() {
        enqueueResponse("new_episodes_success_null.json")

        val newEpisodesResponseData = runBlocking { channelService.getNewEpisodes().await() }

        assertThat(newEpisodesResponseData, notNullValue())

        val newEpisodesResponse = newEpisodesResponseData.newEpisodesResponse
        assertThat(newEpisodesResponse, notNullValue())

        val listCourses = newEpisodesResponse?.courses
        assertThat(listCourses, nullValue())

    }

    @Test
    fun getNewEpisodes_success_return_none_course() {
        enqueueResponse("new_episodes_success_0.json")

        val newEpisodesResponseData = runBlocking { channelService.getNewEpisodes().await() }

        assertThat(newEpisodesResponseData, notNullValue())

        val newEpisodesResponse = newEpisodesResponseData.newEpisodesResponse
        assertThat(newEpisodesResponse, notNullValue())

        val listCourses = newEpisodesResponse?.courses
        assertThat(listCourses, notNullValue())
        val sizeOfListCourses = listCourses?.size
        assertThat(sizeOfListCourses, IsEqual(0))
    }

    @Test
    fun getNewEpisodes_success_return_one_course() {
        enqueueResponse("new_episodes_success_1.json")

        val newEpisodesResponseData = runBlocking { channelService.getNewEpisodes().await() }

        assertThat(newEpisodesResponseData, notNullValue())

        val newEpisodesResponse = newEpisodesResponseData.newEpisodesResponse
        assertThat(newEpisodesResponse, notNullValue())

        val listCourses = newEpisodesResponse?.courses
        assertThat(listCourses, notNullValue())
        val sizeOfListCourses = listCourses?.size
        assertThat(sizeOfListCourses, IsEqual(1))

        val course = listCourses?.get(0)
        assertThat(course, notNullValue())
        val type = course?.type
        assertThat(type, IsEqual("course"))
        val title = course?.title
        assertThat(title, IsEqual("Conscious Parenting"))
        val coverAsset = course?.coverAsset
        assertThat(coverAsset, notNullValue())
        val coverAssetUrl = coverAsset?.url
        assertThat(
            coverAssetUrl,
            IsEqual("https://assets.mindvalley.com/api/v1/assets/5bdbdd0e-3bd3-432b-b8cb-3d3556c58c94.jpg?transform=w_1080")
        )
        val id = course?.id
        assertThat(id, nullValue())
        val channel = course?.channelTitle
        assertThat(channel, notNullValue())
        val channelTitle = channel?.title
        assertThat(channelTitle, IsEqual("Little Humans"))

    }

    @Test
    fun getNewEpisodes_success_return_two_course() {
        enqueueResponse("new_episodes_success_2.json")

        val newEpisodesResponseData = runBlocking { channelService.getNewEpisodes().await() }

        assertThat(newEpisodesResponseData, notNullValue())

        val newEpisodesResponse = newEpisodesResponseData.newEpisodesResponse
        assertThat(newEpisodesResponse, notNullValue())

        val listCourses = newEpisodesResponse?.courses
        assertThat(listCourses, notNullValue())
        val sizeOfListCourses = listCourses?.size
        assertThat(sizeOfListCourses, IsEqual(2))
    }


    @Test
    fun getChannels_success_with_null() {
        enqueueResponse("channels_success_null.json")
        val channelResponseData = runBlocking { channelService.getChannels().await() }

        val channelResponse = channelResponseData.channelResponse
        assertThat(channelResponse, notNullValue())

        val listChannels = channelResponse?.channels
        assertThat(listChannels, nullValue())

    }

    @Test
    fun getChannels_success_return_none_channel() {
        enqueueResponse("channels_success_0.json")
        val channelResponseData = runBlocking { channelService.getChannels().await() }

        val channelResponse = channelResponseData.channelResponse
        assertThat(channelResponse, notNullValue())

        val listChannels = channelResponse?.channels
        assertThat(listChannels, notNullValue())

        val sizeOfListChannels = listChannels?.size
        assertThat(sizeOfListChannels, IsEqual(0))
    }

    @Test
    fun getChannels_success_return_one_channel() {
        enqueueResponse("channels_success_1.json")
        val channelResponseData = runBlocking { channelService.getChannels().await() }

        val channelResponse = channelResponseData.channelResponse
        assertThat(channelResponse, notNullValue())

        val listChannels = channelResponse?.channels
        assertThat(listChannels, notNullValue())

        val sizeOfListChannels = listChannels?.size
        assertThat(sizeOfListChannels, IsEqual(1))

        val channel = listChannels?.get(0)
        assertThat(channel, notNullValue())

        val title = channel?.title
        assertThat(title, IsEqual("Mindvalley Mentoring"))

        val series = channel?.series
        assertThat(series, notNullValue())
        val sizeOfSeries = series?.size
        assertThat(sizeOfSeries, IsEqual(0))

        val mediaCount = channel?.mediaCount
        assertThat(mediaCount, IsEqual(98))

        val courses = channel?.courses
        assertThat(courses, notNullValue())
        val sizeOfCourses = courses?.size
        assertThat(sizeOfCourses, IsEqual(1))
        val course = courses?.get(0)
        assertThat(course, notNullValue())
        val typeOfCourse = course?.type
        assertThat(typeOfCourse, IsEqual("course"))

        val id = channel?.id
        assertThat(id, IsEqual("11"))

        val iconAsset = channel?.iconAsset
        assertThat(iconAsset, notNullValue())
        val iconAssetUrl = iconAsset?.url
        assertThat(
            iconAssetUrl,
            IsEqual("https://edgecastcdn.net/80EC13/public/overmind2/asset/11914f01-ba4a-4d68-9c33-efb34c43ed23/channel-icon-mentoring_thumbnail.png")
        )

        val coverAsset = channel?.coverAsset
        assertThat(coverAsset, notNullValue())
        val coverAssetUrl = coverAsset?.url
        assertThat(
            coverAssetUrl,
            IsEqual("https://assets.mindvalley.com/api/v1/assets/8fd5837a-539c-4367-b1af-8579a3e3d461.jpg?transform=w_1080")
        )
    }

    @Test
    fun getChannels_success_return_two_channel() {
        enqueueResponse("channels_success_2.json")
        val channelResponseData = runBlocking { channelService.getChannels().await() }

        val channelResponse = channelResponseData.channelResponse
        assertThat(channelResponse, notNullValue())

        val listChannels = channelResponse?.channels
        assertThat(listChannels, notNullValue())

        val sizeOfListChannels = listChannels?.size
        assertThat(sizeOfListChannels, IsEqual(2))

        val channel = listChannels?.get(1)
        assertThat(channel, notNullValue())

        val series = channel?.series
        assertThat(series, notNullValue())
        val sizeOfSeries = series?.size
        assertThat(sizeOfSeries, IsEqual(1))

        val course = series?.get(0)
        assertThat(course, notNullValue())
        val titleOfCourse = course?.title
        assertThat(titleOfCourse, IsEqual("Coaching Mastery by Evercoach"))
        val coverAsset = course?.coverAsset
        assertThat(coverAsset, notNullValue())
        val coverAssetUrl = coverAsset?.url
        assertThat(
            coverAssetUrl,
            IsEqual("https://assets.mindvalley.com/api/v1/assets/049742c3-9489-40b0-a415-e80976cd710b.jpg?transform=w_1080")
        )

    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {

        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")

        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}