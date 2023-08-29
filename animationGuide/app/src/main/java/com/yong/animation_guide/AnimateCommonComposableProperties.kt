package com.yong.animation_guide

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

/**
 * - `AnimatedVisibility` 사용하여 Composable을 숨기거나 표시할 수 있습니다.
 * - `AnimatedVisibility` 내부의 자식은 자신의 enter or exit 전환에 `Modifier.animateEnterExit()`를 사용할 수 있습니다.
 * - `AnimatedVisibility`의 `enter`와 `exit` 파라미터를 사용하면 Composable이 나타날 때와 사라질 때 어떻게 동작할 지 설정할 수 있습니다.
 */
@Composable
fun SimpleAnimatedVisiblity() {
    var visible by remember { mutableStateOf(true) }

    // 애니메이션이 끝나면 AnimatedVisibility 항목을 Composition에서 최종적으로 제거합니다.
    AnimatedVisibility(visible) {
        // composable code
    }
}

/**
 * `animateFloatAsState`를 사용하여 Composable을 알파 값을 통해 애니메이션화 할 수 있습니다.
 * `visible` 상태에 따라 알파 값이 1(완전히 보이는) 또는 0(완전히 숨겨진)으로 변경됩니다.
 *
 * 그러나 이 방식에는 주의할 점이 있습니다. 알파를 0으로 설정하더라도 Composable이 Composition에 그대로 남아 있기에 화면의 공간을 계속 차지합니다.
 */
@Composable
fun SimpleAnimateFloatAsState() {
    var visible by remember { mutableStateOf(true) }

    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        label = "alpha"
    )

    Box(
        modifier = Modifier
            .size(200.dp)
            .graphicsLayer { alpha = animatedAlpha }
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Green)
    ) {
        // ...
    }
}