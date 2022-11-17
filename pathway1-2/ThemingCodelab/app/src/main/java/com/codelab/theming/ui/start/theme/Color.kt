package com.codelab.theming.ui.start.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color


val Red200 = Color(0xfff297a2)
val Red300 = Color(0xffea6d7e)
val Red700 = Color(0xffdd0d3c)
val Red800 = Color(0xffd00036)
val Red900 = Color(0xffc20029)

val LightColors = lightColors(
    primary = Red700,
    primaryVariant = Red900,
    onPrimary = Color.White,
    secondary = Red700,
    secondaryVariant = Red900,
    onSecondary = Color.White,
    error = Red800
)

val DarkColors = darkColors(
    primary = Red300,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = Red300,
    onSecondary = Color.Black,
    error = Red200
)
