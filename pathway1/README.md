# BasicCodeLab

## Composable 함수
- @Composable Annotation
- Function 내부에 다른 @Composable 함수 호출 가능
```kotlin
@Composable
private fun Greeting(name: String){
    Text(text = "Hello $name")
}
```

<br>

## Compose 시작하기
- XML 파일을 사용한 것처럼 동일하게 AndroidManiafest.xml에 명시되어 있는대로 Activity가 진입점으로 유지

```kotlin
class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Greeting("Android")
                }
            }
        }
    }
}
```
- Compose에서는 setContentView가 아닌 setContent를 사용하여 레이아웃 정의, XML을 사용하는 것이 아닌 함수 내에서 Composable 함수 호출
- BasicsCodelabTheme는 Composable한 함수의 스타일 지정

<br>

## Compose Preview
```kotlin
@Preview(showBackground = true, name = "Text preview")
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greeting(name = "Android")
    }
}
```
- 매개변수가 없는 @Composable한 함수에 @Preview 애노테이션을 표시하고 프로젝트 빌드를 하면 Design 탭에서 볼 수 있습니다.
- Preview 애노테이션을 하나 더 추가하여 Dark 모드도 보여줄 수 있습니다.

<br>

## Text Background 적용
```kotlin
@Composable
private fun Greeting(name: String) {
    Surface(color = MaterialTheme.colors.primary) {
        Text (text = "Hello $name!")
    }
}
```
- Surface로 기존의 Text를 래핑하면 배경색상을 다른 색으로 변경할 수 있습니다.
- 여기서는 MaterialTheme.colors.primary로 배경색상을 변경하였고 Material 특징으로 다른 요소에 어울리도록 자동 변경해줍니다.

<br>

## Modifier
```kotlin
@Composable
private fun Greeting(name: String) {
    Surface(color = MaterialTheme.colorScheme.primary) {
        Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
    }
}
```
- 대부분의 Compose UI 요소에는 Modifier 매개변수를 허용합니다. Modifier는 상위 UI 또는 레이아웃 내에서 배치되고 동작하는 방식을 알려줍니다.
- 위 코드는 Text 요소가 Surface 요소에게 패딩 값을 알려줍니다. 이 외에도 정렬, 애니메이션 처리, 배치, 클릭 가능 여부, 스크롤 가능 여부 등 여러가지 Modifier가 있습니다.

<br>

## Composable 재사용
- 레이아웃 UI 내에 많은 구성요소들이 있을 경우 별도의 함수로 관리하면 효과적입니다.
```kotlin
@Composable
private fun MyApp(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Greeting("Android")
    }
}
```
- 이와 같이 배경화면이 지정된 Text를 만드는 Composable 함수를 정의하여 어디서든 사용 가능합니다.
- 이 때, 함수의 첫 번째 매개변수로 Modifier을 전달하는 것이 좋습니다. 외부에서 사용하는 곳에서 레이아웃과 같은 Modifier를 수정할 수도 있기 때문입니다.

<br>

## Column, Row, Box
<img width="592" alt="image" src="https://user-images.githubusercontent.com/34837583/202500378-c100d689-c25c-43c7-9151-27c92ec98e93.png">

```kotlin
@Composable
private fun Greeting(name: String) {
    Surface(color = MaterialTheme.colorScheme.primary) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = "Hello,")
            Text(text = name)
        }
    }
}
```
- Compose 기본 표준 레이아웃 요소는 Column, Row, Box가 존재합니다.
- 여기서 Column에 Modifier를 설정하여 Column이 속한 레이아웃 내에 패딩을 결정하게 됩니다.

<br>

## Compose 상태
- Compose 상태를 나타내기 위한 변수는 보통 Composable 함수에 포함됩니다.
- 하지만 일반적으로 지역변수를 사용하는 방식으로는 상태를 저장할 수 없습니다.
```kotlin
// Don't copy over
@Composable
private fun Greeting(name: String) {
    var expanded = false // Don't do this!

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            ElevatedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }
    }
}
```
- Compose 앱의 가장 큰 특징은 명령형이 아닌 선언형으로 UI 상태를 변경하는 것으로 Compose에서는 Composable 함수를 호출하여 데이터를 UI로 변환합니다.
- 데이터가 변경되면채 Composable 함수를 다시 실행하여 업데이트된 UI를 만듭니다. 이를 ``recomposition`` 이라고 합니다.
- 또한, Compose는 변경된 데이터가 있으면 모든 UI를 다시 그리는 것이 아닌 변경된 데이터에 대해서만 업데이트 합니다.
- 위 코드에서 ``recomposition``이 되지 않는 이유는 Composable 함수가 상태를 옵저빙하고 있지 않기 때문입니다.

<br>

### State & MutableState
- Composable 함수에서 데이터 업데이트를 트리거 받기 위해서는 단순 변수를 사용하면 안되고 State, MutableState를 사용해야 합니다.
- 하지만, 바로 할당할 수는 없으며 리컴포지션간에도 상태를 저장하기 위해 ``remember`` 키워드를 사용하여 상태를 기억해야 합니다.

<br>

```kotlin
@Composable
private fun Greeting(name: String) {

    val expanded = remember { mutableStateOf(false) }

    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
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
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}
```
위와 같이 코드를 작성하면 클릭 이벤트 시에 배경화면의 확장과 ElevateddButton의 텍스트가 변경되는 것을 확인할 수  있습니다.

- Greeting이라는 Composable 함수를 여러번 호출하면 remember는 자체 상태 버전 UI 요소로 관리하기 때문에 문제가 없습니다.
- Greetinng 함수는 expanded의 상태를 구독하고 있다가 업데이트가 발생하면 리컴포지션이 발생하는 것으로 요약할 수 있습니다.

<br>

## Compose 상태 hoisting
- Composable 함수에서 여러 UI 요소에서 공통적으로 읽거나 할 때 가장 상위 요소가 관리하는것이 좋습니다.
- 이런 작업을 hoisting이라고 하며 상위 요소에서 상태를 관리하고 하위 UI 요소에게 콜백메소드를 전달하는 방식으로 구현 가능합니다.

```kotlin
@Composable
fun MyApp(modifier: Modifier = Modifier) {

    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface(modifier) {
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
            modifier = Modifier
                .padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }

}
```
- 위 코드와 같이 MyApp 상위요소에서 shouldShowOnboarding 상태에 따라 다르게 보여주고 있습니다.
- shouldShowOnboarding은 OnboardingScreen 컴포저블 함수에서 버튼 UI의 클릭 리스너에 등록이 되어야 합니다. 이 때, 콜백을 전달해주면 됩니다.

<br>

## LazyColumn
- LazyColumn, LazyRow는 Android의 RecyclerView의 동일하다고 생각하시면 됩니다.
- 이전에 했던 코드처럼  Column,Row로 한 번에 생성을 하게 되면 성능적으로 많이 사용되고 애뮬레이터가 중단될 수도 있습니다.
- RecyclerView와 차이가 있다면, RecyclerView처럼 가려지는 뷰들을 재활용하지 않는다고 합니다. 그래도 인플레이션보다는 Composable 함수가 호출되는게 상대적으로 비용이 적게 들어 성능이 유지된다고 합니다.
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

<br>

## Configuration Change & ShutDown 상태 유지
- remeber 대신 remeberSaveable을 사용하면 됩니다.

<br>

## Compose UI에 Animation 적용
- Compose에서는 이미 다양한 Animation API를 지원합니다.
- 아래 예시에서는 animateDpAsState를 사용하였고 animationSpec을 사용하여 커스텀하게 변경할 수도 있습니다.
```kotlin
@Composable
private fun Greeting(name: String) {

    var expanded by remember { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Surface(
        color = MaterialTheme.colorScheme.primary,
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
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show less" else "Show more")
            }

        }
    }
}
```

<br>

## Compose Theme
- BasicCodelabTheme에서는 내부적으로 MaterialTheme 사용, MaterialTheme는 Material 디자인 사양에 맞게 스타일을 지정할 수 있는 Composable 함수입니다.
- 모든 Composable 함수에는 MaterialTheme의 colorSheme, typography, shape에 접근할 수 있습니다.
```kotlin
       Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
```
<br>

### Color
```kotlin
val Navy = Color(0xFF073042)
val Blue = Color(0xFF4285F4)
val LightBlue = Color(0xFFD7EFFE)
val Chartreuse = Color(0xFFEFF7CF)

private val LightColorScheme = lightColorScheme(
    surface = Blue,
    onSurface = Color.White,
    primary = LightBlue,
    onPrimary = Navy
)
```
-  ui/theme 폴더에 Color 파일 또는 Theme 파일 존재
- MaterialTheme3 사용, API 31버전 이상에서 테마 변경 UI 확인 가능

<br><br><br>

# BasicLayoutCodeLab

## Search Bar
- modifier는 매개변수로 받아서 TextField에 전달하는 것이 Compose 권장 사항
- 컴포저블 호출자가 디자인과 분위기 수정할 수 있어 유연성 높아지고 재사용 가능
```kotlin
@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
               imageVector = Icons.Default.Search,
               contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        placeholder = {
            Text(stringResource(id = R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
    )
}
```
- TextField에 다른 컴포저블을 받을 수 있는 leadingIcon 존재

<br>

## Alignment
- Column
    - Start, CenterHorizontally, End
- Row
    - Top, CenterVertically, Bottom
- Box
    - TopStart, TopCenter, TopEnd, CenterStart, Center, CenterEnd, BottomStart, BottomCenter, BottomEnd

<br>

## Slot API
- 개발자가 원하는 대로 채울 수 있도록 UI에 빈 공간 남겨둠.
- 유연한 UI 개발 가능
- Scaffold : Material Design을 구현하는 앱을 위한 구성 가능한 최상위 수준 컴포저블 제공

<br>

## Scroll
- 일반적으로 LazyRow, LazyHorizontalGrid와 같은 Lazy 레이아웃은 자동 스크롤 동작 추가
- 하지만, Lazy 레이아웃은 데이터가 많거나 로드할 데이터가 많아서 동시에 보여질 경우 성능 이슈 시에 사용
- 데이터가 많지 않을 경우는 Column, Row 사용하고 스크롤 동작 추가
- verticalScroll 또는 horizontalScroll 수정자 사용, 스크롤 현재 상태를 포함하며 외부에서 스크롤 상태 수정하는 데 사용되는 ScrollState 필요