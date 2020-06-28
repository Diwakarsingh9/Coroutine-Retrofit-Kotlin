package com.kotlin.todolist.api

import com.kotlin.todolist.model.Todos
import retrofit2.http.GET

interface ApiService {

    @GET("todos")
    suspend fun getTodos(): List<Todos>
}