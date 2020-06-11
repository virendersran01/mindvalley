package com.frank.mindvalley.models

data class ChannelModel(
    val title: String?,
    val series: List<CourseModel>? = null,
    val mediaCount: Int = 0,
    val courses: List<CourseModel>? = null,
    val iconAssetUrl: String? = null,
    val coverAssetUrl: String? = null,
    val slug: String? = null,
    val id: String? = null
)