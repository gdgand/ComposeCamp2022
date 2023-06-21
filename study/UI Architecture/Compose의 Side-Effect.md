# Compose: Side-Effect

---

`Side-Effect`는 Composable 함수의 **범위 밖**에서 일어나는 **앱의 상태 변화를 의미**합니다.

Composable들이 가지는 특징들로 인해 Composable은 사이드 이펙트를 가지지 않아야 합니다.  
Composable들이 가지는 특징들에는 아래와 같이 있을 수 있습니다. 
- 예측할 수 없는 재구성
- 재구성의 순서 변경
- 중지되는 재구성

그러나 가끔 `Side-Effect`가 필요한 경우가 있습니다. 예를 들어, 다음과 같습니다. 
- 특정 상태에 따라 스낵바 노출
- 다른 화면으로 이동하는 등의 일회성 이벤트 시작

위 동작들은 Composable의 생명주기를 이해하고, 재구성이 언제 이루어지는지 알고 있는 `Side-Effect API`를 통해 호출되어야 합니다.

---

## State와 Effect 사용 사례

Composable은 UI를 그리는데 집중해야 합니다. 따라서 UI를 그리는 작업 외에는 다른 작업(`Side-Effect`)을 수행하지 않아야 합니다.  
예를 들어, 네트워크 호출이나 데이터베이스 액세스와 같은 작업은 Composable 함수 외부에서 수행되어야 합니다.

만약 앱의 상태를 변경해야 할 경우, 사이드 이펙트가 예측 가능한 방식으로 실행되도록 `Effect API`를 사용해야 합니다.

### Effect API
`Effect API`는 UI를 출력하지 않지만, Composable의 생명주기를 고려하여 네트워크 호출이나 DB 액세스와 같은 Side-Effect를 실행하는 Composable 함수입니다.

`Effect API`는 쉽게 남용할 수 있으므로, 수행하는 작업이 UI와 관련되어 있고, 단방향 데이터 흐름을 깨뜨리지 않도록 유의해서 사용해야 합니다.

반응형 UI는 본질적으로 비동기적이며, Compose는 이를 코루틴을 통해 해결하고 있습니다.

### LaunchedEffect: Composable 범위에서 suspend 함수 실행

Composable 내부에서 `suspend` 함수를 안전하게 호출하기 위해 `LaunchedEffect`를 사용합니다.   

`LaunchedEffect`가 Composition에 진입하면, 매개변수로 전달된 코드 블럭과 함께 코루틴을 시작합니다.   
그러나 `LaunchedEffect`가 Composition에서 벗어나면 해당 코루틴은 취소가 됩니다. 

`LaunchedEffect`가 다른 `Key`와 함께 `Re-Composition`되면, 
기존의 코루틴은 취소되고, 새로운 `suspend` 함수가 새로운 코루틴에서 실행됩니다.

예를 들어, `Scaffold` 내에서 `Snackbar`를 표시하는 것은 `SnackbarHostState.showSnackbar` 함수를 사용하며, 이는 `suspend` 함수입니다.

```kotlin
@Composable
fun MyScreen(
    state: UiState<List<Movie>>,
    snackbarHostState: SnackbarHostState
) {
    // UI 상태에 오류가 있으면 Snackbar 표시
    if (state.hasError) {

        // `scaffoldState.snackbarHostState`가 변경되면 `LaunchedEffect`는 취소되고 재실행
        LaunchedEffect(snackbarHostState) {
            // 코루틴을 사용하여 Snackbar 표시, 코루틴이 취소되면 Snackbar는 자동으로 해제됩니다. 
            // 이 코루틴은 `state.hasError`가 false일 때 취소되고, `state.hasError`가 true일 때 시작합니다
            // 또는 `scaffoldState.snackbarHostState`가 변경될 때 시작됩니다.
            snackbarHostState.showSnackbar(
                message = "Error message",
                actionLabel = "Retry message"
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { contentPadding ->
        // ...
    }
}
```
 
`LaunchedEffect`의 호출 위치가 `if`문 내부에 있기 때문에, `state.hasError`가 `true`일 때만 `LaunchedEffect`가 실행되며 코루틴이 실행됩니다.   
그러나, `if` 문이 `false`일 경우, 만약 Composition 내에 `LaunchedEffect`가 있었다면 제거되고 코루틴은 취소됩니다.


### rememberCoroutineScope: Composable 외부에서 Coroutine을 시작하려면 Composition에서 인식하는 Scope를 사용

`LaunchedEffect`는 Composable 함수이므로, 다른 Composable 함수 내에서만 사용할 수 있습니다.

`rememberCoroutineScope`를 이용하면 composable 외부에서 코루틴을 시작하면서도 그 코루틴이 현재 composable의 생명주기에 따라 자동으로 취소되게 할 수 있습니다. 
이는 Composable이 Composition에 제거되면 자동으로 해당 코루틴을 정리하므로 메모리 누수나 기타 이슈를 방지하는데 도움이 됩니다.

예를 들어, 사용자가 특정 UI 요소를 클릭하면 애니메이션을 시작하고 다른 요소를 클릭하면 애니메이션을 중단하려면, 
각 클릭 이벤트가 발생할 때마다 코루틴을 시작하고 중지하는 작업을 수행해야 합니다. 이런 경우에 `rememberCoroutineScope`를 사용하면 편리합니다.

`rememberCoroutineScope`를 이용해 생성된 `CoroutineScope`는 Composable의 **생명주기에 바인딩**되므로, 
Composable이 Composition에서 제거되면 연결된 모든 코루틴도 자동으로 취소됩니다. 이를 통해 사용자 이벤트에 따라 여러 코루틴의 생명주기를 수동으로 제어할 수 있게 됩니다.

앞선 예제를 이어, 사용자가 `Button`을 탭하면 Snackbar가 표시되도록 이 코드를 사용할 수 있습니다.

```kotlin
@Composable
fun MoviesScreen(snackbarHostState: SnackbarHostState) {

    // MoviesScreen의 생명주기에 바인딩된 CoroutineScope를 생성
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            Button(
                onClick = {
                    // 이벤트 핸들러에서 새 코루틴을 생성하여 스낵바를 표시
                    scope.launch {
                        snackbarHostState.showSnackbar("Something happened!")
                    }
                }
            ) {
                Text("Press me")
            }
        }
    }
}
```
### rememberUpdatedState: 값이 변경되어도 재시작하면 안 되는 효과 내에서의 값을 참조

`LaunchedEffect`는 `키(key)` 매개 변수가 변경될 때마다 `lambda`를 재시작합니다.  
그런데 어떤 상황에서는 특정 값이 변경되더라도 그에 따라 `Effect`가 재시작되는 것을 원하지 않을 수 있습니다. 
예를 들어, 긴 시간이 소요되는 작업이나 비용이 많이 드는 작업을 수행하는 경우에는 값의 변화에도 불구하고 작업을 재시작하고 싶지 않는 상황이 있을겁니다.
 
`rememberUpdatedState`를 사용하면 값을 `기억(remember)`하게 되며, 해당 값이 변경되어도 `Effect`를 재시작하지 않습니다. 
이는 `Effect` 내부에서 사용되는 값이 최신 상태를 유지하면서도, 값의 변화에 따라 `Effect`가 재시작되는 것을 방지할 수 있습니다.

> 보통의 Effect API는 **Effect의 값의 변화 -> Effect 재시작** 이라는 일반적인 흐름을 가지지만,  
> `rememberUpdatedState`는 **값의 변화 -> 값만 업데이트, Effect 그대로 유지**라는 흐름을 가지도록 돕는 API입니다.   
> 이를 통해 비용이 많이 드는 작업을 효율적으로 관리할 수 있습니다.

예를 들어, 앱에 일정 시간 후 사라지는 `LandingScreen`이 있다고 가정해 보겠습니다.   
`LandingScreen`이 재구성 되더라도 타이머 기능이 재시작 되어서는 안 됩니다.

```kotlin
@Composable
fun LandingScreen(onTimeout: () -> Unit) {

    // LandingScreen이 재구성되더라도 항상 최신 onTimeout 함수 참조
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    LaunchedEffect(true) {
        delay(SplashWaitTimeMillis)
        currentOnTimeout()
    }
    
}
```

특정 경우에는 이 `LaunchedEffect`가 해당 composable의 생명주기 동안 단 한 번만 실행되기를 원할 수 있습니다.  
그런 경우에는 변하지 않는 값(예: `true`나 `Unit`과 같은 상수)를 `LaunchedEffect`의 `Key` 값으로 사용합니다.   
이렇게 하면, 이 composable이 재구성되더라도 `Key` 값이 변하지 않으므로 `LaunchedEffect` 내의 코드 블록이 재실행되지 않습니다.

> 👀 경고: LaunchedEffect(true)는 그 특성상 필요한 경우가 아니라면 
> **무한 루프**를 만들 수 있는 동작을 하므로 사용을 자제해야 합니다.


### DisposableEffect: Effect API 정리

`DisposableEffect`는 `Key`가 변경되거나 composable이 Composition에서 제거된 후에 정리해야 하는 Side-Effect를 처리하기 위해 사용합니다.   

`DisposableEffect`의 `Key`가 변경되면, composable은 현재의 Effect를 정리하고(즉, **cleanup을 수행**하고), Effect를 다시 호출함으로써 리셋해야 합니다.

예를 들어, `LifecycleObserver`를 사용하여 `Lifecycle` 이벤트에 기반한 분석 이벤트를 보냅니다.   
Compose에서 이러한 이벤트를 수신하기 위해, 필요할 때 `observer`를 등록하고 해제하는 `DisposableEffect`를 사용할 수 있습니다.

```kotlin
@Composable
fun HomeScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onStart: () -> Unit, // 'started' 분석 이벤트를 보냄
    onStop: () -> Unit // 'stopped' 분석 이벤트를 보냄
) {
    // 새로운 람다가 제공될 때 안전하게 현재 람다를 업데이트
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)

    // lifecycleOwner 변경 시 Effect를 제거하고 리셋
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                currentOnStop()
            }
        }

        // observer를 lifecycle에 추가
        lifecycleOwner.lifecycle.addObserver(observer)

        // effect가 Composition을 떠날 때 observer를 제거
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
```

위의 코드에서 Effect는 `observer`를 `lifecycleOwner`에 추가합니다. 
`lifecycleOwner`가 변경되면, Effect는 제거되고 새 `lifecycleOwner`로 재시작됩니다.

`DisposableEffect`는 반드시 그 코드 블록의 마지막 문장으로 `onDispose` 절을 포함해야 합니다.

> `onDispose`에 빈 블록을 두는 것은 좋은 방법이 아닙니다.  
> 항상 다시 확인하여 사용 사례에 더 적합한 Effect가 있는지 확인해야 합니다.

### SideEffect: Compose와 Non-Compose 코드 간 상태 공유

App은 UI 외에도 여러 가지 다른 요소들을 갖고 있습니다. 
예를 들어, 네트워크 라이브러리, 데이터베이스, 분석 도구 등이 있습니다. 
이러한 요소들은 Compose 시스템이 아닌 외부 시스템입니다.

Compose는 변경된 상태를 시스템 외부와 공유하기 위해서 `SideEffect` Composable을 사용합니다.
Composable 내부에 `SideEffect` 호출 시 상태가 변경되어 재구성 될 때마다 호출됩니다.

예를 들어, 분석 라이브러리는 사용자를 세분화하기 위해 사용자 정의 메타데이터를 첨부하는 기능을 제공할 수 있습니다. 
사용자의 사용자 유형을 분석 라이브러리에 전달하기 위해 `SideEffect`를 사용하여 그 값을 업데이트합니다.
```kotlin
@Composable
fun rememberFirebaseAnalytics(user: User): FirebaseAnalytics {
    val analytics: FirebaseAnalytics = remember {
        FirebaseAnalytics()
    }

    // userType Firebase Analytics 업데이트
    SideEffect {
        analytics.setUserProperty("userType", user.userType)
    }
    return analytics
}
```
이 코드는 Composable이 성공적으로 구현될 때 마다 `SideEffect`가 `FirebaseAnalytics`의 "userType" 사용자 속성을 현재 `User`의 `userType`으로 업데이트합니다.


### produceState: Non-Compose 상태를 Compose 상태로 변환

`produceState`는 반환된 `State`에 값을 푸시할 수 있는 Composition 범위의 코루틴을 실행합니다.   
이는 Non-Compose 상태를 Compose 상태로 변환하는 데 사용될 수 있으며, 
예를 들어 `Flow`, `LiveData`, `RxJava`와 같은 외부 구독 기반 상태를 Composition으로 가져올 때 사용할 수 있습니다.

생산자(producer)는 `produceState`가 Composition에 진입 시 실행되며, Composition에서 빠져나갈 때 취소됩니다.   
반환된 `State`는 누산(conflate)되며, 이를 통해 동일한 값을 설정해도 재구성(recomposition)을 트리거하지 않습니다.

`produceState`가 코루틴을 생성하는 것이지만, Non-suspend 데이터 소스를 관찰하는 데도 사용할 수 있습니다.   
해당 소스에 대한 구독을 제거하려면 `awaitDispose` 함수를 사용합니다.

아래 예제는 `produceState`를 사용하여 네트워크에서 이미지를 로드하는 방법을 보여줍니다.   
`loadNetworkImage` composable 함수는 다른 composables에서 사용할 수 있는 `State`를 반환합니다.

```kotlin
@Composable
fun loadNetworkImage(
    url: String,
    imageRepository: ImageRepository = ImageRepository()
): State<Result<Image>> {

    // Result.Loading을 초기값으로 가지는 State<T> 생성
    // 'url'이나 'imageRepository'가 변경되면, 실행 중인 producer는 취소되고
    // 새 입력값으로 재실행
    return produceState<Result<Image>>(
        initialValue = Result.Loading,
        url, 
        imageRepository
    ) {

        // 코루틴 내에서는 suspend 호출을 할 수 있음
        val image = imageRepository.load(url)

        // Error 또는 Success 결과를 가진 State 업데이트
        // 이 State를 읽는 Composable에 재구성을 트리거
        value = if (image == null) {
            Result.Error
        } else {
            Result.Success(image)
        }
    }
}
```

> 반환 타입을 가진 Composables은 일반적인 Kotlin 함수와 같이 소문자로 시작하는 이름을 사용해야 합니다.
> 반환 타입을 가지지 않은 Composable은 Class와 같이 대문자로 시작하는 이름을 사용해야 했습니다.


#### `produceState`의 내부 동작
```kotlin
@Composable
fun <T> produceState(
    initialValue: T,
    producer: suspend ProduceStateScope<T>.() -> Unit
): State<T> {
    val result = remember { mutableStateOf(initialValue) }
    LaunchedEffect(Unit) {
        ProduceStateScopeImpl(result, coroutineContext).producer()
    }
    return result
}
```
1. `remember` 함수를 사용하여 `mutableStateOf(initialValue)`의 결과를 저장합니다.   
여기서 `mutableStateOf(initialValue)`는 초기값이 `initialValue`인 변경 가능한 상태를 생성합니다.
`remember` 함수는 이 상태를 저장하여 Compose 라이프사이클 내에서 이 상태를 유지합니다. 
이렇게 하면 이 상태가 변경되면 관련된 UI를 자동으로 다시 그릴 수 있습니다.  

2. `LaunchedEffect`는 `produceState`에서 주어진 `producer 블록`(즉, 상태를 업데이트하는 데 사용되는 코드 블록)를 실행하는 코루틴을 실행합니다.  
이 코루틴은 새로운 상태를 계산하고 이 상태를 `mutableStateOf`로 저장된 상태에 저장합니다.

3. `producer 블록`에서 `value`를 업데이트하면, 이 값이 `mutableStateOf`에 저장된 상태로 설정되고, 이로 인해 관련된 UI가 자동으로 다시 그려집니다.
 
### derivedStateOf: 하나 이상의 상태 객체를 다른 상태로 변환
`derivedStateOf`는 특정한 상태가 다른 상태 객체들로부터 계산되거나 파생될 때 사용되는 함수입니다. 
이 함수를 사용하면 계산에 사용되는 상태 중 어느 하나가 변경될 때 마다 계산이 이루어진다는 것을 보장할 수 있습니다.

다음 예제는 사용자가 정의한 고 우선순위 키워드를 가진 작업이 먼저 나타나는 기본적인 TODO List를 보여줍니다.
```kotlin
@Composable
fun TodoList(highPriorityKeywords: List<String> = listOf("Review", "Unblock", "Compose")) {

    val todoTasks = remember { mutableStateListOf<String>() }

    // todoTasks 또는 highPriorityKeywords가 변경될 때만 고 우선순위 작업을 계산하고, 매번 재구성할 때마다 계산하지 않음
    val highPriorityTasks by remember(highPriorityKeywords) {
        derivedStateOf {
            todoTasks.filter { task ->
                highPriorityKeywords.any { keyword ->
                    task.contains(keyword)
                }
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        LazyColumn {
            items(highPriorityTasks) { /* ... */ }
            items(todoTasks) { /* ... */ }
        }
        /* 사용자가 목록에 요소를 추가할 수 있는 나머지 UI */
    }
}
```
위 코드는 `derivedStateOf`는 `todoTasks`가 변경될 때마다 `highPriorityTasks` 계산이 이루어지고 UI가 그에 따라 업데이트됨을 보장하고 있습니다.

`derivedStateOf`를 사용하는 것은 `todoTasks` 또는 `highPriorityKeywords`가 변경될 때만 `highPriorityTasks`를 계산하게 하기 위함입니다. 
만약 이런 최적화를 하지 않으면, 매번 화면이 다시 그려질 때마다 (즉, 매번 재구성될 때마다) `highPriorityTasks`가 계산될 것입니다. 이는 비효율적입니다.

또한 `derivedStateOf`에 의해 생성된 상태의 업데이트는 그것이 선언된 Composable 함수가 다시 Compose되게 만들지 않습니다.   
이는 `derivedStateOf`로 생성된 상태가 변경되어도, 이 상태를 사용하는 UI가 모두 업데이트되는 것이 아닌, 이 상태를 실제로 읽는 UI만 업데이트되도록 하기 위함입니다. 
위 예제에서는 `LazyColumn`이 해당됩니다.

마지막으로, 이 코드는 `highPriorityKeywords`가 `todoTasks`보다 훨씬 덜 자주 변경된다고 가정하고 있습니다. 
만약 그렇지 않다면, `remember(todoTasks, highPriorityKeywords)`를 사용하여 
두 상태가 모두 변경될 때마다 `highPriorityTasks`를 다시 계산하도록 할 수 있습니다.

### snapshotFlow: convert Compose's State into Flows
`snapshotFlow`는 Compose의 `State<T>` 객체를 `Cold Flow`로 변환하는 데 사용됩니다.  
`snapshotFlow`는 `Collect`시 실행되고, 그 안에서 읽히는 `State` 객체의 결과를 `emit` 합니다.   
`snapshotFlow` 블록 내에서 읽히는 `State` 객체 중 하나가 변경되면, `Flow`는 새 값을 수집기에 `emit` 합니다.   
이 새 값이 이전에 `emit` 된 값과 **같지 않은 경우에만 이런 동작이 발생**합니다 (`Flow.distinctUntilChanged`의 동작과 유사합니다).

다음 예는 사용자가 리스트의 첫 번째 아이템을 스크롤하여 지나갈 때 analytics에 이를 기록하는 `Side-Effect`를 보여줍니다:

```kotlin
val listState = rememberLazyListState()

LazyColumn(state = listState) {
    // ...
}

LaunchedEffect(listState) {
    snapshotFlow { listState.firstVisibleItemIndex }
        .map { index -> index > 0 }
        .distinctUntilChanged()
        .filter { it == true }
        .collect {
            MyAnalyticsService.sendScrolledPastFirstItemEvent()
        }
}
```

위의 코드에서 `listState.firstVisibleItemIndex`는 `Flow`의 연산자의 확장 함수들을 활용할 수 있는 `Flow`로 변환됩니다.

---

## Effects 재실행
`LaunchedEffect`, `produceState`, `DisposableEffect`와 같은 몇몇 `Effect API`들은 실행 중인 작업을 취소하고, 
새로운 `Key`와 함께 새 작업을 시작하기 위해 `숫자 타입의 가변 인자`나 `Key`를 받습니다.

일반적으로, 위에서 언급한 `Effect API`들의 형태는 다음과 같습니다.

```kotlin
EffectName(ifThisKeyChanges, orThisKeyChanges, orThisKeyChanges, ...) { block }
```

`EffectName`은 실행하려는 `Effect`이고 뒤이어 나오는 매개변수들은 이 `Effect`가 변경되어 재실행되어야 할 때를 지정합니다.  
이러한 변경은 `ifThisKeyChanges`, `orThisKeyChanges`등의 `key`가 변화할 때 발생합니다.

그러나 위 방식은 아래와 같은 문제점이 발생할 수 있습니다.

### Effect 재실행 시 발생 할 수 있는 문제점
- 필요한 만큼 `Effect`가 재실행되지 않으면 앱에서 버그가 발생할 수 있습니다. 
  예를 들어, UI가 적절하게 업데이트 되지 않을 수 있습니다.  
- 필요한 것 보다 `Effect`더 자주 재실행되면 비효율적일 수 있습니다. 
  예를 들어, 네트워크 호출을 불필요하게 많이 발생시킬 수 있습니다.

이를 위해 다음과 같은 규칙들이 제안되었습니다.

### Effect 재실행 규칙
- `Effect` 코드 블록 내에서 사용되는 모든 `mutable` 또는 `immutable` 변수는 `Effect` Composable의 매개변수로 추가되어야 합니다.
  - 이를 통해, 이 변수들의 변화가 `Effect`의 재실행을 적절히 트리거 할 수 있습니다.
- `Effect`를 강제로 재실행하고 싶은 경우에는, 더 많은 `Key`를 매개변수로 추가 할 수 있습니다.
  - 이는 특정 `Effect`가 필요한 시점보다 덜 실행되는 상황을 방지하는데 사용됩니다.
- 만약 변수의 변경이 `Effect`를 재실행시킬 필요가 없다면, 이 변수는 `rememberUpdatedState`를 사용하여 감싸야 합니다.
  - 이는 `Effect`가 불필요하게 재실행되는 것을 방지하는데 사용됩니다.
- 변수가 `remember`함수에 의해 감싸져 있고, `key`가 없어 변하지 않는다면, 이 변수를 `Effect`의 `Key`로 전달할 필요는 없습니다.
  - 이는 `Effect`가 불필요하게 재실행되는 것을 방지하는 또 다른 방법입니다. 

> `Effect`에서 사용되는 모든 변수들은 `Effect` Composable 함수의 매개변수로 추가되거나, `rememberUpdatedState`로 감싸져야 합니다.
> 이렇게 하면, `Effect`의 재실행이 적절히 관리되어 앱의 성능과 정확성이 향상될 수 있습니다.

## 상수를 키로 사용하기
호출 위치의 `Lifecycle`을 따르게 하려면 상수를 `Effect`의 `Key`로 사용할 수 있습니다.   
그러나 이렇게 실행하기 전에 두 번 생각하고 그것이 정말 필요한지 확인하십시오.