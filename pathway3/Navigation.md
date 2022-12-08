# Jetpack Compose 탐색
### Compose Navigation으로 이전
__탐색 종목 항목 추가하기__ <br>
`build.gradle(Project: Rally)`
```
buildscript {
    ext {
        // ...
        composeNavigationVersion = '2.5.0-rc01'
```
`build.gradle(Module: Rally.app)`
```
dependencies {
  implementation "androidx.navigation:navigation-compose:$rootProject.composeNavigationVersion"
  // ...
}
```
<br>

__NavController 설정하기__<br>
- `NavController`
  - Compose에서 탐색을 사용할 때 중심이 되는 구성요소
  - 백스택 컴포저블 항목을 추적하고, 스택을 앞으로 이동하고, 백 스택 조작을 사용 설정하고, 대상 상태 간에 이동
- `rememberNavController()` 함수를 호출해 `NavController` 가져옴
- `NavController`는 항상 컴포저블 계층 구조의 최상위 수준에서 만들고 배치해야함
  - 이렇게 해야 `NavController`를 참조해야하는 모든 컴포저블이 액세스 가능
  - 상태 호이스팅 원칙 준수
  - 컴포저블 화면 간에 이동하고 백 스택을 유지하기 위한 기본 정보 소스가 되도록 해줌
  ```kotlin
  val navController = rememberNavController()
  ```
<br>

__Compose Navigation의 경로__<br>
Compose에서 Navigation을 사용할 때는 탐색 그래프의 각 컴포저블 대상에 [경로](https://developer.android.com/jetpack/compose/navigation#create-navhost)가 연결됨
- 경로는 문자열로 표현
- 올바른 위치로 이동할 수 있도록 `navController`를 안내함
- 따라서 각 대상에는 `고유 경로`가 있어야함
<br><br>

__탐색 그래프를 사용하여 NavHost 컴포저블 호출__<br>
`NavHost` 추가해 탐색 그래프 만들기
- Navigation의 세 가지 주요 부분
  - `NavController`: 항상 단일 `NavHost`에 연결
  - `NavGraph`: 이동 가능한 컴포저블 대상을 매핑하는 탐색 그래프
  - `NavHost`: 컨테이너 역할, 그래프의 현재 대상을 표시하는 일 담당
    - 여러 컴포저블 간에 이동하는 과정에서 `NavHost`의 콘텐츠가 자동으로 재구성됨
    - `NavController`를 `NavGraph`에 연결
  ```kotlin
  NavHost(
      navController = navController,
      startDestination = Overview.route,
      modifier = Modifier.padding(innerPadding)
  ) {
    ...
  }
  ```
- `builder: NavGraphBuilder.() -> Unit`
  - 탐색 그래프를 정의하고 빌드하는 일 담당
<br>

__NavGraph에 대상 추가하기__<br>
Navigation Compose는 탐색 그래프에 개별 컴포저블 대상을 쉽게 추가하고 필요한 탐색 정보를 정의할 수 있도록 `navGraphBuilder.composable` 확장 함수를 제공함
- `Overview`
  - `composable` 확장 함수를 통해 추가하고 고유 문자열 `route`를 설정해서 탐색 그래프에 대상 추가함
  - 이 대상으로 이동하면 표시될 UI도 함께 정의
```kotlin
NavHost(
  navController = navController,
  startDestination = Overview.route,
  modifier = Modifier.padding(innerPadding)
){
  composable(route = Overview.route){
    Overview.screen()
  }
  composable(route = Accounts.route) {
    Accounts.screen()
  }
  composable(route = Bills.route) {
    Bills.screen()
  }
}
```
- 아직은 RallyTabRow가 새 탐색에 연결되지 않아 화면 컴포저블의 대상이 변경되지 않음

<br>

### 탐색에 RallyTabRow 통합
- 탐색 그래프 연결
- `navController` 사용해서 RallytabRow의 `onTabSelected` 콜백을 위함 탐색 동작 정의
- `onTabSelected: (RallyDestination) -> Unit` 사용
```kotlin
Scaffold(
  topBar = {
    RallyTabRow(
      allScreens = rallyTabRowScreens,
      onTabSelected = { newScreen ->
        navController.navigate(newScreen.route)
      },
      currentScreen = currentScreen,
    )
  }
```
- 같은 탭을 연속해서 다시 탭하면 여러 개의 사본이 실행되는 문제
- 선택한 탭의 펼치기와 접기가 제대로 작동하지 않음
<br>

__대상의 단일 사본 실행하기__<br>
Compose Navigation API의 `launchSingleTop` 플래그를 `navController.navigate()`동작으로 전달
```kotlin
fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }
// 앱 전체에서 모든 대상에 이 동작을 적용하기 위해 도우미 확장으로 추출함
```
```kotlin
onTabSelected = { newScreen ->
  navController.navigateSingleTopTo(newScreen.route)
},
```
<br>

__탐색 옵션 및 백 스택 상태 제어하기__<br>
`NavOptionBuilder`에는 `launchSingleTop` 외에도 탐색동작을 세밀하게 제어하고 맞춤설정하는 데 사용할 수 있는 여러 플래그가 있음
- 스크롤 위치를 그대로 유지하고 싶은 경우
- 같은 대상을 다시 탭했을 때 화면 상태를 새로고침하고 싶은 경우
<br>

- `navigateSingleTopTo` 확장 함수 내에서 사용할 수 있는 몇가지 추가 옵션
  - `launchSingleTop = true` : 백 스택 위에 대상의 사본이 최대 1개가 되도록
  - `popUpTo(startDestination) { saveState = true }` : 탭을 선택했을 때 백 스택에 대규모 대상 스택이 빌드되지 않도록 그래프의 시작 대상을 팝업으로 만듦
    - 여기선 대상에서 뒤로 화살표를 누르면 백 스택 전체가 '개요'로 팝업됨
  - `restoreState = true` : 이 탐색 동작이 이전에 `PopUpToBuilder.saveState` 또는 `popUpToSaveState` 속성에 의해 저장된 상태를 복원하는지 여부를 정함
    - 이동할 대상 ID를 사용해 이전에 저장된 상태가 없다면 이 옵션은 효과가 없음
```kotlin
fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
}
```
<br>

__탭 UI 수정하기__<br>
백 스택에서 현재 대상의 실시간 업데이트를 `State`의 형식으로 받아보려면 `navController.currentBackStackEntryAsState()` 사용해 현재 `destination:`을 살펴보면 됨
```kotlin
fun RallyApp() {
    RallyTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        // ...
    }
}
```
- `currentBackStack?.destination`: `NavDestination` 반환
  - currentScreen을 업데이트하려면 rallyTabRowScreens 목록을 순환해 일치하는 경로를 찾은 다음 RallyDestination을 반환해야 함
    - `.find()` 함수 사용
    ```kotlin
    val currentScreen = rallyTabRowScreens.find { it.route == currentDestination?.route } ?: Overview
    ```
<br>

### RallyDestinations에서 화면 컴포저블 추출
- `NavHost` 탐색 그래프에 컴포저블을 직접 추가하고 RallyDestination에서 추출해 컴포저블 화면에 추가 정보를 직접 전달함
  - RallyDestination과 화면 객체는 `icon`, `route`와 같은 탐색 관련 정보만 저장하며 Compose UI와 분리됨
- `NavHost`에 상응하는 composable 함수로 각 화면의 컴포저블을 추출하여 `.screen()`호출을 대체함
```kotlin
NavHost(
    navController = navController,
    startDestination = Overview.route,
    modifier = Modifier.padding(innerPadding)
) {
    composable(route = Overview.route) {
        OverviewScreen()
    }
    composable(route = Accounts.route) {
        AccountsScreen()
    }
    composable(route = Bills.route) {
        BillsScreen()
    }
}
```
<br>

__OverviewScreen에서 클릭 사용 설정하기__<br>
OverviewScreen 컴포저블은 여러 함수를 콜백으로 받아서 클릭 이벤트로 설정할 수 있음
- `onClickSeeAllAccounts`, `onClickSeeAllBills` 사용
```kotlin
composable(route = Overview.route){
  OverviewScreen(
    onClickSeeAllAccounts = {
      navController.navigateSingleTopTo(Accounts.route)
    },
    onClickSeeAllBills = {
      navController.navigateSingleTopTo(Bills.route)
    }
  )
}
```
<br>

### 인수를 사용하여 SingleAccountScreen으로 이동
1. 인수를 요청하는 경로를 이전 대상에 대한 신호로 정의
   - 경로에 하나 이상의 인수를 전달해 탐색 라우팅을 동적으로 만들어줌
   - 인수는 `{ }` 사용
    ```kotlin
    route = "${SingleAccount.route}/{${SingleAccount.accountTypeArg}}"
    ```
2. composable이 인수를 받아야 한다는 사실을 알 수 있도록 함
    ```kotlin
    composable(
      route = "${SingleAccount.route}/{${SingleAccount.accountTypeArg}}",
      arguments = listOf(
        navArgument(SingleAccount.accountTypeArg){
          type = NavType.StringType
        }
      )
    ){
      ...
    }
    ```
3. 전달된 인수 값을 가져오기
   - 인수의 값이 제공되지 않을 경우에 사용할 기본값을 자리표시자로 제공해 예외 케이스 처리
      ```kotlin
        ...
      ){ navBackStackEntry ->
        val accountType = navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)
        SingleAccountScreen(accountType)
      }
      ```
<br>

__계좌 및 개요 시작 대상 설정하기__<br>
- 이전 대상이 어디였든 동일한 accountTypeArg 인수가 전달되고 있는지 확인하기
<br>

### 딥 링크 지원 사용 설정
- 인수를 추가하는 것 외에도 [딥 링크](https://developer.android.com/jetpack/compose/navigation#deeplinks)를 추가하여 특정 URL, 동작, MIME 유형을 컴포저블에 연결할 수 있음
- Navigation Compose는 [암시적 딥 링크](https://developer.android.com/guide/navigation/navigation-deep-link#implicit)를 지원
  - 암시적 딥 링크가 호출되면 안드로이드는 앱에 상응하는 대상을 열 수 있음
- `data` 태그를 사용해서 `scheme`(rally- 앱 이름) 및 `host`(single_account - 컴포저블 경로)를 추가해서 딥 링크 정의
  - 예시) rally://single_account
<br>

__딥 링크 트리거 및 확인하기__<br>
```kotlin
composable(
    route = SingleAccount.routeWithArgs,
    // ...
    deepLinks = listOf(navDeepLink {
        uriPattern = "rally://${SingleAccount.route}/{${SingleAccount.accountTypeArg}}"
    })
)
```
<br>

__adb를 사용해서 딥 링크 테스트하기__<br>
- 연결된 에뮬레이터나 기기에 Rally 새로 설치하고 명령줄을 연 다음 다음 명령어 입력
  ```
  adb shell am start -d "rally://single_account/Checking" -a android.intent.action.VIEW
  ```
<br>

### [RallyNavHost에 NavHost 추출](https://developer.android.com/codelabs/jetpack-compose-navigation?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fjetpack-compose-for-android-developers-3%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-navigation#8)

<br>

### Compose Navigation 테스트
__NavigationTest 클래스 준비하기__<br>
1. `/app/src/androidTest/java/com/example/compose/rally` 디렉터리 만들기
2. `NavigationTest` 클래스 생성
3. ```kotlin
    import androidx.compose.ui.test.junit4.createComposeRule
    import org.junit.Rule

    class NavigationTest {
      @get:Rule
      val composeTestRule = createComposeRule()
    }
    ```
<br>

__첫 번째 테스트 작성하기__<br>
```kotlin
@Test
fun rallyNavHost() {
  composeTestRule.setContent {
    navController = TestNavHostController(LocalContext.current)
    navigatorProvider.addNavigator(
      ComposeNavigator()
    )
    RallyNavHost(navController = navController)
  }
  fail()
}
```
```kotlin
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController

    @Test
    fun rallyNavHost_verifyOverviewStartDestination() {
        composeTestRule.setContent {
            navController =
                TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            RallyNavHost(navController = navController)
        }

        composeTestRule
            .onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()
    }
}
```
- 불필요한 반복을 방지하고 테스트를 간결하게 유지하기 위해 초기화를 주석처리된 @Before 함수로 추출함
```kotlin
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupRallyNavHost() {
        composeTestRule.setContent {
            navController =
                TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            RallyNavHost(navController = navController)
        }
    }

    @Test
    fun rallyNavHost_verifyOverviewStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()
    }
}
```
