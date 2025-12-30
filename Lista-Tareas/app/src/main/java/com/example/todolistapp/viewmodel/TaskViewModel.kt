package com.example.todolistapp.ui

import androidx.compose.animation.core.copy
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.todolistapp.model.Task
import java.util.UUID

class TaskViewModel : ViewModel() {

    //El texto de la nueva tarea
    var newTaskText by mutableStateOf((""))
        private set
    //Lista mutable de tareas
    var tasks by mutableStateOf<List<Task>>(emptyList())
        private set //Para que solo sea observable por el View

    //Función para cambiar el texto de la nueva tarea
    fun newTaskTextChange(text: String) {
        newTaskText = text
    }

    //Función para agregar una nueva tarea
    fun addTask() {
        if (newTaskText.isNotBlank()) {
            tasks = tasks + Task(text = newTaskText)
            newTaskText = ""
        }
    }

    //Función para eliminar una tarea
    fun removeTask(taskToRemove: Task) {
        tasks = tasks - taskToRemove
    }

    //Función para marcar una tarea como completada
    fun taskCompletedCheck(taskToComplete: Task) {
        if (taskToComplete.isCompleted == true) {
            tasks = tasks - taskToComplete
        }
    }

    //Función para eliminar todas las tareas completadas
    fun removeTaskCompleted() {
        tasks = tasks.filter { !it.isCompleted }
    }
}
