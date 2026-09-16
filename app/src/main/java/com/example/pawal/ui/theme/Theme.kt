package com.example.pawal.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = IndigoPrimary,
    onPrimary = PureWhite,
    primaryContainer = IndigoDark,
    onPrimaryContainer = PureWhite,
    secondary = AmberSecondary,
    onSecondary = DeepMidnight,
    background = DeepMidnight,
    onBackground = SoftWhite,
    surface = SurfaceDark,
    onSurface = SoftWhite,
    error = ErrorRed,
    outline = BorderColorDark
)

private val LightColorScheme = lightColorScheme(
    primary = IndigoPrimary,
    onPrimary = PureWhite,
    primaryContainer = SurfaceGrey,
    onPrimaryContainer = IndigoPrimary,
    secondary = AmberSecondary,
    onSecondary = DeepMidnight,
    background = SoftWhite,
    onBackground = DeepMidnight,
    surface = PureWhite,
    onSurface = DeepMidnight,
    error = ErrorRed,
    outline = BorderColor
)

@Composable
fun PawalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
