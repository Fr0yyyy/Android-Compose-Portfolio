Contexto y Guía del Proyecto: ToDoListApp
Este documento describe el objetivo final de la aplicación ToDoListApp. La meta es crear una aplicación de lista de tareas funcional y con buen diseño usando Jetpack Compose y siguiendo la arquitectura MVVM.
1. Estructura del Proyecto (Arquitectura MVVM)
•
Model (/data/Task.kt):
◦
Una data class llamada Task.
◦
Propiedades:
▪
id: UUID: Identificador único para cada tarea.
▪
text: String: El contenido de la tarea.
▪
isCompleted: Boolean: true si la tarea está marcada como completada, false por defecto.
•
ViewModel (/ui/TaskViewModel.kt):
◦
Hereda de ViewModel.
◦
Estado (State) que la UI observará:
▪
tasks: List<Task>: La lista actual de todas las tareas.
▪
newTaskText: String: El texto que el usuario está escribiendo en el TextField para una nueva tarea.
◦
Eventos (Functions) que la UI llamará para modificar el estado:
▪
onNewTaskTextChange(text: String): Actualiza newTaskText.
▪
addTask(): Añade una nueva tarea a la lista tasks si newTaskText no está vacío.
▪
removeTask(taskId: UUID): Elimina una tarea específica de la lista.
▪
onTaskCheckedChanged(task: Task, isChecked: Boolean): Cambia el estado isCompleted de una tarea.
▪
removeCompletedTasks(): Elimina de la lista todas las tareas que estén completadas.
•
View (/MainActivity.kt y /ui/TaskScreen.kt):
◦
MainActivity.kt: Punto de entrada. Crea la instancia del TaskViewModel y configura la estructura principal con Scaffold.
◦
TaskScreen.kt: El @Composable principal que contiene toda la UI y se comunica con el ViewModel.
2. Diseño de la Interfaz de Usuario (TaskScreen.kt)
La pantalla se dividirá en las siguientes partes, organizadas verticalmente dentro de un Scaffold:
•
TopAppBar:
◦
Título: Un Text con el valor "Lista de tareas".
◦
Acción: Un IconButton con un icono de papelera. Este botón solo debe ser visible si hay al menos una tarea completada.
▪
Evento: Al pulsarlo, llama a viewModel.removeCompletedTasks().
•
Zona Superior (dentro de un Row):
◦
TextField:
▪
Para introducir el texto de la nueva tarea.
▪
Su valor se sincroniza con viewModel.newTaskText.
▪
Cuando el usuario escribe, llama a viewModel.onNewTaskTextChange().
◦
Button:
▪
Texto: "Añadir".
▪
Evento: Al pulsarlo, llama a viewModel.addTask().
▪
Debería estar deshabilitado si viewModel.newTaskText está vacío.
•
Lista de Tareas (LazyColumn):
◦
Muestra los elementos de la lista viewModel.tasks.
◦
Cada elemento es una Card para darle un aspecto visual separado.
◦
Dentro de cada tarjeta (organizado en un Row):
▪
Checkbox:
▪
Su estado (checked) depende de task.isCompleted.
▪
Evento: Al cambiar su estado, llama a viewModel.onTaskCheckedChanged(task, isChecked).
▪
Text:
▪
Muestra el task.text.
▪
Si task.isCompleted es true, el texto debe aparecer tachado (textDecoration = TextDecoration.LineThrough).
▪
IconButton:
▪
Un icono de papelera a la derecha del todo.
▪
Evento: Al pulsarlo, llama a viewModel.removeTask(task.id).
•
Zona Inferior (debajo de la LazyColumn):
◦
Text:
▪
Muestra el contador de tareas.
▪
Formato: "Completadas: X / Total: Y".
▪
X es el número de tareas con isCompleted = true.
▪
Y es el tamaño total de la lista tasks.
3. Estilo y Colores
•
Fondo principal: Gris claro.
•
Colores de acento (TopAppBar, Botón "Añadir", CheckBox marcado): Tonalidades de Teal o verde oscuro.
•
Botón de borrar (icono): Rojo suave o similar.
•
Usar el Theme de la aplicación (/ui/theme/Color.kt y Theme.kt) para definir y aplicar estos colores de forma consistente.