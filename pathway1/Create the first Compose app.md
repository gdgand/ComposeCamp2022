# Jetpack Compose 기초
### Jetpack Compose란?
- UI 개발을 간소화하기 위해 설계된 최신 툴킷
- 반응형 프로그래밍 모델을 Kotlin 프로그래밍 언어의 간결함 및 사용 편의성과 결합함
- 선언적 접근 방식 
- 데이터를 UI 계층 구조로 변환하는 일련의 함수들을 호출해 UI를 설명함
- 기본 데이터가 변경되면 프레임워크가 이러한 함수를 자동으로 다시 실행해 UI 계층 구조를 업데이트함

### Compose 앱?
- Composable 함수로 구성됨
- `@Composable` 사용해 UI를 업데이트하고 유지관리하기 위해 함수에 특수 지원을 추가하도록 Compose에 알려줌
- Compose를 사용하면 코드를 작은 청크로 구성할 수 있음

__이 코드랩에선 UI 구성요소, 구성 가능한 함수,  컴포저블은 모두 같은 개념__

[CodeLab](https://developer.android.com/codelabs/jetpack-compose-basics?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fjetpack-compose-for-android-developers-1%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-basics#0)

<br>

### 새 Compose 프로젝트 시작
- NewProject > Empty Compose Activity(Material3)
- Compose 최소 API 수준은 21

<br>

### UI 조정
__기본 코드__
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greeting("Android")
    }
}    
```

__Greeting에 배경 색 설정__
```kotlin
@Composable
fun Greeting(name: String) {
    Surface(color = MaterialTheme.colors.primary) {
        Text(text = "Hello $name!")
    }
}
```

- Material은 대부분의 앱에 공통으로 적용되는 적절한 기본값과 패턴을 제공하므로 유연성이 낮은 편임
- Compose의 Material 구성요소는 `androidx.compose.foundation`의 다른 기본 구성요소를 기반으로 빌드되며 이러한 구성요소는 더 많은 유연성이 필요한 경우 앱 구성요소에서 액세스할 수 있음

<br>

### 수정자(Modifier)
- 상위 요소 레이아웃 내에서 UI 요소가 배치되고 표시되고 동작하는 방식을 UI 요소에 알려주는 역할
- padding 수정자는 여백 설정, Modifier.padding()으로 padding  수정자 생성 가능
```kotlin
@Composable
fun Greeting(name: String) {
    Surface(color = MaterialTheme.colors.primary) {
        Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
    }
}
```
![image](https://user-images.githubusercontent.com/101886039/202902800-65796f7f-a850-47e6-b8df-25e5dcb05d95.png)

<br>

### Composable 재사용
- UI에 추가하는 구성요소가 많을수록 중첩 레벨이 더 많아짐
- 함수가 매우 커지면 가독성에 영향을 줄 수 있음
- 재사용할 수 있는 작은 구성요소를 만들면 앱에서 사용하는 UI 요소의 라이브러리를 쉽게 생성 가능
- 각 요소는 화면의 작은 부분을 담당하며 독립적으로 수정 가능
- __함수는 기본적으로 빈 수정자가 할당되는 수정자 매개변수를 포함하는 것이 좋음__
```kotlin
@Composable
private fun MyApp(modifier: Modifier = Modifier){
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.background
    ){
        Greeting("Android")
    }
}
```
- MyApp 컴포저블을 재사용해 코드 중복을 피함
``` kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
        BasicsCodelabTheme {
            MyApp(modifier = Modifier.fillMaxSize())
        }
    }
}
```

<br>

### 열과 행 만들기
- Compose의 기본 표준 레이아웃 요소는 `Column`, `Row`, `Box`
  ![image](https://user-images.githubusercontent.com/101886039/202903117-6f5c820f-8965-4864-8ca7-943001c3922c.png)
- Column 사용 예제
  ```kotlin
  @Composable
  fun Greeting(name: String) {
      Surface(color = MaterialTheme.colors.primary) {
          Column(modifier = Modifier.padding(24.dp)) {
              Text(text = "Hello")
              Text(text = "$name!")
          }
      }
  }
  ```
  ![image](https://user-images.githubusercontent.com/101886039/202903264-d559f18d-2f11-42f0-894d-2b35fec9fb17.png)
- Composable 함수는 Kotlin의 다른 함수처럼 사용 가능
  ```kotlin
  @Composable
  fun MyApp(
      modifier: Modifier = Modifier,
      names: List<String> = listOf("World","Compose")
  ) {
      Column(modifier){
          for(name in names){
              Greeting(name = name)
          }
      }
  }

  @Preview(showBackground = true)
  @Composable
  fun DefaultPreview() {
      BasicsCodelabTheme {
          MyApp()
      }
  } 
  ```
  ![image](https://user-images.githubusercontent.com/101886039/202903735-a992f6ad-7da7-427a-81b0-b0a47b6d7788.png)

- 미리보기 크기 설정
  - `widthDp` 매개변수 사용
    ```kotlin
    @Preview(showBackground = true, widthDp = 320)
    ```
  - `fillMaxWidth`, `padding` 수정자 사용
    ```kotlin
    @Composable
    fun Greeting(name: String) {
        Surface(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                Text(text = "Hello, ")
                Text(text = name)
            }
        }
    }
    ```
    ![image](https://user-images.githubusercontent.com/101886039/202904126-fa161933-9fbd-414e-a5b6-809872e21b7c.png)

<br>

### 버튼 추가
- Button은 Material3 패키지에서 제공하는 Composable
- Composable을 마지막 인수로 사용
- [후행람다](https://kotlinlang.org/docs/lambdas.html#passing-trailing-lambdas)
```kotlin
@Composable
private fun Greeting(name: String) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            ElevatedButton(
                onClick = { /* TODO */ }
            ) {
                Text("Show more", color = Color.Black)
            }
        }
    }
}
```
![image](https://user-images.githubusercontent.com/101886039/202906946-12ca094b-3c70-4103-b2bd-7dfa3146fe50.png)

<br>

### Compose에서의 상태
- Compose 앱은 Composable 함수를 호출해 데이터를 UI로 변환
- 데이터가 변경되면 Compose는 새 데이터로 이러한 함수를 다시 실행해 업데이트된 UI를 만듦 -> 리컴포지션(Recomposition)
- Compose는 데이터가 변경된 구성요소만 다시 구성하고 영향을 받지 않는 구성요소는 다시 구성하지 않고 건너뛰도록 개별 컴포저블에서 필요한 데이터를 확인
- __컴포저블 함수는 자주 실행될 수 있고 순서와 관계없이 실행될 수 있으므로 코드가 실행되는 순서 또는 이 함수가 다시 구성되는 횟수에 의존해서는 안됨__
- 따라서 컴포저블에 내부 상태를 추가하기 위해 `mutableStateOf` 함수 사용
- `MutableState`, `State`는 어떤 값을 보유하고 그 값이 변경될 때마다 UI 업데이트(리컴포지션)를 트리거하는 인터페이스
- 컴포저블 내의 변수에 `mutableStateOf`를 할당하기만은 할 수 없음 -> `remember` 사용해 변경 가능한 상태 기억하도록 함
- `remember`는 리컴포지션을 방지하는데 사용되므로 상태 재설정되지 않음
```kotlin
@Composable
private fun Greeting(name: String) {
    val expanded = remember{ mutableStateOf(false) }
    val extraPadding = if(expanded.value) 48.dp else 0.dp
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            ElevatedButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(
                    if (expanded.value) "Show less" else "Show more",
                    color = Color.Black
                )
            }
        }
    }
}
```
![image](https://im.ezgif.com/tmp/ezgif-1-6da22a5a58.gif)

<br>

### 상태 호이스팅
- 호이스팅
  - 들어올린다, 끌어올린다의 의미
- 상태 호이스팅
  - Composable 함수에서 여러 함수가 읽거나 수정하는 상태는 공통의 상위 항목에 위치해야 하는 프로세스
  - 상태가 중복되지 않고 버그가 발생하는 것을 방지가능
  - 컴포저블 재사용 가능
- Compose에선 UI요소를 숨기지 않음
  - 대신 컴포지션에 UI요소를 추가하지 않기 때문에 Compose가 생성하는 UI 트리에 추가되지 않음
- 상태 값을 상위요소와 공유하는 대신 상태를 호이스팅함
- 이벤트 전달시
  - 아래로 콜백을 전달
```kotlin
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by remember { mutableStateOf(true) }
    Surface(modifier){
        if(shouldShowOnboarding){
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        }
        else {
            Greetings()
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    // = 대신 by 키워드사용하면 .value 키워드 입력할 필요 없게 해주는 속성 위임임
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ){
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}
```
![image](https://im4.ezgif.com/tmp/ezgif-4-3d3a565184.gif)

<br>

### 성능 지연 목록 만들기
- 스크롤 가능한 열 표시 위해 `LazyColumn` 사용
  - 화면에 보이는 항목만 렌더링하기 때문에 성능 향상
  - `LazyColumn`, `LazyRow`는 Android의 RecyclerView와 동일
```kotlin
@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}
```
- __`LazyColumn`은 `RecyclerView`와 같은 하위요소를 재활용하지 않음__
- Composable을 방출하는 것이 View를 인스턴스화하는 것보다 상대적으로 비용이 적게 들기 때문에 `LazyColumn`은 스크롤 할 때 새 컴포저블을 방출해 성능을 유지함
![image](https://im4.ezgif.com/tmp/ezgif-4-a606126bb4.gif)

<br>

### 상태 유지
- `remember` 함수는 컴포저블이 컴포지션에 유지되는 동안에만 작동함
- 기기를 회전하거나, 구성이 변경될 때, 프로세스가 중단될 때 전체 활동이 다시 시작되므로 모든 상태가 손실되는 문제 발생
- `remember` 대신 __`rememberSaveable`__ 사용

<br>

### 목록에 애니메이션 적용
- `animateDpAsState` 컴포저블 사용
  - 애니메이션이 완료될 때 까지 애니메이션에 의해 객체의 value가 계속 업데이트되는 상태 객체를 반환함
  - 유형이 `Dp`인 목표값을 사용함
  - 애니메이션을 맞춤설정할 수 있는 `animationSpec` 매개변수를 선택적으로 사용
```kotlin
@Composable
private fun Greeting(name: String) {
    var expanded by remember{ mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if(expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            ElevatedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(
                    if (expanded) "Show less" else "Show more",
                    color = Color.Black
                )
            }
        }
    }
}
```
- padding 값은 음수가 되지 않도록 해야함
  - 음수가 되면 앱이 다운될 수 있음
- `animate*AsState`를 사용해 만든 애니메이션은 중단될 수 있음
  - 애니메이션 중간에 목표 값이 변경되면 애니메이션을 다시 시작하고 새 값을 가리킴
- 다양한 `spring`용 매개변수, 다양한 사양(`tween`, `repeatable`), 다양한 함수(`animateColorAsState` 또는 [다양한 유형의 Animation API](https://developer.android.com/jetpack/compose/animation))


<br>

### 앱의 스타일 지정 및 테마 설정
- BasicsCodelabTheme는 `MaterialTheme`를 내부적으로 래핑하기 때문에 MyApp은 Theme.kt에 정의된 속성으로 스타일이 지정됨
- `MaterialTheme`의 세 가지 속성
  - `colorScheme`
  - `typography`
  - `shapes`
```kotlin
Text(text = name, style = MaterialTheme.typography.h1)
```
- `copy` 함수를 사용해 미리 정의된 스타일을 수정할 수 있음
```kotlin
Text(text = name, style = MaterialTheme.typography.h3.copy(
    fontWeight = FontWeight.ExtraBold
))
```
![image](https://user-images.githubusercontent.com/101886039/202917085-77a77ae2-1983-488d-87c3-352a879ff645.png)
- 어두운 미리보기 설정
    ```kotlin
    @Preview(
        showBackground = true,
        widthDp = 320,
        uiMode = UI_MODE_NIGHT_YES,
        name = "Dark"
    )
    @Preview(showBackground = true, widthDp = 320)
    @Composable
    fun DefaultPreview() {
        BasicsCodelabTheme {
            Greetings()
        }
    }
    ```
    ![image](https://user-images.githubusercontent.com/101886039/202917274-5f6966e7-27b5-4ef2-9761-4744dfc2bf16.png)
- 미리보기에선 동적 색상 사용하기 때문에 Color.kt에서 색상을 변경해도 미리보기 화면에선 변경되지 않음(Material 3)

```kotlin
// Color.kt
val Navy = Color(0xFF073042)
val Blue = Color(0xFF4285F4)
val LightBlue = Color(0xFFD7EFFE)
val Chartreuse = Color(0xFFEFF7CF)
```
```kotlin
// Theme.kt
private val DarkColorPalette = darkColors(
    primary = LightBlue,
    primaryVariant = Navy,
    secondary = Color.White
)
```
![image](https://user-images.githubusercontent.com/101886039/202917621-207d7d54-3ab6-4b7f-aa7b-cba9b14ca711.png)

<br>

### 설정 완료
- 버튼을 아이콘으로 대체
    ![image](https://user-images.githubusercontent.com/101886039/202917835-b129ec33-0651-49b2-9619-6715cff7eebc.png)

__전체 코드__
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
private fun Greeting(name: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
private fun CardContent(name: String) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                        "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}
```
![image](https://im2.ezgif.com/tmp/ezgif-2-6d3fbe73a8.gif)

<br>

### Quiz
![image](https://user-images.githubusercontent.com/101886039/202919238-b69b4f8d-9ea8-44ee-8760-0851cd2cd823.png)
