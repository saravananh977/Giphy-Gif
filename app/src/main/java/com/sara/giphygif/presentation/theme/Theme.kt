package com.sara.giphygif.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.White,
    secondary = Color.White,
    background = DarkModeBg,
    surface = darkModeSurface,
    onPrimary = Color.White,
    onSurface = Color.White,
    error = darkErrorColor
)

private val LightColorPalette = lightColors(
    primary = Color.Black,
    secondary = Color.Black,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSurface = Color.Black,
    error = darkErrorColor
)

@Composable
fun GiphyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}