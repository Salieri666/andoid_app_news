package ru.example.andoid_app_news.ui.tab

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.example.andoid_app_news.MainActivity
import ru.example.andoid_app_news.R


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkNewsTab() {
        waitForDialogWithId(R.id.newsFragment, 10000)

        onView(withId(R.id.newsFragment))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recycler_news_tab)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
            (0, click()))

    }

    @Test
    fun checkBookmarksTab() {
        waitForDialogWithId(R.id.newsFragment, 10000)

        onView(withId(R.id.bookmarksFragment)).perform(click())
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkSettingTab() {
        waitForDialogWithId(R.id.newsFragment, 10000)

        onView(withId(R.id.settingsFragment)).perform(click())
            .check(matches(isDisplayed()))
    }

    private fun waitForDialogWithId(id: Int, timeout: Long) {
        waitForDialog(withId(id), timeout)
    }

    private fun waitForDialog(viewMatcher: Matcher<View>, timeout: Long) {
        val endTime = System.currentTimeMillis() + timeout
        while (System.currentTimeMillis() < endTime) {
            try {
                onView(viewMatcher).check(matches(isDisplayed()))
                return
            } catch (e: Exception) {

            }
        }
    }

}