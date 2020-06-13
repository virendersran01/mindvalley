package com.frank.mindvalley.db.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.mindvalley.db.MindValleyDBTest
import com.frank.mindvalley.db.entities.CategoryDb
import com.frank.mindvalley.db.entities.ChannelDb
import com.frank.mindvalley.db.entities.CourseDb
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChannelDaoTest : MindValleyDBTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getEmptyCategory() {
        val resultCategories = db.channelDao().getCategories()

        assertThat(resultCategories, notNullValue())
        val sizeofResult = resultCategories.size
        assertThat(sizeofResult, IsEqual(0))
    }

    @Test
    fun insertOneCategoryAndRead() {
        val category = CategoryDb("frank")
        val categories = listOf<CategoryDb>(category)
        db.channelDao().insertListCategories(categories)

        val resultCategories = db.channelDao().getCategories()

        assertThat(resultCategories, notNullValue())
        val sizeofResult = resultCategories.size
        assertThat(sizeofResult, IsEqual(1))

        val resultCategory = resultCategories.get(0)
        assertThat(resultCategory, notNullValue())
        val categoryName = resultCategory.name
        assertThat(categoryName, IsEqual("frank"))
    }

    @Test
    fun insertTwoCategoriesAndRead() {
        val category1 = CategoryDb("frank")
        val category2 = CategoryDb("MindValley")
        val categories = listOf<CategoryDb>(category1, category2)
        db.channelDao().insertListCategories(categories)

        val resultCategories = db.channelDao().getCategories()

        assertThat(resultCategories, notNullValue())
        val sizeofResult = resultCategories.size
        assertThat(sizeofResult, IsEqual(2))
    }

    @Test
    fun deleteAllCategories() {
        val category1 = CategoryDb("frank")
        val category2 = CategoryDb("MindValley")
        val categories = listOf<CategoryDb>(category1, category2)

        db.channelDao().insertListCategories(categories)

        db.channelDao().deleteAllCategories()

        val resultCategories = db.channelDao().getCategories()

        assertThat(resultCategories, notNullValue())
        val sizeofResult = resultCategories.size
        assertThat(sizeofResult, IsEqual(0))

    }

    @Test
    fun getEmptyCourse() {
        val resultCourse = db.channelDao().getAllCourses()

        assertThat(resultCourse, notNullValue())
        val sizeOfResultCourse = resultCourse.size
        assertThat(sizeOfResultCourse, IsEqual(0))
    }

    @Test
    fun getEmptyCourseByType() {
        val resultCourse = db.channelDao().getCoursesByType("newepisode")

        assertThat(resultCourse, notNullValue())
        val sizeOfResultCourse = resultCourse.size
        assertThat(sizeOfResultCourse, IsEqual(0))
    }

    @Test
    fun insertCourseAndGetAllCourses() {
        val course = CourseDb(
            channelId = 1,
            type = "course",
            title = "Frank",
            coverAssetUrl = "mindvalley.com",
            id = "11",
            channelTitle = "Little Human",
            dbCourseType = "newepisode"
        )

        val listOfCourses = listOf<CourseDb>(course)
        db.channelDao().insertListCourses(listOfCourses)

        val resultCourses = db.channelDao().getAllCourses()

        assertThat(resultCourses, notNullValue())
        val sizeOfResultCourse = resultCourses.size
        assertThat(sizeOfResultCourse, IsEqual(1))

        val resultCourse = resultCourses[0]
        assertThat(resultCourse, notNullValue())
        //val courseId = resultCourse.courseId
        val channelId = resultCourse.channelId
        assertThat(channelId, IsEqual(1))
        val type = resultCourse.type
        assertThat(type, IsEqual("course"))
        val title = resultCourse.title
        assertThat(title, IsEqual("Frank"))
        val coverAssetUrl = resultCourse.coverAssetUrl
        assertThat(coverAssetUrl, IsEqual("mindvalley.com"))
        val id = resultCourse.id
        assertThat(id, IsEqual("11"))
        val channelTitle = resultCourse.channelTitle
        assertThat(channelTitle, IsEqual("Little Human"))
        val dbCourseType = resultCourse.dbCourseType
        assertThat(dbCourseType, IsEqual("newepisode"))
    }

    @Test
    fun insertCourseAndGetCourseByType() {
        val course = CourseDb(
            channelId = 1,
            type = "course",
            title = "Frank",
            coverAssetUrl = "mindvalley.com",
            id = "11",
            channelTitle = "Little Human",
            dbCourseType = "series"
        )

        val listOfCourses = listOf<CourseDb>(course)
        db.channelDao().insertListCourses(listOfCourses)

        val resultCourses = db.channelDao().getCoursesByType("series")

        assertThat(resultCourses, notNullValue())
        val sizeOfResultCourse = resultCourses.size
        assertThat(sizeOfResultCourse, IsEqual(1))

        val resultCourse = resultCourses.get(0)
        assertThat(resultCourse, notNullValue())
        val titleOfCourse = resultCourse.title
        assertThat(titleOfCourse, IsEqual("Frank"))
    }

    @Test
    fun insertTwoCourses() {
        val course = CourseDb(
            channelId = 1,
            type = "course",
            title = "Frank",
            coverAssetUrl = "mindvalley.com",
            id = "12",
            channelTitle = "Little Human",
            dbCourseType = "course"
        )

        val series = CourseDb(
            channelId = 1,
            type = "course",
            title = "Frank",
            coverAssetUrl = "mindvalley.com",
            id = "11",
            channelTitle = "Little Human",
            dbCourseType = "series"
        )

        val list = listOf<CourseDb>(course, series)

        db.channelDao().insertListCourses(list)

        val result = db.channelDao().getAllCourses()

        assertThat(result, notNullValue())
        val sizeOfResult = result.size
        assertThat(sizeOfResult, IsEqual(2))

        val resultCourse = result[0]
        assertThat(resultCourse, notNullValue())
        assertThat(resultCourse.courseId, IsEqual(1))

        val resultSeries = result[1]
        assertThat(resultSeries, notNullValue())
        assertThat(resultSeries.courseId, IsEqual(2))

    }

    @Test
    fun deleteCourseByType() {

        val newEpisode = CourseDb(
            channelId = 1,
            type = "course",
            title = "Frank",
            coverAssetUrl = "mindvalley.com",
            id = "12",
            channelTitle = "Little Human",
            dbCourseType = "new_episodes_type"
        )

        val course = CourseDb(
            channelId = 1,
            type = "course",
            title = "Frank",
            coverAssetUrl = "mindvalley.com",
            id = "12",
            channelTitle = "Little Human",
            dbCourseType = "course"
        )

        val series = CourseDb(
            channelId = 1,
            type = "course",
            title = "Frank",
            coverAssetUrl = "mindvalley.com",
            id = "11",
            channelTitle = "Little Human",
            dbCourseType = "series"
        )

        val list = listOf<CourseDb>(newEpisode, course, series)
        db.channelDao().insertListCourses(list)

        val allCourse1 = db.channelDao().getAllCourses()
        assertThat(allCourse1, notNullValue())
        assertThat(allCourse1.size, IsEqual(3))

        db.channelDao().deleteCoursesByType("series")

        val allCourse2 = db.channelDao().getAllCourses()
        assertThat(allCourse2, notNullValue())
        assertThat(allCourse2.size, IsEqual(2))

        val newEpisodesCourse =
            db.channelDao().getCoursesByType("new_episodes_type")
        assertThat(newEpisodesCourse, notNullValue())
        assertThat(newEpisodesCourse.size, IsEqual(1))

        val seriesCourse = db.channelDao().getCoursesByType("series")
        assertThat(seriesCourse, notNullValue())
        assertThat(seriesCourse.size, IsEqual(0))

    }


    @Test
    fun getCoursesByChannelIdAndType() {

        val newEpisode = CourseDb(
            type = "course",
            title = "Frank",
            coverAssetUrl = "mindvalley.com",
            id = "13",
            channelTitle = "Little Human",
            dbCourseType = "new_episodes_type"
        )

        val course = CourseDb(
            channelId = 1,
            type = "course",
            title = "Frank",
            coverAssetUrl = "mindvalley.com",
            id = "12",
            channelTitle = "Little Human",
            dbCourseType = "course"
        )

        val course2 = CourseDb(
            channelId = 1,
            type = "course",
            title = "Frank",
            coverAssetUrl = "mindvalley.com",
            id = "11",
            channelTitle = "Little Human",
            dbCourseType = "course"
        )

        val course3 = CourseDb(
            channelId = 2,
            type = "course",
            title = "Frank",
            coverAssetUrl = "mindvalley.com",
            id = "12",
            channelTitle = "Little Human",
            dbCourseType = "course"
        )

        val list = listOf<CourseDb>(newEpisode, course, course2, course3)
        db.channelDao().insertListCourses(list)

        val result = db.channelDao().getCoursesByChannelIdAndType(1,"course")
        assertThat(result, notNullValue())
        assertThat(result.size,IsEqual(2))

    }

    @Test
    fun getEmptyChannel() {
        val result = db.channelDao().getAllChannels()
        assertThat(result, notNullValue())
        assertThat(result.size, IsEqual(0))
    }

    @Test
    fun insertChannelAndRead() {
        val channel = ChannelDb(
            title = "Mindvalley Mentoring",
            mediaCount = 98,
            iconAssetUrl = "https://edgecastcdn.net/80EC13/public/overmind2/asset/11914f01-ba4a-4d68-9c33-efb34c43ed23/channel-icon-mentoring_thumbnail.png",
            coverAssetUrl = "https://assets.mindvalley.com/api/v1/assets/8fd5837a-539c-4367-b1af-8579a3e3d461.jpg?transform=w_1080"
        )

        db.channelDao().insertChannel(channel)

        val result = db.channelDao().getAllChannels()

        assertThat(result, notNullValue())
        assertThat(result.size, IsEqual(1))

        val resultChannel = result[0]
        assertThat(resultChannel, notNullValue())
        val channelId = resultChannel.channelId
        assertThat(channelId, IsEqual(1))
        assertThat(resultChannel.title, IsEqual("Mindvalley Mentoring"))
        assertThat(resultChannel.mediaCount, IsEqual(98))
        assertThat(
            resultChannel.iconAssetUrl,
            IsEqual("https://edgecastcdn.net/80EC13/public/overmind2/asset/11914f01-ba4a-4d68-9c33-efb34c43ed23/channel-icon-mentoring_thumbnail.png")
        )
        assertThat(
            resultChannel.coverAssetUrl,
            IsEqual("https://assets.mindvalley.com/api/v1/assets/8fd5837a-539c-4367-b1af-8579a3e3d461.jpg?transform=w_1080")
        )
    }

    @Test
    fun deleteChannel(){
        val channel = ChannelDb(
            title = "Mindvalley Mentoring",
            mediaCount = 98,
            iconAssetUrl = "https://edgecastcdn.net/80EC13/public/overmind2/asset/11914f01-ba4a-4d68-9c33-efb34c43ed23/channel-icon-mentoring_thumbnail.png",
            coverAssetUrl = "https://assets.mindvalley.com/api/v1/assets/8fd5837a-539c-4367-b1af-8579a3e3d461.jpg?transform=w_1080"
        )

        db.channelDao().insertChannel(channel)

        val result = db.channelDao().getAllChannels()

        assertThat(result, notNullValue())
        assertThat(result.size, IsEqual(1))

        db.channelDao().deleteAllChannels()
        val result2 = db.channelDao().getAllChannels()

        assertThat(result2, notNullValue())
        assertThat(result2.size, IsEqual(0))
    }

    @Test
    fun insertChannelAndGet() {
        val channel = ChannelDb(
            title = "Mindvalley Mentoring",
            mediaCount = 98,
            iconAssetUrl = "https://edgecastcdn.net/80EC13/public/overmind2/asset/11914f01-ba4a-4d68-9c33-efb34c43ed23/channel-icon-mentoring_thumbnail.png",
            coverAssetUrl = "https://assets.mindvalley.com/api/v1/assets/8fd5837a-539c-4367-b1af-8579a3e3d461.jpg?transform=w_1080"
        )

        val savedChannelId = db.channelDao().insertChannelAndGet(channel)

        assertThat(savedChannelId, notNullValue())
        assertThat(savedChannelId,IsEqual(1L))

    }

}