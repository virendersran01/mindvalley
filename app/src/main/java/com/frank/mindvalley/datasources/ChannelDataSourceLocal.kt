package com.frank.mindvalley.datasources


import com.frank.mindvalley.common.COURSE_DB_TYPE
import com.frank.mindvalley.db.daos.ChannelDao
import com.frank.mindvalley.db.entities.CourseDb
import com.frank.mindvalley.models.CategoryModel
import com.frank.mindvalley.models.ChannelModel
import com.frank.mindvalley.models.CourseModel
import com.frank.mindvalley.utils.*
import javax.inject.Inject

class ChannelDataSourceLocal @Inject constructor(val channelDao: ChannelDao) :
    ChannelDataSourceInterface {

    override suspend fun getNewEpisodes(): List<CourseModel>? {
        val result = channelDao.getCoursesByType(COURSE_DB_TYPE.new_episodes_type.name)
        return result.toListCourseModelFromDb()
    }

    override suspend fun getChannels(): List<ChannelModel>? {
        val result = channelDao.getAllChannels()
        val listChannels = mutableListOf<ChannelModel>()
        for (channelDb in result) {
            var chanelModel = channelDb.toModel()
            val channelId = channelDb.channelId
            val listSeries =
                channelDao.getCoursesByChannelIdAndType(channelId, COURSE_DB_TYPE.series.name)
            chanelModel.series = listSeries.toListCourseModelFromDb()
            val listCourses =
                channelDao.getCoursesByChannelIdAndType(channelId, COURSE_DB_TYPE.course.name)
            chanelModel.courses = listCourses.toListCourseModelFromDb()
            listChannels.add(chanelModel)
        }
        return listChannels.toList()
    }

    override suspend fun getCategories(): List<CategoryModel>? {
        val result = channelDao.getCategories()
        return result.toListCategoryModelFromDb()
    }

    suspend fun saveNewEpisodesToDB(courses: List<CourseModel>) {
        channelDao.deleteCoursesByType(COURSE_DB_TYPE.new_episodes_type.name)

        val listCourses = mutableListOf<CourseDb>()
        for (courseModel in courses) {
            var courseDb = courseModel.toEntity()
            courseDb.dbCourseType = COURSE_DB_TYPE.new_episodes_type.name
            listCourses.add(courseDb)
        }
        channelDao.insertListCourses(listCourses)
    }

    suspend fun saveListChannelToDB(channels: List<ChannelModel>) {

        for (channelModel in channels) {
            val channelDb = channelModel.toEntity()
            val channelDbId = channelDao.insertChannelAndGet(channelDb).toInt()
            val listSeries = channelModel.series?.toListEntities()
            if (!listSeries.isNullOrEmpty()) {
                saveListCoursesToDB(channelDbId, COURSE_DB_TYPE.series.name, listSeries)
            }

            val listCourses = channelModel.courses?.toListEntities()
            if (!listCourses.isNullOrEmpty()) {
                saveListCoursesToDB(channelDbId, COURSE_DB_TYPE.course.name, listCourses)
            }
        }

    }

    suspend fun saveListCategoriesToDB(categories: List<CategoryModel>) {
        channelDao.deleteAllCategories()
        channelDao.insertListCategories(categories.map { it.toEntity() })
    }

    suspend fun saveListCoursesToDB(channelId: Int, dbCourseType: String, courses: List<CourseDb>) {
        for (course in courses) {
            course.channelId = channelId
            course.dbCourseType = dbCourseType
        }
        channelDao.insertListCourses(courses)
    }

}