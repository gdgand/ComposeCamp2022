https://developer.android.com/codelabs/jetpack-compose-advanced-state-side-effects?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fcompose%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-advanced-state-side-effects#0



StateFlow.collectAsState
LiveData.observeAsState
Kotlin 코루틴은 Android에서 비동기 작업을 수행하는 데 권장되는 방법입니다.

Compose의 부작용은 구성 가능한 함수의 범위 밖에서 발생하는 앱 상태에 관한 변경사항입니다. 
예를 들어 사용자가 버튼을 탭할 때 새 화면이 열리거나 앱이 컴포저블 내에서 안전하게 정지 함수를 호출하려면 Compose에서 코루틴 범위의 부작용을 트리거하는 LaunchedEffect API를 사용합니다.

컴포저블 내에서 안전하게 정지 함수를 호출하려면 Compose에서 코루틴 범위의 부작용을 트리거하는 LaunchedEffect API를 사용합니다.
LaunchedEffect가 컴포지션을 시작하면 매개변수로 전달된 코드 블록으로 코루틴이 실행됩니다. 
LaunchedEffect가 컴포지션을 종료하면 코루틴이 취소됩니다.

LaunchedEffect와 같은 일부 부작용 API는 다양한 수의 키를 매개변수로 사용하여 이러한 키 중 하나가 변경될 때마다 효과를 다시 시작합니다.
컴포저블의 수명 주기 동안 한 번만 부작용을 트리거하려면 상수를 키로 사용합니다(예: LaunchedEffect(true) { ... }).

***
부작용이 진행되는 동안 onTimeout이 변경되면 효과가 끝날 때 마지막 onTimeout이 호출된다는 보장이 없습니다. 캡처하고 새 값으로 업데이트하여 이를 보장하려면 rememberUpdatedState API를 사용합니다.
즉 부작용이 인자로 전달된 onTimeout 이 변경될 때마다 재 실행되는 것은 좋지 않으니 상수값으로 마지막 컴포지션까지 한번만 런치하려는 것
근데 onTimeout 이 부작용 내에서 최신화되어 실행하게하기 위해 사용하는 것이 rememberUpdatedState 라는 것

Composable 을 호출할 수 없는 곳이라서 LaunchedEffect 를 사용할 수 없는 경우
(예를 들면, 컴포지션 외부에 있는 일반 콜백에서 코루틴을 만들기 위한 호출을 트리거하는 경)
호출 사이트의 수명 주기를 따르는 CoroutineScope를 사용하는 것이 좋습니다. 
이렇게 하려면 rememberCoroutineScope API를 사용
컴포지션을 종료하면 범위가 자동으로 취소

LaunchedEffect vs rememberCoroutineScope
LandingScreen의 본문에 rememberCoroutineScope 및 scope.launch를 사용하는 경우 코루틴은 호출이 컴포지션으로 향하는지 여부와 무관하게 Compose에서 LandingScreen을 호출할 때마다 실행됩니다. 
따라서 리소스를 낭비하게 되며 제어된 환경에서 이 부작용을 실행하지 않게 됩니다.
