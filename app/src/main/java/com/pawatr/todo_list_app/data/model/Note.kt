package com.pawatr.todo_list_app.data.model

data class Note(
    val id: Long = 0,
    val title: String,
    val description: String,
    val timeHour: Int = 0,
    val timeMinute: Int = 0,
    val isCompleted: Boolean = false
)