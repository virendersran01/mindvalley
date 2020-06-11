package com.frank.mindvalley.network

import com.frank.mindvalley.network.response.CategoryResponseData
import com.frank.mindvalley.network.response.ChannelResponseData
import com.frank.mindvalley.network.response.NewEpisodesResponseData
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ChannelService {

    @GET("/raw/Xt12uVhM")
    fun getChannels(): Deferred<ChannelResponseData>

    @GET("raw/z5AExTtw")
    fun getNewEpisodes(): Deferred<NewEpisodesResponseData>

    @GET("raw/A0CgArX3")
    fun getCategories(): Deferred<CategoryResponseData>

}