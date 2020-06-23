package com.frank.mindvalley.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.frank.mindvalley.db.daos.ChannelDao
import com.frank.mindvalley.db.entities.CategoryDb
import com.frank.mindvalley.db.entities.ChannelDb
import com.frank.mindvalley.db.entities.CourseDb

@Database(
    entities = [CategoryDb::class, ChannelDb::class, CourseDb::class],
    version = 1,
    exportSchema = false
)
abstract class MindValleyDB : RoomDatabase() {

    abstract fun channelDao(): ChannelDao
}