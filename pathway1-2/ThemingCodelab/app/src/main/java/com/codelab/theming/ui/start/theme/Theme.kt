package com.codelab.theming.ui.start.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.codelab.theming.data.PostRepo
import com.codelab.theming.ui.start.FeaturedPost

private val LightColors = lightColors(
    primary = RED700,
    primaryVariant = RED900,
    onPrimary = Color.White,
    secondary = RED700,
    secondaryVariant = RED900,
    onSecondary = Color.White,
    error = RED800
)

private val DarkColors = darkColors(
    primary = RED300,
    primaryVariant = RED700,
    onPrimary = Color.Black,
    secondary = RED300,
    onSecondary = Color.Black,
    error = RED200
)

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

@Preview("Featured Post", showBackground = true)
@Composable
private fun FeaturedPostPreview() {
    val post = remember {
        PostRepo.getFeaturedPost()
    }
    JetnewsTheme(darkTheme = true) {
        FeaturedPost(post = post)
    }
}
