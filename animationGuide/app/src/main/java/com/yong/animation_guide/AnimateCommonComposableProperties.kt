package com.yong.animation_guide

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

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

/**
 * Composable에 정적으로 배경색을 설정할 때에는 `Modifier.background()`를 사용하는 것이 간단하고 적절하지만,
 * 배경색을 시간에 따라 애니메이션하려는 경우에는 `Modifier.background()` 방식으로 처리하면 재구성이 빈번하게 발생될 수 있기에 효율적이지 않을 수 있습니다.
 */
@Composable
fun SimpleAnimateColorAsState() {
    var animateBackgroundColor by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        animateBackgroundColor = true
    }

    val animatedColor by animateColorAsState(
        targetValue = if (animateBackgroundColor) Color.Green else Color.Blue,
        label = "color"
    )

    Column(
        modifier = Modifier.drawBehind { drawRect(animatedColor) }
    ) {
        // composable code
    }
}

/**
 * `Modifier.animteContentSize()`는 Composable의 크기 변경을 애니메이션하기 위해 설계되었습니다.
 * Composable의 크기가 변경되면 이전 크기와 새 크기 사이의 전환을 애니메이션화합니다.
 */
@Composable
fun simpleAnimateContentSize() {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(Color.Blue)
            .animateContentSize()
            .height(if (expanded) 400.dp else 200.dp)
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    )
}

/**
 * `animateIntOffsetAsState()`는 두 개의 PixelOffset을 `IntOffset(x: Int, y: Int)` 인자로 사용하여 두 값 사이를 애니메이션으로 표현하는데 도움을 줍니다.
 *
 * 그러나 이 방법은 부모 Composable이 인식하는 위치에 영향을 주지 않으며 형제 Composable의 위치에도 영향을 주지 않습니다.
 * 이에 따라 형제 Composable이 서로 위에 그려질 수도 있습니다.
 */
@Composable
fun SimpleAnimateIntOffsetAsState() {
    var moved by remember { mutableStateOf(false) }
    val pxToMove = with(LocalDensity.current) { 100.dp.toPx().roundToInt() }

    val offset by animateIntOffsetAsState(
        targetValue = if (moved) IntOffset(x = pxToMove, y = pxToMove) else IntOffset.Zero,
        label = "offset"
    )

    Box(
        modifier = Modifier
            .offset { offset }
            .background(Color.Blue)
            .size(100.dp)
            .clickable { moved = !moved }
    )
}

/**
 * 위치를 형제 Composable과 관련하여 더 정확하게 제어하려면 `Modifier.layout`을 사용할 수 있습니다.
 *
 * `Modifier.layout`는 Composable의 측정 및 위치 지정을 직접 제어할 수 있게 해줍니다.
 */
@Preview
@Composable
fun SimpleModifierLayout() {

    var toggled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .clickable { toggled = toggled.not() }
    ) {
        val offset by animateIntOffsetAsState(
            targetValue = if (toggled) IntOffset(150, 150) else IntOffset.Zero,
            label = "offset"
        )

        DefaultBox()

        Box(
            modifier = Modifier
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)

                    layout(
                        width = placeable.width + offset.x,
                        height = placeable.height + offset.y
                    ) {
                        placeable.placeRelative(offset)
                    }
                }
                .size(100.dp)
                .background(Color.Green)
        )

        DefaultBox()
    }
}