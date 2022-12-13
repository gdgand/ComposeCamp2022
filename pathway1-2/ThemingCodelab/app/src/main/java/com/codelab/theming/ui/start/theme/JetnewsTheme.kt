package com.codelab.theming.ui.start.theme

import DarkColors
import LightColors
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun JetnewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = JetnewsTypography,
        shapes = JetNewsShapes,
        content = content
    )
}