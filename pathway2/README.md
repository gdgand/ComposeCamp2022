# AnimationCodelab

## State Animation
- Compose에서 가장 간단한 Animation API 중 하나는 ``animate*AsState`` 
- State 변경에 애니메이션을 적용할 때 사용
```kotlin
val backgroundColor by animateColorAsState(if(tabPage == TabPage.Home) Purple100 else Green300)
```
- ``animate*AsState`` 반환된 값은 ``State< T >`` 므로 by를 사용하여 델리게이트 처리할 수 있음.

<br>

## Visibility Animation
- if문을 통해 UI를 표시하거나 숨길 수 있는데 이 때 애니메이션까지 적용하기 위해서는 ``AnimatedVisibility`` Composable 사용
- ``AnimatedVisibility``는 지정된 Boolean 값이 변경될 때마다 애니메이션 실행, 기본적으로 페이드 인으로 UI를 보여주고 페이드 아웃 방식으로 숨김.
- 애니메이션 맞춤 설정이 필요할 경우 ``enter``, ``exit`` 매개변수를 ``AnimatedVisibility`` 컴포저블에 추가

```kotlin
AnimatedVisibility(
    visible = shown,
    enter = slideInVertically(
        initialOffsetY = { fullHeight -> -fullHeight }
    ),
    exit = slideOutVertically(
        targetOffsetY = { fullHeight -> -fullHeight }
    )
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.secondary,
        elevation = 4.dp
    ) {
        Text(
            text = stringResource(R.string.edit_message),
            modifier = Modifier.padding(16.dp)
        )
    }
}
```

<br>

## Content Size Animation
- ``animateContentSize`` Modifier를 추가하여 크기 변경에 애니메이션을 적용 가능
- ``animationSpec``을 통해서 애니메이션 맞춤 설정도 가능
```kotlin
Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .animateContentSize()
) {
    // ... the title and the body
}
```

<br>

## Multiple Value Animation
- 여러 애니메이션을 추적할 때는 이전에 사용했던 Animation API로는 불가능, ``Transition`` API를 사용
- ``Transition`` API는 모든 애니메이션이 완료된 시점 추적, 여러 상태 간에 전활할 때 다양한 ``transitionSpec`` 정의
```kotlin
val transition = updateTransition(tabPage, label = "Tab indicator")
val indicatorLeft by transition.animateDp(label = "Indicator left") { page ->
   tabPositions[page.ordinal].left
}
val indicatorRight by transition.animateDp(label = "Indicator right") { page ->
   tabPositions[page.ordinal].right
}
val color by transition.animateColor(label = "Border color") { page ->
   if (page == TabPage.Home) Purple700 else Green800
}
```
- ``updateTransition`` 컴포저블 함수로 Transition을 생성 후 각 애니메이션 값을 ``animate`` 확장 함수를 사용하여 선언 가능 ex) animateDp , animateColor

<br>

## Repeat Animation
- 반복적인 애니메이션을 처리할 경우에는 ``InfiniteTransition`` 사용
- ``Transition``은 상태 변경에 따라 값에 애니메이션 적용, ``InfiniteTransition``은 무한정으로 애니메이션 적용
```kotlin
val infiniteTransition = rememberInfiniteTransition()
val alpha by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
        animation = keyframes {
            durationMillis = 1000
            0.7f at 500
        },
        repeatMode = RepeatMode.Reverse
    )
)
```
- InfiniteTransition은 생성하기 위해 rememberInfiniteTransition 함수 사용, 이후 값 변경은 ``animate*`` 확장 함수 사용
- 이 애니메이션의 ``AnimationSpec``은 오직 ``InfiniteReeatableSpec``만 가능

<br>

## Gesture Animation
- ``pointerInput``: Mofier를 사용하면 수신되는 포인터 터치 이벤트 액세스 및 드래그 속도 추적
- ``VelocityTracker``: 사용자가 왼쪽에서 오른쪽으로 이동하는 속도 계산하는데 사용
- ``awaitPointerEventScope``: 사용자 입력 이벤트를 기다렸다가 응답하는 suspend function

