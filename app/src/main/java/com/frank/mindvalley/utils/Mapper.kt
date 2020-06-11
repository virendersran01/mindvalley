package com.frank.mindvalley.utils

import com.frank.mindvalley.models.CategoryModel
import com.frank.mindvalley.models.ChannelModel
import com.frank.mindvalley.models.CourseModel
import com.frank.mindvalley.network.response.CategoryNetwork
import com.frank.mindvalley.network.response.ChannelNetwork
import com.frank.mindvalley.network.response.CourseNetwork


fun List<ChannelNetwork>.toListChannelModel(): List<ChannelModel> {
    return map {
        it.toModel()
    }
}

fun ChannelNetwork.toModel(): ChannelModel {
    return ChannelModel(
        title = title,
        series = series?.toListCourseModel(),
        mediaCount = mediaCount,
        courses = courses?.toListCourseModel(),
        iconAssetUrl = iconAsset?.url,
        coverAssetUrl = coverAsset?.url,
        slug = slug,
        id = id
    )
}

fun List<CourseNetwork>.toListCourseModel(): List<CourseModel> {
    return map {
        it.toModel()
    }
}

fun CourseNetwork.toModel(): CourseModel {
    return CourseModel(
        id = id,
        type = type,
        title = title,
        coverAssetUrl = coverAsset?.url,
        channelTitle = channelTitle?.title
    )
}

fun List<CategoryNetwork>.toListCategoryModel(): List<CategoryModel> {
    return map {
        it.toModel()
    }
}

fun CategoryNetwork.toModel(): CategoryModel {
    return CategoryModel(
        name = name
    )
}