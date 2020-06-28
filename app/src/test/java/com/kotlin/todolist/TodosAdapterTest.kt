package com.kotlin.todolist

import android.os.Build
import androidx.constraintlayout.widget.ConstraintLayout
import com.kotlin.todolist.model.Todos
import com.kotlin.todolist.views.ToDoListActivity
import com.kotlin.todolist.views.adapter.TodosAdapter
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.android.synthetic.main.layout_parent.view.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.internal.util.reflection.Whitebox.setInternalState
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class TodosAdapterTest {

    lateinit var list: List<Todos>

    lateinit var adapter: TodosAdapter

    private lateinit var activity: ToDoListActivity

    private lateinit var activityController: ActivityController<ToDoListActivity>

    lateinit var holder: TodosAdapter.TodoViewHolder

    @Before
    fun setUp() {
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks()
        list = ArrayList()
        activityController = Robolectric.buildActivity(ToDoListActivity::class.java)
        activity = activityController.get()
        adapter = TodosAdapter(list)
        initHolder()
    }

    private fun initHolder() {
        holder = adapter.onCreateViewHolder(ConstraintLayout(activity), 0)
    }

    @Test
    fun onCreateViewHolderTest() {
        val createHolder = adapter.onCreateViewHolder(ConstraintLayout(activity), 0)
        assertNotNull(createHolder)
    }

    @Test
    fun onBindViewHolderTest() {
        val todoList =
            listOf(
                Todos(false, 1, "todos", 2), Todos(
                    false
                    , 1, "todos2", 5
                )
            )

        setInternalState(adapter, "list", todoList)
        adapter.onBindViewHolder(holder, 1)
        assertEquals(holder.itemView.title.text, "Todos2");
//        val userId:List<Todos>  = getInternalState(adapter,"list") as List<Todos>
//        assertEquals(userId.get(0).userId, 2);

    }

    @Test
    fun getItemCountTest() {
        val todoList =
            listOf(
                Todos(false, 1, "todos", 2), Todos(
                    false
                    , 1, "todos2", 5
                )
            )

        setInternalState(adapter, "list", todoList)
        assertNotNull(adapter.itemCount)
        assertEquals(adapter.itemCount, todoList.size)
    }

    @After
    fun tearDown() {
    }
}