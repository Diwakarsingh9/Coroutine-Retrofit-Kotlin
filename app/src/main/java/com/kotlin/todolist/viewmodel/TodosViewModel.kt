package com.kotlin.todolist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.todolist.api.ApiBuilder
import com.kotlin.todolist.model.ApiResponse
import com.kotlin.todolist.model.ApiResponse.Error
import com.kotlin.todolist.model.ApiResponse.TodosList
import kotlinx.coroutines.*


class TodosViewModel : ViewModel() {

    private var todos = MutableLiveData<ApiResponse>()
    private var job: CompletableJob? = null

    init {
        fetchTodos()
    }

    private fun fetchTodos() {
        job = Job()
        job?.let { it ->
            CoroutineScope(Dispatchers.IO + it).launch {
                withContext(Dispatchers.Main) {
                    it.complete()
                    try {
                        todos.postValue(TodosList(ApiBuilder.apiService.getTodos()))
                    } catch (e: Exception) {
                        Log.e("ViewModelGetTodosError", "reason : " + e)
                        todos.postValue(Error(e.toString()))
                    }
                }
            }
        }
    }

    fun cancelJobs() {
        job?.cancel()
    }

    fun getTodos(): LiveData<ApiResponse> {
        return todos
    }

}
