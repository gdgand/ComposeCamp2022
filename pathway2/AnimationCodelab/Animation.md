# Jetpack Compose의 요소 애니메이션
[animation codelab](https://developer.android.com/codelabs/jetpack-compose-animation?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fjetpack-compose-for-android-developers-2%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-animation#0)
### 간단한 값 변경 애니메이션
- `animate*AsState` API 사용
- 탭의 색상 변경
  - `animateColorAsState` 사용
  - 리턴 값은 `State<T>` 객체이므로 `by`를 사용해 일반 변수처럼 처리 가능
  ```kotlin
  val backgroundColor by animateColorAsState(if (tabPage == TabPage.Home) Purple100 else Green300)
  ```
<br>

### 가시성 애니메이션
- 앱의 콘텐츠를 스크롤할 때 스크롤 방향에 따라 플로팅 작업 버튼이 확장되거나 축소될때 사용
- `AnimatedVisibility` 사용
```kotlin
AnimatedVisibility (boolean 값){
    ...
}
```
  - boolean 값이 변경될 때마다 애니메이션 실행
- `AnimatedVisibility` 맞춤 설정의 경우
  - `enter`, `exit` 매개변수 
    - `AnimatedVisibility` 컴포저블에 추가
    - `enter`은 `EnterTransition`의 인스턴스로 여기선 `slideInVertically` 함수를 사용해 나가기 전환을 위한 `EnterTransition`, `slideOutVertically` 만들기 가능
  ```kotlin
  AnimatedVisibility(
      visible = shown,
      enter = slideInVertically(),
      exit = slideOutVertically()
  ) {
    ...
  }
  ```
- `initialOffsetY` 
  - 매개변수 설정을 통해 항목의 전체 높이 사용하도록 기본 동작 조절
  - 초기 위치를 반환하는 람다여야하고 인수 하나(요소 높이)를 수신함
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
        ...
    }
  ```
<br>

### 콘텐츠 크기 변경 애니메이션
- `Column` 컴포저블은 콘텐츠가 변경되면 크기 변경
- `animateContentSize` 수정자를 추가해 크기 변경에 애니메이션 적용 가능
  - `animationSpec` 으로 맞춤 설정 가능
```kotlin
Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .animateContentSize()
) {
    ...
}
```
<br>

### 여러 값 애니메이션
- `Transition` API
  - `Transition`의 모든 애니메이션이 완료된 시점을 추적할 수 있음
  - 여러 상태 간에 전환할 때 다양한 `transitionSpec`을 정의할 수 있음
  - `updateTransition` 함수를 사용해서 `Transition` 만들 수 있음
  - `targetState` 매개변수로 현재 선택된 탭의 색인을 전달
  - `Transition`의 `animate*` 확장 함수를 사용해 애니메이션 값 선언 가능
  ```kotlin
  val indicatorLeft by transition.animateDp(label = "Indicator left") { page ->
   tabPositions[page.ordinal].left }
  ```
  ```kotlin
  val indicatorRight by transition.animateDp(
        transitionSpec = {
            if (TabPage.Home isTransitioningTo TabPage.Work) {
                spring(stiffness = Spring.StiffnessMedium)
            } else {
                spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = "Indicator right"
    ) { page ->
        tabPositions[page.ordinal].right
    }
  ```
<br>

### 애니메이션 반복
- `InfiniteTransition` 사용
  - 값에 무기한으로 애니메이션 적용
  - cf) `Transition`은 상태 변경에 따라 애니메이션을 값에 적용함
  - `rememberInfiniteTransition` 함수 사용해 만듦
  - `animate*` 확장 함수 중 하나 사용해 선언 애니메이션 값 변경 선언 가능
  - 여기선 alpha 값에 애니메이션 적용하기 때문에 `animatedFloat` 사용
  - `AnimationSpec` 대신 `InfiniteRepeatableSpec`만 사용
  - `repeatMode`를 `RepeatMode.Reverse`로 설정하면 애니메이션이 `initialValue`에서 `targetValue`로 진행된 후 `targetValue`에서 `initialValue`로 진행됨
  - `keyFrames` 애니메이션은 다른 밀리초 단위에서 진행중인 값을 변경할 수 있는 또 다른 유형의 `animationSpec`임
    ```kotlin
    animationSpec = infiniteRepeatable(
        animation = keyframes{
            durationMillis = 1000
            0.7f at 500 // 500ms 내에 0에서 0.7까지 빠르게 진행되고 남은 500ms는 0.7에서 1.0까지 천천히 속도를 줄이며 진행됨
        },
        repeatMode = RepeatMode.Reverse
    )
    - 두 개 이상의 키프레임을 원하는 경우 여러개 정의도 가능
    ```kotlin
    animation = keyframes {
        durationMillis = 1000
        0.7f at 500
        0.9f at 800
    }
    ```
<br>

### 동작 애니메이션
- 터치 입력 기반 애니메이션
  - `swipeToDismiss` 수정자
  - `pointerInput` 수정자를 사용하면 수신되는 포인터 터치 이벤트에 대한 하위 수준 액세스 권한을 얻고 동일한 포인터를 사용해서 사용자가 드래그 하는 속도도 추적 가능
- 고려해야 할 사항
  - 진행중인 애니메이션이 터치 이벤트로 중단될 수 있음
    - `Animatable`에서 `stop` 호출해 중단
    - 애니메이션이 실행되지 않는 경우 호출은 무시됨
  - 애니메이션 값이 유일한 정보 소스가 아닐 수 있음 즉, 애니메이션 값을 터치 이벤트에서 발생하는 값과 동기화해야할 수 있음
    - `Animatable`에서 `snapTo` 사용해 동기화
    - `snapTo`는 다른 `launch` 블록 내에서 호출해야 함
- `Animatable` 인스턴스 생성
- `VelocityTracker`는 사용자가 왼쪽에서 오른쪽으로 이동하는 속도를 계산하는 데 사용
- `awaitPointerEventScope`는 사용자 입력 이벤트를 기다렸다가 이에 응답할 수 있는 정지 함수
- `Animatable`에 상한값과 하한값을 설정해 애니메이션 경계 설정
  ```kotlin
  offsetX.updateBounds(
      lowerBound = -size.width.toFloat(),
      upperBound = size.width.toFloat()
  )
  ```
