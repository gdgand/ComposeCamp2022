package com.codelab.theming.ui.start.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

private val Red700 = Color(0xffdd0d3c)
private val Red800 = Color(0xffd00036)
private val Red900 = Color(0xffc20029)
private val Red200 = Color(0xfff297a2)
private val Red300 = Color(0xffea6d7e)

val JetnewsLightColors = lightColors(
    primary = Red700,
    primaryVariant = Red900,
    onPrimary = Color.White,
    secondary = Red700,
    secondaryVariant = Red900,
    onSecondary = Color.White,
    error = Red800
)

val JetnewsDarkColors = darkColors(
    primary = Red300,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = Red300,
    onSecondary = Color.Black,
    error = Red200
)