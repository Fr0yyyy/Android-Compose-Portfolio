package com.example.todolistapp.data

import java.util.UUID

data class Task(

    val id: UUID = UUID.randomUUID(),
    val text: String,
    val isCompleted: Boolean = false
)