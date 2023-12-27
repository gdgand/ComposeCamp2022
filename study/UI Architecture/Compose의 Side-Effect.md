# Side-effects in Compose

'Side-Effect'는 컴포저블 'Scope' 외부에서 '앱의 상태'가 변경되는 것을 말합니다.

컴포저블은 'ReComposition'의 순서가 바뀌거나, 중단될 수 있기에 예측할 수 없습니다.  
이러한 특징으로 인해 이상적으로는 컴포저블에 'Side-Effect'가 없어야 합니다.

하지만 때때로 앱의 특정 '상태'에 따라 SnackBar를 보여주거나 다른 화면으로 네비게이션하는 등의 작업이 필요한데,    
이와 같은 작업들은 일회성 이벤트를 트리거하는 'Side-Effect'의 예시로써 볼 수 있습니다.

'Side-Effect'는 컴포저블의 생명주기를 고려하여 통제된 방식으로 수행되어야 합니다.  
즉, 컴포저블이 화면에 존재하는 동안만 'Side-Effect'가 실행되고, 컴포저블이 화면에서 제거되면 'Side-Effect'도 중단되어야 합니다.

Compose는 이러한 요구사항을 충족하기 위해 다양한 'Side-Effect API'를 제공합니다.

---

## State and effect use cases

> - LaunchedEffect
>   - Composable에서 'suspend function'을 안전하게 호출하는 'Effect Composable'
>   - 'Composition' 진입 시, `Block`을 'Coroutine'으로 시작, 'Composition'을 떠나면, 'Coroutine' 취소
>   - `Key` 변경 시, 'Coroutine' 취소 후, 새로운 'Coroutine'으로 재시작
> - rememberCoroutineScope
>   - 'Composable Lifecycle'에 연결된 `CoroutineScope` 생성
>   - Composable '외부'에서 'Coroutine' 실행 가능 (`ViewModel`에서 Composable Animation 처리)
>   - 해당 Composable이 'Composition'을 떠나면 모든 'Child Coroutine' 취소 처리
> - rememberUpdatedState
>   - 'Composable'의 'ReComposition' 시, 참조 값 유지
>   - `Effect`에서 '오래 지속되는 작업', '비용이 많이 드는 작업' 처리 시 유용
> - DisposableEffect 
>   - 'Composable'이 'Composition'에서 제거 또는 `Key` 변경 시, 정리가 필요한 'Side-Effect' 관리
>   - `DisposableEffect`에서 `onDispose` 블록 포함을 잊으면 안됨 
>   - `onDispose`가 '빈' 경우, `DisposableEffect`가 필요하지 않는 상황일 수 있기에 다른 방식을 생각해보아야 함
> - SideEffect
>   - 'Compose State'를 'Non-Compose Code'에 공유할 때 사용
>   - 매번 '성공적인 Composition(ReComposition 포함)' 후, `SideEffect` 내부 코드 블록 실행 보장
> - produceState
>   - 'Non-Compose State'를 'Compose State'로 변환할 때 사용
>   - 'Composition'과 연관된 'Coroutine' 실행 후, '반환된 State'를 `value`를 통해 푸시하는 기능 제공
>   - `Flow`, `LiveData`, `RxJava`와 같은 '구독 기반 State'를 'Composition'으로 가져올 때 유용
>   - 내부에서 `LuanchedEffect`를 사용, 이에 따라 'Composition' 진입 시 'Coroutine' 실행 'Composition'을 떠날 때 'Coroutine' 취소
>   - '반환된 State'는 같은 값을 설정해도 'ReComposition'을 트리거하지 않음
>   - 'Coroutine' 생성으로 인해, 'Non-suspend DataSource'를 관찰 가능, `awaitDispose`로 구독 제거 필요
> - derivedStateOf
>   - 'Compose'에서 'State', 'Composable 입력'이 실제 필요한 UI 업데이트 보다 자주 변경될 때 사용
>   - 'Scroll Position'과 같이 빈번하게 변경되지만 특정 시기에만 반응해야 하는 'State'를 다룰 때 사용
> - snapshotFlow
>   - 'Compose' `State<T>`를 `Flow`로 변환할 때 사용
>   - '터미널 연산' 호출 시, '람다 블록'을 실행하며 'State' 결과를 내보냄
>   - '람다 블록'에서 읽힌 'State' 중 하나라도 변경되면 '람다 블록'을 재실행

컴포저블은 UI 작업 외, 'Side-Effect' 작업을 하지 않는게 좋습니다.  
하지만, 때떄로 앱의 '상태'를 변경해야 하는 경우 `Effect` API를 통해 처리할 수 있습니다.

`Effect`는 UI를 생성하지 않고, 'Composition'이 완료될 때, 'Side-Effect'를 실행하는 '컴포저블'입니다.  
`Effect`는 쉽게 남용될 수 있기에 수행되는 작업이 UI와 관련되어 있는지, 단방향 데이터 흐름을 깨뜨리지 않는지, 유의해서 사용해야 합니다.

### LaunchedEffect: run suspend functions in the scope of a composable

컴포저블에서 안전하게 'suspend function'을 호출하려면 `LaunchedEffect`를 사용하면 됩니다.

`LaunchedEffect`가 'Composition'에 들어갈 때, `block` 파라미터를 'Coroutine'으로 시작합니다.  
만약 `LaunchedEffect`가 'Composition'에서 벗어나면 해당 'Coroutine'을 취소합니다.

또한 `LuanchedEffect`의 `Key` 값이 변경되면, '`Effect` 컴포저블'이 'ReComposition'되어서 
기존 'Coroutine'을 취소하고, 새로운 'suspend function'을 새로운 'Coroutine'에서 시작하게 됩니다.

예를 들어 `Scaffold` 안에서 `Snackbar`를 표시하는 예시입니다.   
`SnackbarHostState.showSnackbar`는 'suspend function' 입니다.

```kotlin
@Composable
fun MyScreen(
    state: UiState<List<Movie>>,
    snackbarHostState: SnackbarHostState
) {
    // UI 상태에 오류가 있으면 Snackbar 표시
    if (state.hasError) {

        // `snackbarHostState` 변경 시, `LaunchedEffect` 취소 후 다시 시작
        LaunchedEffect(snackbarHostState) {
            // 'Coroutine'으로 snackbar 표시, 'Coroutine'이 취소되면 자동으로 snackbar 해제
            // 'LaunchedEffect Composable'의 Composition 진입과 해제는 `state.hasError`가 변경될 때마다 수행됨 
            // 이 'Coroutine'은 'state.hasError'의 값에 따라 취소되고 시작됨
            // 또는 `scaffoldState.snackbarHostState`가 변경될 때 다시 시작됩니다.
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

---

### rememberCoroutineScope: obtain a composition-aware scope to launch a coroutine outside a composable

`LaunchedEffect`는 컴포저블이기에, 다른 컴포저블에서만 사용할 수 있습니다.  

만약 컴포저블 '외부'에서 'Coroutine'을 실행하고 싶고, 컴포저블 생명주기에 맞춰 'Coroutine'이 자동으로 관리되도록 `Scope`를 설정하고 싶은 경우 `rememberCoroutineScope`를 사용할 수 있습니다. 

또한 사용자 이벤트에 반응하여 'Coroutine'으로 애니메이션을 시작하고 중단하는 경우에도 `rememberCoroutineScope`를 사용하여 편하게 처리할 수 있습니다.

`rememberCoroutineScope`으로 생성된 `CoroutineScope`는 컴포저블이 존재하는 'Composition'에 연결됩니다.  
즉, `CoroutineScope`가 해당 컴포저블의 생명주기를 따름을 의미합니다. 
만약  컴포저블이 화면에서 제거되어 'Composition'에서 떠나면, `rememberCoroutineScope`로 생성된 `CoroutineScope` 내에서 실행 중인 모든 'Child Coroutine'은 자동으로 '취소'됩니다.

앞선 예제를 이어서 보면, 사용자가 `Button` 클릭 시 Snackbar가 표시되도록 사용 할 수 있습니다.

```kotlin
@Composable
fun MoviesScreen(snackbarHostState: SnackbarHostState) {

    // `MoviesScreen` Composable Lifecycle에 연결된 CoroutineScope 생성
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Column(
          Modifier.padding(contentPadding)
        ) {
            Button(
                onClick = {
                    // 'Child Coroutine' 생성하여 'Snackbar' 표시
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

---

### rememberUpdatedState: refrence a value in an effect that shouldn't restart if the value changes

`LaunchedEffect`는 `Key` 파라미터가 변경될 때마다 재시작됩니다.   
그러나 특정 상황에서 `Key`가 변경 되어도 `Effect`를 재시작하고 싶지 않을 수 있습니다.

이를 위해 `rememberUpdatedState`를 통해 특정 값을 참조하면서 해당 값이 변경되더라도 컴포저블이 'ReComposition'될 때마다 해당 참조를 유지하게 할 수 있습니다.
이는 '오래 지속되는 작업'이나 '비용이 많이 드는 작업'을 실행하는 `Effect`에서 유용하게 사용될 수 있습니다.

일정 시간 후 사라지는 `LandingScreen`은 'ReComposition' 되더라도, 
일정 시간을 기다린 후 시간이 지났음을 알리는 `Effect`의 재시작은 원하지 않을 수 있습니다.

```kotlin
@Composable
fun LandingScreen(onTimeout: () -> Unit) {

    // LandingScreen이 재구성되더라도 항상 최신 onTimeout 함수 참조
    val currentOnTimeout by rememberUpdatedState(onTimeout)
  
    // LandingScreen 생명 주기에 맞는 Effect 실행
    // LandingScreen ReComposition 되어도 currentOnTimeout 함수 참조는 유지됨
    LaunchedEffect(true) {
        delay(SplashWaitTimeMillis)
        currentOnTimeout()
    }
}
```

---

### DisposableEffect: effects that require cleanup

`DisposableEffect`는 'Composable'이 'Composition'에서 제거되거나, `DisposableEffect`에 제공된 `Key`가 변경될 때 정리가 필요한 'Side-Effect'를 관리하는 데 사용됩니다.

예를 들어 `DisposableEffect`를 `LifecycleObserver`와 함께 사용하여,
'Composable' 내에서 `Lifecycle Event`를 기반으로 'Analytics Event'를 처리할 수 있습니다. 

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

        lifecycleOwner.lifecycle.addObserver(observer)

        // Effect가 Composition을 떠날 때 observer 제거
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
```

`DisposableEffect`에서 `onDispose` 블록 포함을 잊으면 안됩니다.  
만약 빈 `onDispose`를 작성하는 경우, `DisposableEffect`가 필요하지 않는 상황일 수 있기에 다른 방식을 생각해보아야 합니다.

---

### SideEffect: publish Compose state to non-Compose code

`SideEffect`는 'Compose'가 관리하지 않는 객체에 'Compose State'를 공유할 때 사용됩니다.  

`SideEffect` 내부의 코드는 'ReComposition'이 성공적으로 완료된 후 실행되는 것을 보장합니다.  
즉, 'ReComposition'이 성공적으로 완료되기 전 `SideEffect`를 사용하여 `Effect`를 수행하는 것은 잘못된 접근 방식입니다.

예를 들어, Analytics 라이브러리는 사용자 집단을 세분화하기 위해 '사용자 정의 메타데이터'를 모든 이벤트에 첨부해야 합니다.  
이때, 현재 사용자 유형을 Analytics 라이브러리에 전달하기 위해 `SideEffect`를 사용하여 해당 값을 업데이트 할 수 있습니다.

```kotlin
@Composable
fun rememberFirebaseAnalytics(user: User): FirebaseAnalytics {
    val analytics: FirebaseAnalytics = remember {
        FirebaseAnalytics()
    }

    // 매번 성공적인 Composition 후, 현재 사용자 유형 FirebaseAnalytics 업데이트 보장
    SideEffect {
        analytics.setUserProperty("userType", user.userType)
    }
    return analytics
}
```

---

### produceState: convert non-Compose state into Compose state

`produceState`는 'Composition'과 연관된 'Coroutine'을 시작하여, '반환된 State'로 값을 푸시하는 기능을 제공합니다.  
이를 통해 'Non-Compose State'를 'Compose State'로 변환할 수 있습니다.  
예를 들어 `Flow`, `LiveData`, `RxJava`와 같은 '구독 기반 State'를 'Composition'으로 가져올 때 유용합니다.

`produceState`가 'Composition'으로 진입할 때 실행되며, 'Composition'에서 떠날 때 취소됩니다.  
'반환된 State'는 같은 값을 설정해도 'ReComposition'을 트리거하지 않는 특성을 가집니다.

또한 `produceState`는 'Coroutine'을 생성하지만, 'Non-suspend DataSource'를 관찰할 때에도 사용할 수 있습니다.  
만약 해당 DataSource에 대한 구독을 제거하려면 `awaitDispose`를 사용하면 됩니다.

아래 예제는 `produceState`를 사용하여 네트워크에서 이미지를 로드하는 방법을 보여줍니다.   
`loadNetworkImage`은 다른 'Composable'에서 사용할 수 있는 `State`를 반환합니다.

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

        // 'Coroutine'이 실행되기에 suspend 호출 가능
        val image = imageRepository.load(url)

        // Error 또는 Success 결과를 가진 State 업데이트
        // 이 State를 읽는 Composable에 ReComposition 트리거
        value = if (image == null) {
            Result.Error
        } else {
            Result.Success(image)
        }
    }
}
```

중요한 점은 `produceState`는 내부에서 초기값으로 `remember { mutableStateOf(initialValue) }`를 사용하여 결과를 유지하고, 
`LuanchedEffect`를 사용하여 `produceState` 블록을 트리거하며, `value`가 업데이트될 때마다 `State`를 새로운 값으로 업데이트하며 알립니다.

---

### derivedStateOf: convert one or multiple state objects into another state

`derivedStateOf`는 'Compose'에서 '하나 이상의 State'를 '다른 State'로 변환하는데 사용됩니다.

'Compose'에서는 'State'나 'Composable 입력'이 변경될 때마다 'ReComposition'이 발생합니다. 
하지만 'State'나 'Composable 입력'이 실제로 필요한 UI 업데이트 보다 자주 변경된다면 불필요한 'ReComposition'으로 이어질 수 있습니다.

예를 들어 'Scroll Position'과 같이 빈번하게 변경되지만 'Composable'이 특정 임계값을 넘었을 때만 반응해야 하는 경우에 자주 사용될 수 있습니다.
이처럼 `drivedStateOf`는 필요한 만큼만 업데이트 되는 새로운 'Compose State'를 생성할 수 있습니다.  
이 방식은 `Flow`의 `distinctUntilChanged()`와 유사하게 동작합니다.

아래는 `drivedStateOf`에 대한 '적절한 사용 사례' 입니다.

```kotlin
@Composable
fun MessageList(messages: List<Message>) {
    Box {
        val listState = rememberLazyListState()
      
        LazyColumn(state = listState) {
            // ...
        }
        
        // 첫 번째로 보이는 항목을 지나면 버튼 표시
        // 불필요한 ReComposition을 최소화 하기 위해 remember 사용
        val showButton by remember { 
            derviedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        
        AnimatedVisibility(visible = showButton) {
            ScrollToTopButton()
        }
    }
}
```


2개의 'Compose State' 결합 시, `derivedStateOf`를 사용하는 것은 '잘못된 사용 방식'입니다.

```kotlin
// 사용하지 마세요. 잘못된 사용 방식 입니다.
val firstName by remember { mutableStateOf("") }
val lastName by remember { mutableStateOf("") }

val fullName by remember { derivedStateOf { "$firstName $lastName" } } // 잘못된 사용 방식
val fullNameCorrect = "$firstName $lastName" // 올바른 사용 방식
```

---

### snapshotFlow: convert Compose's State into Flows

`snapshotFlow`는 'Compose'의 `State<T>`를 `Flow`로 변환하는데 사용됩니다.

`snapshotFlow`는 '터미널 연산'이 호출되면 `block`을 실행하고, 그 안에 읽힌 `State` 결과를 내보냅니다.  
또한 `block` 내에서 읽힌 `State` 중 하나가 변경되면 `block`을 다시 실행합니다. 그 후 새로운 값이 이전에 내보낸 값과 다를 경우, `Flow`는 새로운 값을 내보냅니다.

다음 예제는 사용자 목록의 첫 번째 항목을 스크롤할 때 Analytics 기록하는 'Side-Effect'의 예시입니다.

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

---

## Restarting effects

> - `Key` 파라미터를 통해 `Effect` 재시작 관리
> - `Effect`를 적절하게 관리하기 위한 원칙
>   - `Effect` 'Block 내부'에 사용되는 변수들은 `Effect`의 `Key`로 전달되어야 함
>   - 변수 변경이 `Effect`를 재시작하게 하지 않는 경우 해당 변수를 `rememberUpdatedState`로 래핑해서 사용
>   - 변수가 `remember`로 래핑되고 `Key`가 없어 변하지 않는다면, 해당 변수를 `Effect`의 `Key`로 전달할 필요가 없음

'Compose'에서 특정 `Effect`(`LaunchedEffect`, `produceState`, `DisposableEffect` 등)은 `Key`를 파라미터로 받습니다. 
이 `Key`는 실행 중인 `Effect`를 '취소'하고 새로운 `Key`로 새로운 `Effect`를 시작하는데 사용됩니다.

위 `Effect`들의 일반적인 형태는 다음과 같습니다.

```kotlin
EffectName(ifThisKeyChanges, orThisKeyChanges, orThisKeyChanges, ...) { block }
```

`Effect`를 재시작하는 데 사용되는 `Key` 파라미터가 올바르지 않으면 다음과 같은 문제가 발생할 수 있습니다.

1. 사용자 입력에 따라 'State'가 변경되어 `Effect`를 재시작하는 경우에 만약 해당 'State'가 `Effect`의 `Key`로 사용되지 않으면, 
   사용자 입력에 따른 'State' 변화가 반영되지 않아 앱에 버그를 유발할 수 있습니다.
2. 'State' 변화가 UI 업데이트에 영향을 주지 않음에도 불구하고 `Effect`를 재시작 하게되면 불필요한 리소스가 소모될 수 있습니다. 

이에 따라 아래는 `Effect`를 적절하게 관리하기 위한 방식 입니다.

1. `Effect` 'Block 내부'에 사용되는 변수들은 `Effect`의 `Key`로 전달되어야 합니다.  
   `Effect` 'Block' 안에서 사용되는 '가변•불변 변수'들은 해당 `Effect`가 재시작 되어야 하는 기준이 됩니다. 해당 변수들이 변경될 떄 `Effect`가 재시작되어야 올바른 동작을 보장할 수 있습니다. 
2. 필요에 따라 `Effect`를 강제로 재시작 하기 위해 추가적인 파라미터를 넣을 수 있습니다.  
3. 변수의 변경이 `Effect`를 재시작하게 하지 않아야 하는 경우에는 해당 변수를 `rememberUpdatedState`로 래핑해야 합니다.  
   이는 변수의 최신 상태를 유지하면서도 `Effect`의 재시작을 유발하지 않습니다.
4. 변수가 `remember`로 래핑되어 있고, `Key`가 없어서 변하지 않는다면, 그 변수를 `Effect`의 `Key`로 전달할 필요가 없습니다.

---

## 상수를 키로 사용하기
호출 위치의 `Lifecycle`을 따르게 하려면 상수를 `Effect`의 `Key`로 사용할 수 있습니다.   
그러나 이렇게 실행하기 전에 두 번 생각하고 그것이 정말 필요한지 확인하십시오.