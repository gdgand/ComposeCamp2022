# Jetpack Compose Basic

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