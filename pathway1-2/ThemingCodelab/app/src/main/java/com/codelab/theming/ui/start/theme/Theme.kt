package com.codelab.theming.ui.start

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.codelab.theming.theme.DarkColors
import com.codelab.theming.theme.LightColors
import com.codelab.theming.ui.start.theme.JetnewsShapes
import com.codelab.theming.ui.start.theme.JetnewsTypography

@Composable
fun JetnewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = JetnewsTypography,
        shapes = JetnewsShapes,
        content = content
    )
}