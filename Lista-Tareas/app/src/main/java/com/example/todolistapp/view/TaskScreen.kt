package com.example.todolistapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolistapp.model.Task
import com.example.todolistapp.ui.TaskViewModel


@Composable
fun Screen(viewModel: TaskViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(all = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = viewModel.newTaskText,
                onValueChange = { viewModel.newTaskTextChange(it) },
                label = { Text("Nueva tarea") },
                modifier = Modifier
                    .weight(3f)
                    .padding(horizontal = 6.dp)
            )
            Button(
                onClick = { viewModel.addTask() },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 6.dp)
            ) {
                Text("Añadir")

            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(top = 10.dp)
                .padding(bottom = 10.dp)
                .padding(horizontal = 6.dp)
        ) { }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit, // Evento para el Checkbox
    onRemoveTask: () -> Unit // Evento para el botón de borrar
){}
