package com.example.practica_evaluable_ej3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practica_evaluable_ej3.ui.theme.*
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita el renderizado edge-to-edge para una UI más inmersiva.
        enableEdgeToEdge()
        setContent {
            Practica_Evaluable_Ej3Theme {
                CatalogApp()
            }
        }
    }
}

// Modelo de datos para representar un curso.
// Se utiliza una data class para obtener `equals`, `hashCode`, etc., de forma automática.
data class Course(
    val title: String,
    val level: String,
    val duration: Int,
    val tags: List<String>
)

// Dataset de ejemplo. En una aplicación real, estos datos provendrían de un repositorio.
val courses = listOf(
    Course("Introducción a Kotlin", "Básico", 12, listOf("Kotlin", "Android", "Fundamentos")),
    Course("Android con Jetpack Compose", "Intermedio", 20, listOf("Compose", "UI moderna", "Material3")),
    Course("Arquitectura MVVM en Android", "Avanzado", 18, listOf("Arquitectura", "MVVM", "Clean Code")),
    Course("Consumo de APIs REST", "Intermedio", 16, listOf("Networking", "Retrofit", "JSON")),
    Course("Pruebas unitarias en Kotlin", "Avanzado", 14, listOf("Testing", "JUnit", "Mockk"))
)

/**
 * Composable raíz que estructura la pantalla principal de la aplicación.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogApp() {
    val context = LocalContext.current
    // Scaffold proporciona la estructura básica de Material Design (TopAppBar, etc.).
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de cursos") },
                navigationIcon = {
                    IconButton(onClick = { /* No-op */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú")
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        Toast.makeText(context, "Búsqueda no implementada", Toast.LENGTH_SHORT).show() 
                    }) {
                        Icon(Icons.Filled.Search, contentDescription = "Buscar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        // LazyColumn es la elección óptima para listas, ya que solo renderiza los elementos visibles.
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = innerPadding, // Aplica el padding del Scaffold para evitar solapamientos.
            verticalArrangement = Arrangement.spacedBy(16.dp) // Espaciado uniforme entre tarjetas.
        ) {
            item { Spacer(Modifier.height(8.dp)) } // Margen superior para la lista.
            items(courses) { course ->
                CourseCard(course = course)
            }
            item { Spacer(Modifier.height(8.dp)) } // Margen inferior para la lista.
        }
    }
}

/**
 * Muestra una tarjeta individual para un curso, gestionando su propia interacción de resaltado.
 * @param course El objeto de datos del curso a mostrar.
 */
@Composable
fun CourseCard(course: Course) {
    val context = LocalContext.current
    // Estado para gestionar el resaltado visual de la tarjeta al pulsarla.
    var isHighlighted by remember { mutableStateOf(false) }

    // Anima el color de fondo de la tarjeta para un feedback visual suave.
    val cardColor by animateColorAsState(
        targetValue = if (isHighlighted) Color(0xFFF0F0F0) else MaterialTheme.colorScheme.surface,
        animationSpec = tween(durationMillis = 300),
        label = "CardColorAnimation"
    )

    // Efecto secundario para revertir el resaltado tras un breve retardo.
    // Se relanza si `isHighlighted` cambia a `true`.
    LaunchedEffect(isHighlighted) {
        if (isHighlighted) {
            delay(300L)
            isHighlighted = false
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isHighlighted = true
                Toast.makeText(context, "Curso: ${course.title}", Toast.LENGTH_SHORT).show()
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // El `weight` asegura que el título ocupe el espacio disponible y empuje la duración a la derecha.
                Text(
                    text = course.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f, fill = false)
                )
                Text(
                    text = "${course.duration} h",
                    color = TealDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LevelChip(level = course.level)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                course.tags.forEach { tag ->
                    TagChip(tag = tag)
                }
            }
        }
    }
}

/**
 * Un chip simple para mostrar el nivel del curso con un color distintivo.
 */
@Composable
fun LevelChip(level: String) {
    val chipColor = when (level) {
        "Básico" -> LevelBasic
        "Intermedio" -> LevelIntermediate
        else -> LevelAdvanced
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(chipColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text = level, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextSecondary)
    }
}

/**
 * Un chip simple para mostrar las etiquetas (tags) del curso.
 */
@Composable
fun TagChip(tag: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50)) // Forma de píldora para una estética moderna.
            .background(TagChipBackground)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text = tag, fontSize = 12.sp, color = TextSecondary)
    }
}

/**
 * Preview para visualizar el `CatalogApp` en Android Studio.
 */
@Preview(showBackground = true, widthDp = 375)
@Composable
fun CatalogAppPreview() {
    Practica_Evaluable_Ej3Theme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            CatalogApp()
        }
    }
}
