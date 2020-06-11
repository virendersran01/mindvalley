package com.frank.mindvalley.network.response

import com.squareup.moshi.Json

data class ChannelResponseData(
    @Json(name = "data") val channelResponse: ChannelResponse?
)

data class ChannelResponse(
    @Json(name = "channels") val channels: List<ChannelNetwork>? = null
)

data class ChannelNetwork(
    @Json(name = "title") val title: String? = null,
    @Json(name = "series") val series: List<CourseNetwork>? = null,
    @Json(name = "mediaCount") val mediaCount: Int = 0,
    @Json(name = "latestMedia") val courses: List<CourseNetwork>? = null,
    @Json(name = "id") val id: String? = null,
    @Json(name = "iconAsset") val iconAsset: IconAssetNetwork? = null,
    @Json(name = "coverAsset") val coverAsset: CoverAssetNetwork? = null,
    @Json(name = "slug") val slug: String? = null
)

data class IconAssetNetwork(
    @Json(name = "thumbnailUrl") val url: String? = null
)

data class CourseNetwork(
    @Json(name = "type") val type: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "coverAsset") val coverAsset: CoverAssetNetwork? = null,
    @Json(name = "id") val id: String? = null,
    @Json(name = "channel") val channelTitle: ChannelTitleNetwork? = null
)

data class CoverAssetNetwork(
    @Json(name = "url") val url: String? = null
)

data class ChannelTitleNetwork(
    @Json(name = "title") val title: String? = null
)