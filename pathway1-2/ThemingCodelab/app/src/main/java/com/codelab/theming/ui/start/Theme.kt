package com.codelab.theming.ui.start

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


val LightColors = lightColors(
        primary = Red700,
        primaryVariant = Red900,
        onPrimary = Color.White,
        secondary = Red700,
        secondaryVariant = Red900,
        onSecondary = Color.White,
        error = Red800
    )
val JetnewsShapes = Shapes(
    small = CutCornerShape(topStart = 8.dp),
    medium = CutCornerShape(topStart = 24.dp),
    large = RoundedCornerShape(8.dp)
)
val DarkColors = darkColors(
    primary = Red300,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = Red300,
    onSecondary = Color.Black,
    error = Red200
)