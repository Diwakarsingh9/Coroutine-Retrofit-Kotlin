package com.kotlin.todolist

import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.kotlin.todolist.model.Todos
import com.kotlin.todolist.viewmodel.TodosViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.reflect.Whitebox
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class TodosViewModelTest {

    private lateinit var viewModel: TodosViewModel

    @Before
    fun setUp() {
        viewModel = TodosViewModel()
    }

    @Test
    fun loadTodosShouldShowDataTest() = runBlocking {
        assertNotNull(viewModel.getTodos())
        var response = viewModel.getTodos().value
        val todosListLiveData: MutableLiveData<Todos> = MutableLiveData()
        response?.let { assertEquals(it, todosListLiveData) }
        return@runBlocking
    }

    @Test
    fun cancelJobsTest() {
        Whitebox.invokeMethod<TodosViewModel>(viewModel, "cancelJobs")
    }
}