package com.codelab.theming.ui.start.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Material Theme은 color, typography, shape 속성으로 구성
// Theme을 바꾸면 자동으로 컴포저블에 적용될 것이다.

// Color
// app에서 사용될 color palette를 만드는 거 추천
private val LightColors = lightColors(
    // Material Color Palette를 구성하기 위한 default 값을 제공하는 함수
    primary = Red700,
    primaryVariant = Red900,
    onPrimary = Color.White,
    secondary = Red700,
    secondaryVariant = Red900,
    onSecondary = Color.White,
    error = Red800,
)

private val DarkColors = darkColors(
    primary = Red300,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = Red300,
    onSecondary = Color.Black,
    error = Red200,
)

// Typography
// custom font,

// Shape
// MT에서는 small, medium, large 3가지를 정의함

// styling을 중앙화하기 위해서 MaterialTheme을 앱 코드에 그대로 사용하는 게 아니라 wrapper 함수를 만들어 관리하자.
@Composable
fun JetnewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = JetnewsTypography,
        shapes = JetnewsShapes,
        content = content,
    )
}
