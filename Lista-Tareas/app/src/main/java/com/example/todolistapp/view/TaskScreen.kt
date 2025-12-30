package com.example.todolistapp.view



import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.todolistapp.model.Task
import com.example.todolistapp.ui.TaskViewModel


@Composable
fun TaskItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit, // Evento para el Checkbox
    onRemoveTask: () -> Unit // Evento para el botón de borrar
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = task.isCompleted, onCheckedChange = onCheckedChange)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = task.text,
                modifier = Modifier.weight(1f),
                textDecoration = if (task.isCompleted) {
                    TextDecoration.LineThrough
                } else {
                    null
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onRemoveTask) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Borrar tarea"
                )
            }
        }


    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(viewModel: TaskViewModel) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de tareas") },
                actions = {
                    // Según el ejercicio, la AppBar tiene un botón para borrar las completadas
                    if (viewModel.tasks.any { it.isCompleted }) {
                        IconButton(onClick = { viewModel.removeTaskCompleted() }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar tareas completadas"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            // CAMBIO: El botón de borrar ahora está en la BottomBar
            // Usamos el estado del ViewModel que deberías haber creado (showClearCompletedButton)
            if (viewModel.showClearCompletedButton) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { viewModel.removeTaskCompleted() }) {
                        Text("Borrar completadas")
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = viewModel.newTaskText,
                    onValueChange = { viewModel.newTaskTextChange(it) },
                    label = { Text("Nueva tarea") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 6.dp)
                )
                Button(
                    onClick = { viewModel.addTask() },
                    enabled = viewModel.newTaskText.isNotBlank(),
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                ) {
                    Text("Añadir")

                }
            }

            val completedTasks = viewModel.tasks.count { it.isCompleted }
            val totalTasks = viewModel.tasks.size
            Text(
                text = "Completadas: $completedTasks / Total: $totalTasks",
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {


                items(viewModel.tasks, key = { it.id }) { task ->

                    TaskItem(
                        task = task,
                        onCheckedChange = { isChecked -> //Parámetro inventado que almacena si es true o false, lo hace el propio componente del checkbox
                            // Cuando el checkbox cambia, se notifica al ViewModel
                            viewModel.onTaskCheckedChanged(task, isChecked)
                        },
                        onRemoveTask = {
                            // Cuando se pulsa el botón de borrar, se notifica al ViewModel
                            viewModel.removeTask(task)
                        }
                    )
                }
            }
        }
    }
}
