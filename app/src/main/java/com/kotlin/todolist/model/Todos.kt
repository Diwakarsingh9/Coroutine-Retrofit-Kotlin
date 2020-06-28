package com.kotlin.todolist.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Todos(
    @Expose
    @SerializedName("completed")
    var completed: Boolean? = null,

    @Expose
    @SerializedName("id")
    var id: Int? = null,

    @Expose
    @SerializedName("title")
    var title: String? = null,

    @Expose
    @SerializedName("userId")
    var userId: Int? = null
)