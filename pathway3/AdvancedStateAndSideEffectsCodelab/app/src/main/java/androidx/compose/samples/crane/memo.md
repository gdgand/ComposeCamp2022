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

## LaunchedEffect 와 rememberUpdatedState
부작용이 진행되는 동안 onTimeout이 변경되면 효과가 끝날 때 마지막 onTimeout이 호출된다는 보장이 없습니다. 캡처하고 새 값으로 업데이트하여 이를 보장하려면 rememberUpdatedState API를 사용합니다.
즉 부작용이 인자로 전달된 onTimeout 이 변경될 때마다 재 실행되는 것은 좋지 않으니 상수값으로 마지막 컴포지션까지 한번만 런치하려는 것
근데 onTimeout 이 부작용 내에서 최신화되어 실행하게하기 위해 사용하는 것이 rememberUpdatedState 라는 것

Composable 을 호출할 수 없는 곳이라서 LaunchedEffect 를 사용할 수 없는 경우
(예를 들면, 컴포지션 외부에 있는 일반 콜백에서 코루틴을 만들기 위한 호출을 트리거하는 경)
호출 사이트의 수명 주기를 따르는 CoroutineScope를 사용하는 것이 좋습니다. 
이렇게 하려면 rememberCoroutineScope API를 사용
컴포지션을 종료하면 범위가 자동으로 취소

## rememberCoroutineScope
LaunchedEffect vs rememberCoroutineScope
LandingScreen의 본문에 rememberCoroutineScope 및 scope.launch를 사용하는 경우 코루틴은 호출이 컴포지션으로 향하는지 여부와 무관하게 Compose에서 LandingScreen을 호출할 때마다 실행됩니다. 
따라서 리소스를 낭비하게 되며 제어된 환경에서 이 부작용을 실행하지 않게 됩니다.

컴포저블의 내부 상태를 담당하는 상태 홀더를 만들어 모든 상태 변경사항을 한 곳으로 중앙화할 수 있습니다. 
이렇게 하면 상태가 쉽게 동기화되고 관련 로직도 모두 단일 클래스로 그룹화됩니다. 
또한 이 상태는 쉽게 끌어올릴 수 있으며 이 컴포저블의 호출자로부터 사용될 수 있습니다.


## 상태 홀더 만들기
앱의 다른 부분에서 재사용할 수 있는 하위 수준의 UI 구성요소이므로 상태를 끌어올리는 것이 좋습니다. 
따라서 유연성과 제어 가능성이 높을수록 좋습니다.

상태를 오직remember 처리하기만 하면 활동을 다시 만들 때 유지되지 않습니다. 
이를 달성하기 위해 remember와 유사하게 동작하는 rememberSaveable API를 대신 사용할 수 있지만 저장된 값은 활동 및 프로세스 재생성에서도 유지됩니다. 
내부적으로 저장된 인스턴스 상태 메커니즘을 사용합니다.

rememberSaveable은 Bundle 내에 저장할 수 있는 객체에 대한 추가 작업 없이 이 작업을 모두 수행합니다.
프로젝트에서 만든 커스텀 클래스는 그렇지 않습니다.
따라서 Saver를 사용하여 이 클래스의 인스턴스를 저장 및 복원하는 방법을 rememberSaveable에 알려야 합니다.

Saver 정의를 함께 작동하는 클래스와 가깝게 배치하는 것이 좋습니다. 
정적으로 액세스해야 하기 때문에 companion object에 EditableUserInputState의 Saver를 추가해 보겠습니다.

커스텀 ~State클래스의 프로퍼티 값 변화를 다른 인자 전달없이 감지하려면 LaunchedEffect 와 snapshotFlow 를 사용하는 방법이 있다.
snapshotFlow API를 사용하여 Compose State<T> 객체를 Flow로 변환합니다. snapshotFlow 내에서 읽은 상태가 변형되면 Flow는 수집기에 새 값을 내보냅니다. 
이 경우 Flow 연산자를 사용하기 위해 상태를 Flow로 변환합니다.

## DisposableEffect 활용해서 기존 라이프사이클 인식하는 컴포저블 만들기
효과가 컴포지션을 종료하는 시기를 알려주는 부작용이 있어야 일부 정리 코드를 수행할 수 있습니다.
필요한 부작용 API는 DisposableEffect입니다.
DisposableEffect는 키가 변경되거나 컴포저블이 컴포지션을 종료하면 정리되어야 하는 부작용을 위한 것입니다.
. 현재 LifecycleOwner를 LocalLifecycleOwner 컴포지션 로컬과 함께 사용해 이 관찰자를 가져올 수 있습니다


## produceState를
produceState를 사용하면 Compose가 아닌 상태를 Compose 상태로 변환할 수 있습니다. value 속성을 사용하여 반환된 State에 값을 푸시할 수 있는 컴포지션으로 범위가 지정된 코루틴을 실행합니다.
LaunchedEffect와 마찬가지로 produceState 역시 키를 가져와 계산을 취소하고 다시 시작합니다.