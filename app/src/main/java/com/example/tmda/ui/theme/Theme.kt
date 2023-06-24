package com.example.tmda.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40


)

@Composable
fun TMDATheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme.copy(
            primary= Color.White,
            background = Color.Transparent,
            surface = Color.Transparent,
            onBackground = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onSurface = Color.White,
            onTertiary = Color.White)
        else -> LightColorScheme.copy(
            primary= Color.White,
            background = Color.Transparent,
            surface = Color.Transparent,
            onBackground = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onSurface = Color.White,
            onTertiary = Color.White)
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = {
            ProvideTextStyle(value = TextStyle(color = Color.White)) {
                content()
            }}
    )
}