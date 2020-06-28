package com.kotlin.todolist

import android.os.Build
import com.kotlin.todolist.util.InternetNetwork
import com.kotlin.todolist.views.ToDoListActivity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class InternetNetworkTest {

    private lateinit var activity: ToDoListActivity
    private lateinit var activityController: ActivityController<ToDoListActivity>

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        activityController = Robolectric.buildActivity(ToDoListActivity::class.java)
        activity = activityController.get()
    }


    @Test
    fun `is Online Test`() {
        InternetNetwork.isOnline(activity)
        assertEquals(false, InternetNetwork.isOnline(activity))
    }

}
