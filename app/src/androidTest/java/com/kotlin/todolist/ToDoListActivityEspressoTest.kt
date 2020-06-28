package com.kotlin.todolist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.kotlin.todolist.views.ToDoListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ToDoListActivityEspressoTest {

    @get:Rule
    var mActivityTestRule: ActivityTestRule<ToDoListActivity> = ActivityTestRule(
        ToDoListActivity::class.java, true, true
    )


    @Test
    fun startConnectedTesting() {
        ensureRecycleView()
        ensureToolbar()
    }

    private fun ensureToolbar() {
        onView(withId(R.id.toolbar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.toolbar_title))
            .check(ViewAssertions.matches(withText("Todo List")))
    }

    private fun ensureRecycleView() {
        onView(withId(R.id.shimmer_view_container))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}