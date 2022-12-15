# AdvancedStateAndSideEffectsCodelab

## ViewModel에서 Flow 사용
- ``StateFlow.collectAsState`` 메소드를 사용
- ``collectAsState()``는 StateFlow에서 값 수집 후 Compose의 State API로 값 접근
- 그 외 ``LiveData.observeAsState()`` or ``Observable.subscribeAsState()`` 로 대체 가능

<br>

## LaunchedEffect , rememberUpdatedState
- Compose Side Effect: Composable 함수의 스코프 외부에서 발생하는 앱 상태에 관한 변경사항
- Composable 함수에서 suspend function을 안전하게 호출하기 위해서는 ``LaunchedEffect`` API 사용
- ``LaunchedEffect``가 컴포지션을 시작하면 매개변수로 전달된 코드 블록으로 코루틴 실행, 컴포지션 종료 시 코루틴도 취소
- Side Effect가 진행되는 동안 특정 상태가 변경 시 업데이트를 하지 않음. 이를 보장하기 위해서는 ``remeberUpdatedState`` API를 사용

```kotlin
@Composable
fun LandingScreen(modifier: Modifier = Modifier, onTimeout: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // This will always refer to the latest onTimeout function that
        // LandingScreen was recomposed with
        val currentOnTimeout by rememberUpdatedState(onTimeout)

        // Create an effect that matches the lifecycle of LandingScreen.
        // If LandingScreen recomposes or onTimeout changes,
        // the delay shouldn't start again.
        LaunchedEffect(true) {
            delay(SplashWaitTime)
            currentOnTimeout()
        }

        Image(painterResource(id = R.drawable.ic_crane_drawer), contentDescription = null)
    }
}
```

<br>

## remeberCoroutineScope
- 일부 Compose API 중 suspend function이 존재하고 그 중 하나로 ``rememberCoroutineScope``가 존재함.
- 컴포지션을 종료하면 CoroutineScope는 자동 취소
```kotlin
@Composable
fun CraneHome(
    onExploreItemClicked: OnExploreItemClicked,
    modifier: Modifier = Modifier,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.statusBarsPadding(),
        drawerContent = {
            CraneDrawer()
        }
    ) {
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
    }
}
```
- LaunchedEffect와 rememberCoroutineScope와의 차이점
    - LaunchedEffect는 composable 함수로 일반 콜백에서는 호출 불가
    - 컴포저블 함수 내에서는 remeberCoroutineScope는 언제든지 실행되지만 LaunchedEffect는 key에 해당되는 값의 변경 시에 실행됨.

<br>

## State Holder
- 컴포저블의 내부 상태를 담당하는 State Holder를 만들어서 변경사항을 한 곳에 집중
- 상태 홀더 클래스를 만든 후 프로세스 종료 시에도 Activity 유지될 수 있도록 ``rememberSaveable`` API 사용
- ``rememberSaveable``은 Bundle에 저장할 수 있는 객체만 가능 또는 ``Saver``를 사용하면 가능
- ``Saver`` 만들 시에는 save, restore 메소드를 정의
- ``snapshotFlow`` API는 Compose State를 Flow로 변환

<br>

## DisposableEffect
- 컴포지션 종료 시기를 알려주는 Side Effect로 ``DisposableEffect``가 있음.
- ``DisposableEffect``는 key가 변경되거나 컴포저블이 컴포지션을 종료하면 정리되어야 하는 부분 구현
```kotlin
@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(key1 = lifecycle, key2 = mapView) {
        // Make MapView follow the current lifecycle
        val lifecycleObserver = getMapLifecycleObserver(mapView)
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView
}
```

<br>

## produceState, deriveStateOf
- ``produceState`` API는 Compose가 아닌 상태를 Compose 상태로 변환
```kotlin
@Composable
fun DetailsScreen(
    onErrorLoading: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = viewModel()
) {
    val uiState by produceState(initialValue = DetailsUiState(isLoading = true)) {
        val cityDetailsResult = viewModel.cityDetails
        value = if (cityDetailsResult is Result.Success<ExploreModel>) {
            DetailsUiState(cityDetailsResult.data)
        } else {
            DetailsUiState(throwError = true)
        }
    }

    when {
        uiState.cityDetails != null -> {
            DetailsContent(uiState.cityDetails!!, modifier.fillMaxSize())
        }
        uiState.isLoading -> {
            Box(modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        else -> { onErrorLoading() }
    }
}
```
- ``deriveStateOf``는 다른 State에서 파생된 Compose State를 원하는 경우 사용
```kotlin
// DO NOT DO THIS - It's executed on every recomposition
val showButton = listState.firstVisibleItemIndex > 0

// Show the button if the first visible item is past
// the first item. We use a remembered derived state to
// minimize unnecessary compositions
val showButton by remember {
    derivedStateOf {
        listState.firstVisibleItemIndex > 0
    }
}
```
- Compose State를 사용하여 최소한으로 리컴포지션 수행

<br>

