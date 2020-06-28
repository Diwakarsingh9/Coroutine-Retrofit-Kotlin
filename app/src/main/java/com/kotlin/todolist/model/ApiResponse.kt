package com.kotlin.todolist.model

sealed class ApiResponse {

    data class Error(var error: String) : ApiResponse()
    data class TodosList(var todolist: List<Todos>) : ApiResponse()
}