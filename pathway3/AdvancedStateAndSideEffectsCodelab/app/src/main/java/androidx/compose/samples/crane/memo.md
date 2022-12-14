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