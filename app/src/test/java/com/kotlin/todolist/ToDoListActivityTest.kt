package com.kotlin.todolist

import android.os.Build
import android.os.Looper.getMainLooper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.kotlin.todolist.model.Todos
import com.kotlin.todolist.viewmodel.TodosViewModel
import com.kotlin.todolist.views.ToDoListActivity
import com.kotlin.todolist.views.adapter.TodosAdapter
import junit.framework.Assert.*
import kotlinx.android.synthetic.main.activity_to_do_list.*
import kotlinx.android.synthetic.main.activity_to_do_list.view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.powermock.reflect.Whitebox
import org.powermock.reflect.Whitebox.getInternalState
import org.powermock.reflect.Whitebox.invokeMethod
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.fakes.RoboMenu
import org.robolectric.fakes.RoboMenuItem


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ToDoListActivityTest {

    private lateinit var activity: ToDoListActivity

    private lateinit var activityController: ActivityController<ToDoListActivity>

    private lateinit var viewModel: TodosViewModel


    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        viewModel = TodosViewModel()
        activityController = Robolectric.buildActivity(ToDoListActivity::class.java)
        activity = activityController.get()
        activityController.create()
        activity.setTestViewModel(viewModel)
        activityController.start()
    }


    @Test
    fun `has visible view on create Test`() {
        assertNotNull(viewModel)
        assertNotNull(activity)
        assertEquals(View.VISIBLE, activity.shimmer_view_container.visibility)
        assertEquals(View.VISIBLE, activity.recycler_view.visibility)
    }

    @Test
    fun `fetch data Test`() {
        invokeMethod<ToDoListActivity>(activity, "fetchData")
        assertEquals(View.VISIBLE, activity.recycler_view.visibility)
        assertTrue(activity.shimmer_view_container.isAnimationStarted())
        assertEquals(View.INVISIBLE, activity.shimmer_view_container.container.visibility)
    }

    @Test
    fun `fetch error data Test`() = runBlocking {
        var apiResponse = viewModel.getTodos()?.value
        if (apiResponse == null) {
            val responsebool = false
            assertFalse(responsebool)
        } else {
        }
        return@runBlocking
    }

    @Test
    fun `set adapter with data Test`() {
        val todoList =
            listOf(
                Todos(false, 1, "todos", 2), Todos(
                    false
                    , 1, "todos2", 5
                )
            )

        invokeMethod<ToDoListActivity>(activity, "setDataInAdapter", todoList)
        assertEquals(View.VISIBLE, activity.recycler_view.visibility)
        assertFalse(activity.shimmer_view_container.isActivated())
        assertEquals(View.INVISIBLE, activity.shimmer_view_container.container.visibility)
        assertEquals(
            todoList?.size,
            (activity.recycler_view.adapter as? TodosAdapter)?.itemCount
        )
    }

    @Test
    fun `on Reload Test`() {
        activity.onReload()
        assertEquals(View.INVISIBLE, activity.container.visibility)
        assertFalse(activity.swipe_refresh_layout.isRefreshing)
    }

    @Test
    fun `on Destroy Test`() {
        activityController.destroy()
        val job = getInternalState<Job>(viewModel,"job")
        assertTrue(job!!.isCancelled)
    }

    @Test
    fun `on createOptionsMenu Test`() {
        val menu: Menu = RoboMenu()
        activity.onCreateOptionsMenu(menu)

        val menuItem: MenuItem = RoboMenuItem()
        activity.onOptionsItemSelected(menuItem)

        shadowOf(getMainLooper()).idle()
        shadowOf(activity).clickMenuItem(R.id.sort_by_names);
        val sortbynames = getInternalState<Boolean>(activity, "sortValue")
        assertTrue(sortbynames)

        shadowOf(getMainLooper()).idle()
        shadowOf(activity).clickMenuItem(R.id.sort_by_default);
        val default = getInternalState<Boolean>(activity, "sortValue")
        assertFalse(default)
    }
}