package com.codelab.theming.ui.start.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


// Colors 객체를 가져와 Material의 이름이 지정된 색상에 특정 색상을 할당
private val LightColors = lightColors(
    primary = Red700,
    primaryVariant = Red900,
    onPrimary = Color.White,
    secondary = Red700,
    secondaryVariant = Red900,
    onSecondary = Color.White,
    error = Red800
)

/*
테마 맞춤 설정을 한곳에서 지정하고 여러 화면 or @Preview 등 여러 위치에서 쉽게 재사용할 수 있음
필요하다면 여러 테마 컴포저블 생성이 가능

- MaterialTheme을 감싸는 JetnewsTheme 함수 생성
- 해당 함수를 고차함수로 표현하여 다른 곳에서 호출하여 원하는 MaterialTheme를 적용시킬 수 있게 한다.

- lightColors 함수를 사용해 Colors를 빌드 해준다. 미리 임의로 색상의 기본값을 지정해놓았기 때문에 Material 팔레트를
  구성하는 모든 색상을 지정할 필요가 없다.
 */
@Composable
fun JetnewsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        // 정의한 색상을 테마에 적용시켜 준다.
        colors = LightColors,
        typography = JetnewsTypography,
        content = content
    )
}