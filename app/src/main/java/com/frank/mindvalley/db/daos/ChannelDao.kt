package com.frank.mindvalley.db.daos


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frank.mindvalley.db.entities.CategoryDb
import com.frank.mindvalley.db.entities.ChannelDb
import com.frank.mindvalley.db.entities.CourseDb

@Dao
interface ChannelDao {

    @Query("SELECT * FROM category")
    fun getCategories(): List<CategoryDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListCategories(categories: List<CategoryDb>)

    @Query("DELETE  FROM category")
    fun deleteAllCategories()

    @Query("SELECT * FROM course")
    fun getAllCourses(): List<CourseDb>

    @Query("SELECT * FROM course WHERE db_course_type = :dbCourseType")
    fun getCoursesByType(dbCourseType: String): List<CourseDb>

    @Query("SELECT * FROM course WHERE chanel_id =:chanelId AND db_course_type = :dbCourseType")
    fun getCoursesByChannelIdAndType(chanelId: Int, dbCourseType: String): List<CourseDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListCourses(courses: List<CourseDb>)

    @Query("DELETE FROM course")
    fun deleteAllCourses()

    @Query("DELETE FROM course WHERE db_course_type = :dbCourseType")
    fun deleteCoursesByType(dbCourseType: String)

    @Query("SELECT * FROM chanel")
    fun getAllChannels(): List<ChannelDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChannel(channel: ChannelDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChannelAndGet(channel: ChannelDb): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListChannels(channels: List<ChannelDb>)

    @Query("DELETE FROM chanel")
    fun deleteAllChannels()

}