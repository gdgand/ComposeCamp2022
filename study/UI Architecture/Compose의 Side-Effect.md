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