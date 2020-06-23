package com.frank.mindvalley.models

data class ChannelModel(
    val title: String?,
    var series: List<CourseModel>? = null,
    val mediaCount: Int = 0,
    var courses: List<CourseModel>? = null,
    val iconAssetUrl: String? = null,
    val coverAssetUrl: String? = null,
    val slug: String? = null,
    val id: String? = null
)