# Catálogo de Cursos - Práctica Android con Jetpack Compose

Este proyecto es una aplicación de Android sencilla desarrollada con Jetpack Compose que
 muestra un catálogo de cursos de programación. La aplicación presenta una lista de cursos en tarjetas, cada una con detalles como el título, nivel, duración y etiquetas relevantes.

## Características

*   **UI Moderna con Jetpack Compose:** La interfaz está construida enteramente con Jetpack Compose, el moderno toolkit de UI
 de Android.
*   **Diseño con Material 3:** Se utilizan componentes de Material 3 para seguir las guías de diseño de Google.
*   **Lista Dinámica:** `LazyColumn` se usa para mostrar la lista de cursos de manera eficiente.
*   **Interactividad:** Las tarjetas
 de los cursos reaccionan al tacto con una animación de resaltado.
*   **Arquitectura Sencilla:** El código está estructurado de forma clara, separando el modelo de datos (`Course`), la fuente de datos (lista de ejemplo) y los componentes de la UI.

## Vista Pre
via

Aquí hay una captura de pantalla de la aplicación en funcionamiento:

*(Aquí iría una captura de pantalla de la aplicación, por ejemplo `screenshot.png`)*

## Estructura del Proyecto

El código fuente principal se encuentra en `app/src/main/java/com/example/pract
ica_evaluable_ej3/MainActivity.kt`.

*   `MainActivity.kt`: Contiene toda la lógica de la aplicación, incluyendo:
    *   El modelo de datos `Course`.
    *   El conjunto de datos de ejemplo `courses`.
    *   Los composables `@Composable
` que definen la UI: `CatalogApp`, `CourseCard`, `LevelChip`, y `TagChip`.
*   `app/src/main/java/com/example/practica_evaluable_ej3/ui/theme/`: Contiene los archivos de configuración del tema de la
 aplicación (colores, tipografía y tema general).

## Cómo Compilar y Ejecutar

1.  Clona este repositorio en tu máquina local.
2.  Abre el proyecto en Android Studio.
3.  Deja que Gradle sincronice las dependencias del proyecto.
4.  Ej
ecuta la aplicación en un emulador o en un dispositivo físico.

## Tecnologías Utilizadas

*   [Kotlin](https://kotlinlang.org/)
*   [Jetpack Compose](https://developer.android.com/jetpack/compose)
*   [Material 3](https://
m3.material.io/)
*   [Android Studio](https://developer.android.com/studio)

---

Este proyecto fue creado como parte de una práctica evaluable.