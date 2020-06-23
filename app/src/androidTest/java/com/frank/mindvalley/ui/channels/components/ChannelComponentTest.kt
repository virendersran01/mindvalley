package com.frank.mindvalley.ui.channels.components

import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.mindvalley.models.ChannelModel
import com.frank.mindvalley.models.CourseModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.frank.mindvalley.MainActivity
import com.frank.mindvalley.R
import com.frank.mindvalley.di.ViewModelFactory
import com.frank.mindvalley.ui.channels.ChannelsFragment
import com.frank.mindvalley.ui.channels.ChannelsViewModel
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNull.notNullValue
import org.hamcrest.core.IsNull.nullValue

@RunWith(AndroidJUnit4::class)
class ChannelComponentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    lateinit var channelComponent: ChannelComponent

    @Before
    fun init(){
        val channelViewModel: ChannelsViewModel = mock(ChannelsViewModel::class.java)
        val mockViewModelFactory: ViewModelFactory = mock(ViewModelFactory::class.java)
        val scenario = launchFragmentInContainer (
            themeResId = R.style.AppTheme) {
            ChannelsFragment().apply {
                viewModelFactory = mockViewModelFactory
                viewModel = channelViewModel

            }
        }
    }

    @Test
    fun createView_return_not_null() {
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
        channelComponent =
            ChannelComponent(ApplicationProvider.getApplicationContext(), channelModel)

        val resultView = channelComponent.createView()

        assertThat(resultView, notNullValue())
        onView(withId(resultView.findViewById<TextView>(R.id.tvTitleChannel).id)).check(
            matches(
                withText("Frank")
            )
        )

    }


}