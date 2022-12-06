package com.young.metro.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val metroDarkColorScheme = darkColorScheme(
    primary = metroDarkPrimary,
    onPrimary = metroDarkOnPrimary,
    primaryContainer = metroDarkPrimaryContainer,
    onPrimaryContainer = metroDarkOnPrimaryContainer,
    inversePrimary = metroDarkInversePrimary,
    secondary = metroDarkSecondary,
    onSecondary = metroDarkOnSecondary,
    secondaryContainer = metroDarkSecondaryContainer,
    onSecondaryContainer = metroDarkOnSecondaryContainer,
    tertiary = metroDarkTertiary,
    onTertiary = metroDarkOnTertiary,
    tertiaryContainer = metroDarkTertiaryContainer,
    onTertiaryContainer = metroDarkOnTertiaryContainer,
    error = metroDarkError,
    onError = metroDarkOnError,
    errorContainer = metroDarkErrorContainer,
    onErrorContainer = metroDarkOnErrorContainer,
    background = metroDarkBackground,
    onBackground = metroDarkOnBackground,
    surface = metroDarkSurface,
    onSurface = metroDarkOnSurface,
    inverseSurface = metroDarkInverseSurface,
    inverseOnSurface = metroDarkInverseOnSurface,
    surfaceVariant = metroDarkSurfaceVariant,
    onSurfaceVariant = metroDarkOnSurfaceVariant,
    outline = metroDarkOutline
)

private val metroLightColorScheme = lightColorScheme(
    primary = metroLightPrimary,
    onPrimary = metroLightOnPrimary,
    primaryContainer = metroLightPrimaryContainer,
    onPrimaryContainer = metroLightOnPrimaryContainer,
    inversePrimary = metroLightInversePrimary,
    secondary = metroLightSecondary,
    onSecondary = metroLightOnSecondary,
    secondaryContainer = metroLightSecondaryContainer,
    onSecondaryContainer = metroLightOnSecondaryContainer,
    tertiary = metroLightTertiary,
    onTertiary = metroLightOnTertiary,
    tertiaryContainer = metroLightTertiaryContainer,
    onTertiaryContainer = metroLightOnTertiaryContainer,
    error = metroLightError,
    onError = metroLightOnError,
    errorContainer = metroLightErrorContainer,
    onErrorContainer = metroLightOnErrorContainer,
    background = metroLightBackground,
    onBackground = metroLightOnBackground,
    surface = metroLightSurface,
    onSurface = metroLightOnSurface,
    inverseSurface = metroLightInverseSurface,
    inverseOnSurface = metroLightInverseOnSurface,
    surfaceVariant = metroLightSurfaceVariant,
    onSurfaceVariant = metroLightOnSurfaceVariant,
    outline = metroLightOutline
)

@Composable
fun MetroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val metroColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> metroDarkColorScheme
        else -> metroLightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = metroColorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = metroColorScheme,
        typography = metroTypography,
        shapes = shapes,
        content = content
    )
}