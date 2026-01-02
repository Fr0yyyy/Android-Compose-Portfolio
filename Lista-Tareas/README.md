# Lista de Tareas - App Android con Jetpack Compose

Este proyecto es una aplicación de lista de tareas (To-Do List) desarrollada para Android utilizando tecnologías modernas. El objetivo principal es demostrar mis habilidades en el desarrollo de aplicaciones nativas con Kotlin y Jetpack Compose, siguiendo las mejores prácticas de arquitectura y diseño de software.

## 📷 App en funcionamiento

**



## ✨ Características

- **Añadir Tareas**: Campo de texto y botón para agregar nuevas tareas a la lista.
- **Marcar Tareas como Completadas**: Checkbox para llevar un seguimiento visual de las tareas finalizadas. El texto de la tarea aparece tachado.
- **Eliminar Tareas Individuales**: Botón para borrar una tarea específica.
- **Eliminar Taras Completadas**: Un icono en la barra superior aparece cuando hay tareas completadas, permitiendo eliminarlas todas a la vez.
- **Contador de Tareas**: Muestra el número total de tareas y cuántas han sido completadas.
- **UI Reactiva**: La interfaz se actualiza automáticamente al añadir, modificar o eliminar tareas.

## 🛠️ Tecnologías y Arquitectura

Este proyecto está construido 100% en **Kotlin** y sigue una filosofía de desarrollo moderna de Android.

- **Lenguaje**: [Kotlin](https://kotlinlang.org/)
- **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetpack/compose) para la construcción de la interfaz de usuario de forma declarativa.
- **Arquitectura**: **MVVM (Model-View-ViewModel)**.
    - **Model**: La capa de datos, representada por la data class `Task`, que define la estructura de una tarea.
    - **View**: La interfaz de usuario (`TaskScreen.kt`), construida con Composables que observan el estado del ViewModel.
    - **ViewModel**: `TaskViewModel` actúa como intermediario, gestionando la lógica de negocio y el estado de la UI.
- **Gestión de Estado**: Se utiliza `mutableStateOf` y `derivedStateOf` dentro del `TaskViewModel` para exponer el estado a la UI. Esto asegura un flujo de datos unidireccional y una UI predecible.
- **Componentes de Arquitectura de Android**:
    - `ViewModel`: Para mantener el estado de la UI a salvo de cambios de configuración (como la rotación de pantalla).
    - `by viewModels()`: Delegado de propiedad de KTX para instanciar el ViewModel de forma concisa y correcta en la `Activity`.
- **Diseño de UI**: [Material 3](https://m3.material.io/), la última versión del sistema de diseño de Google, utilizando componentes como `Scaffold`, `TopAppBar`, `Card`, `Checkbox`, etc.

## 🧠 Decisiones de Diseño y Arquitectura

La elección de la arquitectura y las herramientas no fue al azar. A continuación, explico las decisiones más importantes:

### 1. ¿Por qué MVVM?

La arquitectura MVVM (Model-View-ViewModel) es ideal para el desarrollo con Jetpack Compose.
- **Separación de Responsabilidades**: El `ViewModel` se encarga de toda la lógica y la manipulación de datos, mientras que la `View` (los Composables) solo se dedica a mostrar el estado actual. Esto hace que el código sea más limpio, fácil de entender y de mantener.
- **Testabilidad**: Al aislar la lógica en el ViewModel, se pueden realizar pruebas unitarias sobre ella sin necesidad de un dispositivo o emulador.
- **Ciclo de Vida**: El componente `ViewModel` de Android está diseñado para sobrevivir a cambios de configuración, evitando la pérdida de estado y la recarga innecesaria de datos.

### 2. Flujo de Datos Unidireccional (UDF)

La comunicación entre la UI y el ViewModel sigue un patrón de flujo de datos unidireccional:
- **Estado hacia abajo**: El `ViewModel` expone el estado (la lista de tareas `tasks` y el texto de la nueva tarea `newTaskText`) a la UI. La UI no puede modificar este estado directamente.
- **Eventos hacia arriba**: La UI notifica al `ViewModel` sobre las acciones del usuario (ej: `addTask()`, `removeTask(id)`). El `ViewModel` procesa estos eventos, actualiza su propio estado y, como resultado, la UI se recompone para reflejar los cambios.

Este patrón hace que el flujo de datos sea predecible y fácil de depurar.

### 3. Inmutabilidad del Estado

En el `TaskViewModel`, la lista de tareas (`tasks`) es inmutable. Cuando se añade o elimina una tarea, no se modifica la lista existente, sino que se crea una **nueva lista** y se le asigna a la variable de estado.

```kotlin
// Ejemplo al añadir una tarea
tasks = tasks + Task(text = newTaskText)
```

Este enfoque es fundamental para que Jetpack Compose detecte los cambios de estado de manera eficiente y decida qué partes de la UI necesita redibujar.

## 🚀 Cómo ejecutar el proyecto

1. Clona este repositorio: `git clone https://github.com/tu-usuario/tu-repositorio.git`
2. Abre el proyecto con Android Studio.
3. Espera a que Gradle sincronice las dependencias.
4. Ejecuta la aplicación en un emulador o dispositivo físico.

## 🔮 Futuras Mejoras

Este proyecto es una base sólida. Algunas mejoras que se podrían implementar son:
- **Persistencia de Datos**: Utilizar **Room** o **DataStore** para que las tareas se guarden localmente y no se pierdan al cerrar la aplicación.
- **Inyección de Dependencias**: Integrar **Hilt** para gestionar las dependencias de forma más robusta y facilitar las pruebas.
- **Pruebas Unitarias y de UI**: Añadir pruebas unitarias para el `ViewModel` y pruebas de instrumentación para la UI con Compose.
- **Navegación**: Implementar una pantalla de detalle para cada tarea usando **Jetpack Navigation Compose**.
