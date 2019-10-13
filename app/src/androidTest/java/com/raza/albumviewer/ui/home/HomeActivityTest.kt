package com.raza.albumviewer.ui.home


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.raza.albumviewer.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class HomeActivityTest {
    private val DELAY = 2000L

    @get:Rule
    private val activityRule = ActivityTestRule(
        HomeActivity::class.java, false, false
    )

    @Before
    fun setup() {
        activityRule.launchActivity(null)
    }

    @Test
    @Throws(Exception::class)
    fun launchAndSearch() {
        onView(withId(R.id.menu_search)).perform(click())
        onView(withId(R.id.search_src_text))
            .perform(replaceText("Trump"))
        runBlocking {
            delay(DELAY)
            onView(withId(R.id.search_src_text))
                .perform(pressImeActionButton())
            delay(DELAY)
            onView(withId(R.id.artist_items))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,
                        click()
                    )
                )
            delay(DELAY)
            onView(withId(R.id.album_items))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,
                        click()
                    )
                )
            delay(DELAY)
            onView(withId(R.id.save)).perform(click())
            delay(DELAY)
            onView(withId(R.id.album_items)).perform(pressBack())
            delay(DELAY)
            onView(withId(R.id.artist_items)).perform(pressBack())
            delay(DELAY)
        }
    }
}