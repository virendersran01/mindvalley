package com.frank.mindvalley.network.response

import com.squareup.moshi.Json

data class NewEpisodesResponseData(
    @Json(name = "data") val newEpisodesResponse: NewEpisodesResponse? = null
)

data class NewEpisodesResponse(
    @Json(name = "media") val courses: List<CourseNetwork>?
)