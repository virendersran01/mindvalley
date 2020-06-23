package com.frank.mindvalley.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "chanel")
class ChannelDb(
    @PrimaryKey(autoGenerate = true)
    val channelId: Int = 0,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "media_count") val mediaCount: Int = 0,
    @ColumnInfo(name = "icon_asset_url") val iconAssetUrl: String? = null,
    @ColumnInfo(name = "cover_asset_url") val coverAssetUrl: String? = null,
    @ColumnInfo(name = "slug") val slug: String? = null,
    @ColumnInfo(name = "id") val id: String? = null
)