https://developer.android.com/codelabs/jetpack-compose-navigation?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fcompose%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-navigation#0


Compose 내에서 Navigation을 사용할 때는 탐색 그래프의 각 컴포저블 대상에 경로가 연결됩니다. 
경로는 컴포저블의 경로를 정의하는 문자열로 표현되며, 올바른 위치로 이동할 수 있도록 navController를 안내합니다. 
특정 대상으로 연결되는 암시적 딥 링크라고 생각할 수 있습니다. 
각 대상에는 고유 경로가 있어야 합니다.

## Navigation의 구성요소
Navigation의 세 가지 주요 부분은 NavController, NavGraph, NavHost입니다.
NavController는 항상 단일 NavHost 컴포저블에 연결됩니다.
NavHost는 컨테이너 역할을 하며 그래프의 현재 대상을 표시하는 일을 담당합니다.
여러 컴포저블 간에 이동하는 과정에서 NavHost의 콘텐츠가 자동으로 재구성됩니다.
또한 NavController를 이동 가능한 컴포저블 대상을 매핑하는 탐색 그래프(NavGraph)에 연결합니다.
NavHost는 가져올 수 있는 대상의 모음입니다.


- launchSingleTop = true 
  - 백 스택 위에 대상의 사본이 최대 1개가 되도록 해 줍니다.
  - Rally 앱의 경우에는 동일한 탭을 여러 번 탭해도 동일한 대상의 사본이 여러 개 실행되지 않습니다.
- popUpTo(startDestination) { saveState = true } 
  - 탭을 선택했을 때 백 스택에 대규모 대상 스택이 빌드되지 않도록 그래프의 시작 대상을 팝업으로 만듭니다.
- restoreState = true
  - 이 탐색 동작이 이전에 PopUpToBuilder.saveState 또는 popUpToSaveState 속성에 의해 저장된 상태를 복원하는지 여부를 정합니다.
  - 이동할 대상 ID를 사용하여 이전에 저장된 상태가 없다면 이 옵션은 효과가 없습니다.


백 스택에서 현재 대상의 실시간 업데이트를 State의 형식으로 받아보려면 navController.currentBackStackEntryAsState()를 사용하여 현재 destination:을 살펴보면 됩니다.


## 인수사용하여 이동
인수는 경로에 하나 이상의 인수를 전달하여 탐색 라우팅을 동적으로 만들어 주는 매우 강력한 도구입니다. 
인수를 사용하면 제공된 인수에 따라 서로 다른 정보를 표시할 수 있습니다.

Compose Navigation에서 각 NavHost 구성 가능한 함수는 백 스택에 있는 항목의 현재 경로 및 전달된 인수에 관한 정보를 저장하는 클래스인 현재 NavBackStackEntry에 액세스할 수 있습니다. 
이를 사용하여 navBackStackEntry에서 arguments목록을 가져온 다음 필요한 인수를 검색하고 가져와서 컴포저블 화면으로 전달할 수 있습니다.


## 딥링크
인수를 추가하는 것 외에도 딥 링크를 추가하여 특정 URL, 동작, MIME 유형을 컴포저블에 연결할 수 있습니다. 
Android에서 딥 링크란 앱 내의 특정 대상으로 직접 이동할 수 있는 링크입니다. 
Navigation Compose는 암시적 딥 링크를 지원합니다. 
암시적 딥 링크가 호출되면(예: 사용자가 링크를 클릭할 때) Android는 앱의 상응하는 대상을 열 수 있습니다.
암시적 딥 링크는 앱의 특정 대상을 참조합니다. 
딥 링크가 호출되면(예: 사용자가 링크를 클릭한 경우) Android는 참조 대상에 앱을 열 수 있습니다.


## 자체 컴포저블로 추출 (깔끔함)
RallyApp은 navController로 직접 사용할 수 있는 유일한 컴포저블입니다. 
앞서 언급했듯이 다른 모든 중첩된 컴포저블 화면은 navController 자체가 아닌 탐색 콜백만 받아야 합니다