package com.frank.mindvalley.utils

import com.frank.mindvalley.db.entities.CategoryDb
import com.frank.mindvalley.db.entities.ChannelDb
import com.frank.mindvalley.db.entities.CourseDb
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

fun List<CourseDb>.toListCourseModelFromDb(): List<CourseModel> {
    return map {
        it.toModel()
    }
}

fun CourseDb.toModel(): CourseModel {
    return CourseModel(
        id = id,
        type = type,
        title = title,
        coverAssetUrl = coverAssetUrl,
        channelTitle = channelTitle
    )
}

fun List<CategoryDb>.toListCategoryModelFromDb(): List<CategoryModel> {
    return map {
        it.toModel()
    }
}

fun CategoryDb.toModel(): CategoryModel {
    return CategoryModel(
        name
    )
}

fun ChannelDb.toModel(): ChannelModel {
    return ChannelModel(
        title = title,
        mediaCount = mediaCount,
        iconAssetUrl = iconAssetUrl,
        coverAssetUrl = coverAssetUrl,
        slug = slug,
        id = id
    )
}


fun CategoryModel.toEntity(): CategoryDb {
    return CategoryDb(name ?: "")
}

fun List<CourseModel>.toListEntities(): List<CourseDb>{
    return map {
        it.toEntity()
    }
}

fun CourseModel.toEntity(): CourseDb {
    return CourseDb(
        type = type,
        title = title,
        coverAssetUrl = coverAssetUrl,
        id = id,
        channelTitle = channelTitle
    )
}

fun ChannelModel.toEntity(): ChannelDb {
    return ChannelDb(
        title = title,
        mediaCount = mediaCount,
        iconAssetUrl = iconAssetUrl,
        coverAssetUrl = coverAssetUrl,
        slug = slug,
        id = id
    )
}