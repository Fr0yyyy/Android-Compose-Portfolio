# Práctica SharedPreferences - Android App

## Descripción General

Esta aplicación es un proyecto de portfolio diseñado para demostrar el manejo de persistencia de datos locales en Android utilizando `SharedPreferences`. La app presenta una pantalla de inicio de sesión donde el usuario puede introducir sus credenciales, guardarlas y, si las credenciales son correctas, acceder a una pantalla de bienvenida.

El objetivo principal de este proyecto es mostrar una implementación limpia y moderna de la arquitectura Android, siguiendo las mejores prácticas recomendadas por Google.

## Capturas de Pantalla


## Características Implementadas

- **Pantalla de Login:** Interfaz de usuario para que el usuario introduzca su nombre y contraseña.
- **Guardado de Preferencias:** Funcionalidad para persistir las credenciales del usuario de forma local usando `SharedPreferences`.
- **Validación de Credenciales:** Lógica para comprobar si los datos introducidos por el usuario coinciden con los guardados previamente.
- **Navegación Condicional:** Flujo de navegación que dirige al usuario a una pantalla de bienvenida solo si las credenciales son correctas.
- **Feedback al Usuario:** Uso de `Snackbar` para notificar al usuario en caso de que las credenciales sean incorrectas.
- **Interfaz Reactiva:** La UI se actualiza automáticamente gracias al uso de `StateFlow` y `collectAsState`.

## Arquitectura y Patrones de Diseño

La aplicación está construida siguiendo una arquitectura MVVM (Model-View-ViewModel), recomendada oficialmente para el desarrollo de Android.

### Componentes Principales

1.  **Model (Capa de Datos):**
    -   **`UserPreferencesRepository`**: Es la única fuente de verdad (`Single Source of Truth`) para los datos de las credenciales. Su responsabilidad es abstraer por completo el origen de los datos. El resto de la app no sabe (ni necesita saber) que se están usando `SharedPreferences`. Esto permite que, en un futuro, se pueda cambiar el sistema de almacenamiento (por ejemplo, a `DataStore`) modificando únicamente este repositorio, sin afectar al resto de la aplicación.

2.  **ViewModel (Capa de Lógica de UI):**
    -   **`LoginViewModel`**: Actúa como intermediario entre la Vista y el Repositorio. Hereda de `AndroidViewModel`, lo que le permite obtener el `ApplicationContext` de forma segura para instanciar el `UserPreferencesRepository`.
    -   **Gestión de Estado:** Expone el estado de la UI (`LoginUiState`) a través de un `StateFlow`, asegurando un flujo de datos unidireccional y predecible. La UI observa este flujo y reacciona a los cambios.
    -   **Manejo de Eventos:** Procesa los eventos de la UI (cambios en los `TextFields`, clics en los botones) y ejecuta la lógica de negocio correspondiente, como guardar las credenciales o validar el login.
    -   **Corrutinas y `Dispatchers`:** Utiliza `viewModelScope` para lanzar corrutinas. Las operaciones de lectura/escritura en `SharedPreferences` se delegan al `Dispatchers.IO` para no bloquear el hilo principal, garantizando una experiencia de usuario fluida.

3.  **View (Capa de UI):**
    -   Construida 100% con **Jetpack Compose**, el toolkit de UI moderno de Android.
    -   **`LogInScreen` y `WelcomeScreen`**: Son funciones `Composable` que no tienen estado propio (`stateless`). Su única responsabilidad es "pintar" el estado que reciben del `ViewModel` y notificarle los eventos del usuario.
    -   **Navegación:** Se utiliza el componente **Jetpack Navigation para Compose** (`NavHost`, `NavController`) para gestionar el flujo entre pantallas. Se definen rutas (`routes`) como `Strings` y se pasan argumentos (usuario y contraseña) a través de la ruta para la pantalla de bienvenida.

## Decisiones de Diseño y Buenas Prácticas

- **Inyección de Dependencias (Manual):** Aunque en este proyecto se usa `AndroidViewModel` para simplificar, la estructura está preparada para escalar a un sistema de inyección de dependencias más robusto como Hilt o Koin. La separación de responsabilidades es clara.
- **Estado Inmutable:** El `ViewModel` expone el estado a través de un `StateFlow` inmutable, mientras que internamente utiliza un `MutableStateFlow` privado. Esto garantiza que solo el `ViewModel` pueda modificar el estado, siguiendo los principios de la programación reactiva.
- **Uso de `Scaffold`:** La estructura principal de las pantallas se organiza con el `Composable` `Scaffold`, permitiendo integrar fácilmente componentes como el `SnackbarHost`.
- **Prevención de Errores Comunes:** Se evita el uso de `LocalContext` para la creación de dependencias de larga duración, optando por `AndroidViewModel` para una gestión segura del ciclo de vida.
- **Código Limpio y Legible:** Se han utilizado nombres de variables y funciones descriptivos, y se han añadido comentarios para explicar las partes más complejas del código, como la lógica de la navegación y el manejo de corrutinas.

## Cómo Ejecutar el Proyecto

1.  Clona este repositorio.
2.  Abre el proyecto en Android Studio.
3.  Sincroniza las dependencias de Gradle.
4.  Ejecuta la aplicación en un emulador o en un dispositivo físico.

Este proyecto demuestra mi comprensión de los principios de la arquitectura moderna de Android y mi capacidad para construir aplicaciones robustas, mantenibles y eficientes. ¡Gracias por revisar mi trabajo!