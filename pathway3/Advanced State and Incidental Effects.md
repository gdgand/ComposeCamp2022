# Jetpack Compose의 고급 상태 및 부작용
### ViewModel에서 Flow 사용하기
```kotlin
val suggestedDestinations: StateFlow<List<ExploreModel>>
```
- `StateFlow.collectAsState()` 함수를 사용해 스트림에 새 항목 보낼 때 마다 UI 업데이트
```kotlin
val suggestedDestinations by viewModel.suggestedDestinations.collectAsState()
```
<br>

### LaunchedEffect 및 rememberUpdatedState
- 이상적으로는 화면을 표시하고 모든 데이터가 로드된 후 호출자에게 `onTimeout` 콜백을 사용해 랜딩 화면을 닫을 수 있음을 알림
- 일반적으로 앱을 시작할 때 백그라운드에서 항목을 로드하기 위해 코루틴 사용<br>
  
`Compose 부작용`
```
compose 함수 범위 밖에서 발생하는 앱 상태에 관한 변경 사항
예) 사용자가 버튼을 탭할 때 새 화면이 열리거나 앱이 인터넷에 연결되어 있지 않을 때 메시지 표시
```
<br>

- `LauncedEffect` API
  - 컴포저블 내에서 안전하게 정지 함수를 호출하려면 Compose에서 코루틴 범위의 부작용을 트리거하는 `LaunchedEffect` API 사용
  - 새 컴포지션을 시작하면 매개변수로 전달된 코드블록으로 코루틴이 실행됨
  - 컴포지션을 종료하면 코루틴이 취소됨
  ```kotlin
  LaunchedEffect(true) {
    delay(SplashWaitTime)
    currentOnTimeout()
  }
  ```
  - `rememberUpdatedState` API
    - 도중에 상태가 변경되어 코드 실행이 완전하게 이루어지지 않는 경우를 보장할 수 있음
    ```kotlin
    val currentOnTimeout by rememberUpdatedState(onTimeout)
    ```
<br>

### rememberCoroutineScope
- `openDrawer`.콜백을 사용해 탐색창 열기 구현
- `scaffoldState.drawerState.open()` 은 `open`함수가 정지 함수이기 때문에 완료되고 실행을 재개할 때 까지 호출되는 코루틴의 실행을 정지해 오류가 발생함
- 따라서 `scaffoldState.drawerState.open()`은 코루틴 내에 호출되어야 함
  - `rememberCoroutineScope` API의 `CoroutineScope` 사용
  - 컴포지션을 종료하면 범위가 자동으로 취소됨
  ```kotlin
  val scope = rememberCoroutineScope()
    CraneHomeContent(
      modifier = modifier,
      onExploreItemClicked = onExploreItemClicked,
      openDrawer = {
        scope.launch {
          scaffoldState.drawerState.open()
        }
      }
    )
  ```
- `LaunchedEffect` vs `rememberCoroutineScope`
  - `LaunchedEffect`는 컴포저블에 대한 호출이 컴포지션으로 향할 때 부작용이 실행되도록 함
  - `rememberCoroutineScope` 및 `scope.launch`를 사용하는 경우 코루틴은 호출이 컴포지션으로 향하는지 여부와 무관하게 Compose에서 해당 컴포저블을 호출할 때마다 실행됨
    - 리소스를 낭비하게 됨
    - 제어된 환경에서 이 부작용을 실행하지 않게 됨
<br>

### 상태 홀더 만들기
- 일반 클래스로 생성
  ```kotlin
  class EditableUserInputState(private val hint: String, initialText: String) {
    var text by mutableStateOf(initialText)
    val isHint: Boolean
        get() = text == hint
  }
  ```
- 상태 홀더 기억하기
  - 상태 홀더가 항상 기억되어야 컴포지션에서 유지되고 매번 새로 만들 필요가 없어짐
    - 프로젝트에서 직접 만든 `EditableUserInputState` 클래스는 `rememberSaveable`로 저장하기 힘듦
    - `Saver` 사용해서 `rememberSaveable`에 이 클래스의 인스턴스를 저장 및 복원하는 방법 알리기
  - 상용구를 삭제하고 발생할 수 있는 실수를 피하도록 이 작업을 수행하는 메서드를 동일한 파일에 만드는 것이 좋음

- 맞춤 `Saver` 만들기
  - `Saver`은 객체를 `Saveable` 상태인 것으로 변환하는 방법을 설명함
  1. `save`는 원래 값을 저장 가능한 값으로 변환함
  2. `restore`은 복원된 값을 원본 클래스의 인스턴스로 변환함
  - 여기선 맞춤 구현 대신 `listSaver`, `mapSaver` 사용
  - 정적으로 액세스해야 하기 때문에 `companion object` 사용
  ```kotlin
  companion object {
    val Saver: Saver<EditableUserInputState, *> = listSaver(
      save = { listOf(it.hint, it.text) },
      restore = {
        EditableUserInputState(
          hint = it[0],
          initialText = it[1],
        )
      }
    )
  }
  ```
  ```kotlin
  fun rememberEditableUserInputState(hint: String): EditableUserInputState =
    rememberSaveable(hint, saver = EditableUserInputState.Saver) {
        EditableUserInputState(hint, hint)
    }
  ```
<br>

### DisposableEffect
올바른 수명 주기를 따르지 않는 뷰가 존재
- 앱이 언제 백그라운드로 이동하는지, 뷰가 언제 일시중지되어야 하는지 등을 알 수 없음
- `LifecycleEventObserver` 관찰자 만들어서 현재 활동의 수명주기에 추가해야함
```kotlin
private fun getMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
            Lifecycle.Event.ON_START -> mapView.onStart()
            Lifecycle.Event.ON_RESUME -> mapView.onResume()
            Lifecycle.Event.ON_PAUSE -> mapView.onPause()
            Lifecycle.Event.ON_STOP -> mapView.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
            else -> throw IllegalStateException()
        }
    }
```
- `DisposableEffect` API는 키가 변경되거나 컴포저블이 컴포지션을 종료하면 정리되어야 하는 부작용을 위한 것
<br>

### produceState
- `uiState` 매핑 로직을 Compose 환경으로 옮기려면 `produceState` API 사용
  - Compose가 아닌 상태를 Compose 상태로 변환 가능
  - `value` 속성을 사용하여 반환된 `State`에 값을 푸시할 수 있는 컴포지션으로 범위가 지정된 코루틴을 실행함
  - `LaunchedEffect`와 마찬가지로 `produceState` 역시 키를 가져와 계산을 취소하고 다시 시작함
<br>

### derivedStateOf
- `derivedStateOf` API는 다른 State에서 파생된 Compose State를 원하는 경우에 사용
  - 이 함수를 사용하면 계산에서 사용되는 상태 중 하나가 변경될 때만 계산이 실행됨
