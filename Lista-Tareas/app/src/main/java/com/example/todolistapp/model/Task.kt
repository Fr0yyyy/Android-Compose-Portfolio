package com.example.todolistapp.model

import java.util.UUID

data class Task(

    val id: UUID = UUID.randomUUID(),
    val text: String,
    val isCompleted: Boolean = false
)