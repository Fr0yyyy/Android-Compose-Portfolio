package com.example.practica_evaluable_ej3.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Esquema de colores personalizado para el tema claro
private val AppColorScheme = lightColorScheme(
    primary = TealPrimary,
    surface = CardBackground,
    background = BackgroundGray,
    onPrimary = TextPrimary,
    onSurface = TextSecondary,
    onBackground = TextSecondary
)

@Composable
fun Practica_Evaluable_Ej3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Usamos nuestro esquema de colores personalizado y desactivamos el color dinámico
    val colorScheme = AppColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // El color de la barra de estado será el primario de nuestro tema (TealPrimary)
            window.statusBarColor = colorScheme.primary.toArgb()
            // Aseguramos que los iconos de la barra de estado (hora, batería) sean visibles
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
