package com.frank.mindvalley.datasources

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.mindvalley.common.COURSE_DB_TYPE
import com.frank.mindvalley.db.MindValleyDBTest
import com.frank.mindvalley.db.entities.ChannelDb
import com.frank.mindvalley.db.entities.CourseDb
import com.frank.mindvalley.models.CategoryModel
import com.frank.mindvalley.models.ChannelModel
import com.frank.mindvalley.models.CourseModel
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNull.notNullValue
import org.hamcrest.core.IsNull.nullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChannelDataSourceLocalTest : MindValleyDBTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    lateinit var channelDataSourceLocal: ChannelDataSourceLocal

    @Before
    fun setup() {
        channelDataSourceLocal = ChannelDataSourceLocal(db.channelDao())
    }

    @Test
    fun saveNewEpisodesToDBAndRead() {
        val courseModel = CourseModel(
            type = "course",
            title = "title",
            coverAssetUrl = "https://assets.mindvalley.com/api/v1/assets/5bdbdd0e-3bd3-432b-b8cb-3d3556c58c94.jpg",
            channelTitle = "frank"
        )

        val listCourseModels = listOf<CourseModel>(courseModel)
        runBlocking { channelDataSourceLocal.saveNewEpisodesToDB(listCourseModels) }

        val listCourseDb =
            runBlocking { db.channelDao().getCoursesByType(COURSE_DB_TYPE.new_episodes_type.name) }

        assertThat(listCourseDb, notNullValue())
        assertThat(listCourseDb.size, IsEqual(1))

        val courseDb = listCourseDb[0]
        assertThat(courseDb, notNullValue())
        assertThat(courseDb.type, IsEqual("course"))
        assertThat(courseDb.title, IsEqual("title"))
        assertThat(courseDb.dbCourseType, IsEqual(COURSE_DB_TYPE.new_episodes_type.name))
        assertThat(
            courseDb.coverAssetUrl,
            IsEqual("https://assets.mindvalley.com/api/v1/assets/5bdbdd0e-3bd3-432b-b8cb-3d3556c58c94.jpg")
        )
        assertThat(courseDb.channelTitle, IsEqual("frank"))
        assertThat(courseDb.id, nullValue())
        assertThat(courseDb.courseId, IsEqual(1))
    }

    @Test
    fun saveListCoursesToDBAndRead() {
        val courseDb1 = CourseDb(
            type = "course",
            title = "Frank",
            coverAssetUrl = "mindvalley.com",
            id = "11",
            channelTitle = "Little Human"
        )

        val courseDb2 = CourseDb(
            type = "course",
            title = "MindValley",
            coverAssetUrl = "mindvalley.com",
            channelTitle = "Little Human"
        )

        val listCourseDbs = listOf<CourseDb>(courseDb1, courseDb2)

        runBlocking {
            channelDataSourceLocal.saveListCoursesToDB(
                19,
                COURSE_DB_TYPE.new_episodes_type.name,
                listCourseDbs
            )
        }

        val listSavedCourseDbs =
            runBlocking { db.channelDao().getCoursesByType(COURSE_DB_TYPE.new_episodes_type.name) }

        assertThat(listSavedCourseDbs, notNullValue())
        assertThat(listSavedCourseDbs.size, IsEqual(2))

        val firstSavedCourseDb = listSavedCourseDbs[0]
        assertThat(firstSavedCourseDb, notNullValue())
        assertThat(firstSavedCourseDb.courseId, IsEqual(1))
        assertThat(firstSavedCourseDb.dbCourseType, IsEqual(COURSE_DB_TYPE.new_episodes_type.name))
        assertThat(firstSavedCourseDb.channelId, IsEqual(19))

        val secondSavedCourseDb = listSavedCourseDbs[1]
        assertThat(secondSavedCourseDb, notNullValue())
        assertThat(secondSavedCourseDb.courseId, IsEqual(2))
        assertThat(secondSavedCourseDb.dbCourseType, IsEqual(COURSE_DB_TYPE.new_episodes_type.name))
        assertThat(secondSavedCourseDb.channelId, IsEqual(19))

    }

    @Test
    fun saveListCategoriesToDBAndRead() {
        val categoryModel = CategoryModel("frank")
        val listCategoryModel = listOf<CategoryModel>(categoryModel)

        runBlocking { channelDataSourceLocal.saveListCategoriesToDB(listCategoryModel) }

        val listCategoryDbs = runBlocking { db.channelDao().getCategories() }

        assertThat(listCategoryDbs, notNullValue())
        assertThat(listCategoryDbs.size, IsEqual(1))

        val categoryDb = listCategoryDbs[0]
        assertThat(categoryDb, notNullValue())
        assertThat(categoryDb.name, IsEqual("frank"))

    }

    @Test
    fun saveListChannelToDBAndRead() {

        val courseModel = CourseModel(
            type = "course",
            title = "title",
            coverAssetUrl = "https://assets.mindvalley.com/api/v1/assets/5bdbdd0e-3bd3-432b-b8cb-3d3556c58c94.jpg",
            channelTitle = "frank"
        )
        val listCourses = listOf<CourseModel>(courseModel)

        val series = CourseModel(
            title = "series title",
            coverAssetUrl = "https://assets.mindvalley.com/api/v1/assets/"
        )
        val listSeries = listOf<CourseModel>(series)

        val channelModel = ChannelModel(
            title = "Frank",
            series = listSeries,
            mediaCount = 1,
            courses = listCourses,
            coverAssetUrl = "https://assets.mindvalley.com/"
        )

        val courseModel2 = CourseModel(
            type = "course",
            title = "title2",
            coverAssetUrl = "https://assets.mindvalley.com/api/v1/assets/5bdbdd0e",
            channelTitle = "Frank2"
        )
        val listCourses2 = listOf<CourseModel>(courseModel2)

        val channelModel2 = ChannelModel(
            title = "Frank2",
            mediaCount = 1,
            courses = listCourses2,
            coverAssetUrl = "https://assets.mindvalley.com/"
        )

        val listChannelModels = listOf<ChannelModel>(channelModel, channelModel2)

        runBlocking { channelDataSourceLocal.saveListChannelToDB(listChannelModels) }

        val listSavedChannel = runBlocking { db.channelDao().getAllChannels() }

        assertThat(listSavedChannel, notNullValue())
        assertThat(listSavedChannel.size, IsEqual(2))

        val firstSavedChannel = listSavedChannel[0]
        assertThat(firstSavedChannel, notNullValue())
        assertThat(firstSavedChannel.channelId, IsEqual(1))
        assertThat(firstSavedChannel.title, IsEqual("Frank"))

        val listSeriesOfFirstSavedChannel = runBlocking {
            db.channelDao().getCoursesByChannelIdAndType(1, COURSE_DB_TYPE.series.name)
        }
        assertThat(listSeriesOfFirstSavedChannel, notNullValue())
        assertThat(listSeriesOfFirstSavedChannel.size, IsEqual(1))
        val firstSeries = listSeriesOfFirstSavedChannel[0]
        assertThat(firstSeries, notNullValue())
        assertThat(firstSeries.title, IsEqual("series title"))

        val listCoursesOfFirstSavedChannel = runBlocking {
            db.channelDao().getCoursesByChannelIdAndType(1, COURSE_DB_TYPE.course.name)
        }
        assertThat(listCoursesOfFirstSavedChannel, notNullValue())
        assertThat(listCoursesOfFirstSavedChannel.size, IsEqual(1))
        val firstCourse = listCoursesOfFirstSavedChannel[0]
        assertThat(firstCourse, notNullValue())
        assertThat(firstCourse.title, IsEqual("title"))

        val secondSavedChannel = listSavedChannel[1]
        assertThat(secondSavedChannel, notNullValue())
        assertThat(secondSavedChannel.channelId, IsEqual(2))
        assertThat(secondSavedChannel.title, IsEqual("Frank2"))

        val listSeriesOfSecondSavedChannel = runBlocking {
            db.channelDao().getCoursesByChannelIdAndType(2, COURSE_DB_TYPE.series.name)
        }
        assertThat(listSeriesOfSecondSavedChannel, notNullValue())
        assertThat(listSeriesOfSecondSavedChannel.size, IsEqual(0))

        val listCourseOfSecondSavedChannel = runBlocking {
            db.channelDao().getCoursesByChannelIdAndType(2, COURSE_DB_TYPE.course.name)
        }
        assertThat(listCourseOfSecondSavedChannel, notNullValue())
        assertThat(listCourseOfSecondSavedChannel.size, IsEqual(1))
        val courseOfSecondSavedChannel = listCourseOfSecondSavedChannel[0]
        assertThat(courseOfSecondSavedChannel, notNullValue())
        assertThat(courseOfSecondSavedChannel.title, IsEqual("title2"))

    }


    @Test
    fun getNewEpisodes_return_empty_courseModel() {

        val listCourseModels = runBlocking { channelDataSourceLocal.getNewEpisodes() }

        assertThat(listCourseModels, notNullValue())
        assertThat(listCourseModels?.size, IsEqual(0))
    }

    @Test
    fun getNewEpisodes_return_one_courseModel() {
        val courseDb1 = CourseDb(
            type = "course",
            title = "Frank",
            coverAssetUrl = "mindvalley.com",
            id = "11",
            channelTitle = "Little Human",
            dbCourseType = COURSE_DB_TYPE.new_episodes_type.name
        )
        val listCourseDbs = listOf<CourseDb>(courseDb1)
        runBlocking { db.channelDao().insertListCourses(listCourseDbs) }

        val listCourseModels = runBlocking { channelDataSourceLocal.getNewEpisodes() }
        assertThat(listCourseModels, notNullValue())
        assertThat(listCourseModels?.size, IsEqual(1))

        val courseModel = listCourseModels?.get(0)
        assertThat(courseModel, notNullValue())
        assertThat(courseModel?.title, IsEqual("Frank"))

    }


    @Test
    fun getChannels_return_one_chanelModel() {

        val channel = ChannelDb(
            title = "Mindvalley Mentoring",
            mediaCount = 98,
            iconAssetUrl = "https://edgecastcdn.net/80EC13/public/overmind2/asset/11914f01-ba4a-4d68-9c33-efb34c43ed23/channel-icon-mentoring_thumbnail.png",
            coverAssetUrl = "https://assets.mindvalley.com/api/v1/assets/8fd5837a-539c-4367-b1af-8579a3e3d461.jpg?transform=w_1080"
        )

        val channelId = runBlocking { db.channelDao().insertChannelAndGet(channel) }

        val courseDb1 = CourseDb(
            type = "course",
            title = "Frank",
            channelId = channelId.toInt(),
            coverAssetUrl = "mindvalley.com",
            id = "11",
            channelTitle = "Little Human",
            dbCourseType = COURSE_DB_TYPE.course.name
        )
        val listCourses = listOf<CourseDb>(courseDb1)
        runBlocking { db.channelDao().insertListCourses(listCourses) }

        val courseDb2 = CourseDb(
            type = "course",
            title = "MindValley",
            channelId = channelId.toInt(),
            coverAssetUrl = "mindvalley.com",
            channelTitle = "MindValley Tonight",
            dbCourseType = COURSE_DB_TYPE.series.name
        )
        val listSeries = listOf<CourseDb>(courseDb2)
        runBlocking { db.channelDao().insertListCourses(listSeries) }

        val listChannelModels = runBlocking { channelDataSourceLocal.getChannels() }

        assertThat(listChannelModels, notNullValue())
        assertThat(listChannelModels?.size, IsEqual(1))

        val channelModel = listChannelModels?.get(0)
        assertThat(channelModel, notNullValue())
        assertThat(channelModel?.title, IsEqual("Mindvalley Mentoring"))

        val listSeriesModel = channelModel?.series
        assertThat(listSeriesModel, notNullValue())
        assertThat(listSeriesModel?.size, IsEqual(1))
        val firstSeries = listSeriesModel?.get(0)
        assertThat(firstSeries?.title, IsEqual("MindValley"))

        val listCoursesModel = channelModel?.courses
        assertThat(listCoursesModel, notNullValue())
        assertThat(listCoursesModel?.size, IsEqual(1))
        val firstCourse = listCoursesModel?.get(0)
        assertThat(firstCourse?.title, IsEqual("Frank"))


    }

}