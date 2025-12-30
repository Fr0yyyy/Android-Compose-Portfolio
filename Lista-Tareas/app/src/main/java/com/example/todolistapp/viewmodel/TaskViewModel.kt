package com.example.todolistapp.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.todolistapp.model.Task

class TaskViewModel : ViewModel() {

    //El texto de la nueva tarea
    var newTaskText by mutableStateOf((""))
        private set
    //Lista mutable de tareas
    var tasks by mutableStateOf<List<Task>>(emptyList())
        private set //Para que solo sea observable por el View

    val showClearCompletedButton by derivedStateOf { //Lógica para mostrar o no el botón de borrar tareas completadas

        tasks.any { it.isCompleted }
    }

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
    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) {

        tasks = tasks.map {
            if (it.id == task.id) {
                // Si es la tarea que buscamos, se devuelve una copia con el nuevo estado
                it.copy(isCompleted = isChecked)
            } else {
                it
            }
        }
    }

    //Función para eliminar todas las tareas completadas
    fun removeTaskCompleted() {
        tasks = tasks.filter { !it.isCompleted }
    }


}
