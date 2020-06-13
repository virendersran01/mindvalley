package com.frank.mindvalley.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course")
class CourseDb(
    @PrimaryKey(autoGenerate = true)
    val courseId: Int = 0,
    @ColumnInfo(name = "chanel_id") var channelId: Int = -1,
    @ColumnInfo(name = "type") val type: String? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "cover_asset_url") val coverAssetUrl: String? = null,
    @ColumnInfo(name = "id") val id: String? = null,
    @ColumnInfo(name = "channel_title") val channelTitle: String? = null,
    @ColumnInfo(name = "db_course_type") var dbCourseType: String? = null
)