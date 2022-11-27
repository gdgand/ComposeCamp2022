# Jetpack Compose 테마 설정

### [Material Theming](https://m2.material.io/design/material-theming/overview.html#material-theming)
- 색상
  - 기본 색상은 주요 브랜드 색상
  - 보조 색상은 강조 표시에 사용
  - 배경, 표면 색상은 앱의 '표면''에 개념적으로 존재하는 구성요소를 보유한 컨테이너에 사용
  - 'on' 색상은 이름이 지정된 색상 중 하나 위에 있는 콘텐츠에 사용됨
    - 예를 들어 '표면' 색상 컨테이너의 텍스트는 'on surface'에 색상이 지정되어야 함
  - [Material 색상 도구](https://m2.material.io/resources/color/#!/?view.left=0&view.right=0)를 사용해 쉽게 색상을 선택하여 색상 팔레트를 만들 수 있음
- 서체
  ![image](https://user-images.githubusercontent.com/101886039/204137753-0f080b1f-6cdf-49d5-99b7-fe1fadd0927a.png)<br>
  - 앱 내에서 일관성을 높이기 위해 서체 스케일 사용
  - [유형 스케일 생성기 도구](https://m2.material.io/design/typography/the-type-system.html#type-scale)
- 도형
  - Material은 도형을 체계적으로 사용해 브랜드를 전달할 수 있도록 지원
  - 소형, 중형, 대형 구성요소라는 3가지 카테고리 정의
  - 모서리스타일과 크기를 맞춤 설정하여 도형을 정의할 수 있음
  - [도형 테마에 관한 구성요소의 전체 매핑](https://m2.material.io/design/shape/applying-shape-to-ui.html#shape-scheme)
  - [도형 맞춤설정 도구](https://m2.material.io/design/shape/about-shape.html#shape-customization-tool)

<br>

### 테마 정의
#### 1. MaterialTheme
- 스타일을 중앙 집중화하려면 `MaterialTheme`를 래핑하고 구성하는 자체 컴포저블을 만드는 것이 좋음
  - 테마 맞춤설정을 한곳에서 지정하고 여러 화면 또는 `@Preview` 등 여러 위치에서 재사용 가능
  - 필요하다면 여러 테마 컴포저블을 만들 수 있음
  ```kotlin
  @Composable
  fun JetnewsTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
  }
  ```
  사용 시<br>
  ```kotlin
  -  MaterialTheme {
  +  JetnewsTheme {
      ...
  ```

#### 2. 색상
- `Color` 클래스를 사용해 Compose의 색상 정의
- 색상을 `ULong`으로 또는 별도의 색상 채널로 지정할 수 있는 생성자가 여러 개 있음
- `Color.kt` 파일
  ```kotlin
  import androidx.compose.ui.graphics.Color

  val Red700 = Color(0xffdd0d3c)
  val Red800 = Color(0xffd00036)
  val Red900 = Color(0xffc20029)
  ```
  사용하고자 하는 파일
  ```kotlin
  private val LightColors = lightColors(
    primary = Red700,
    primaryVariant = Red900,
    onPrimary = Color.White,
    secondary = Red700,
    secondaryVariant = Red900,
    onSecondary = Color.White,
    error = Red800
  )

  @Composable
  fun JetnewsTheme(content: @Composable () -> Unit){
    MaterialTheme(
      colors = LightColors,
      content = content
    )
  }
  ```
  ![image](https://user-images.githubusercontent.com/101886039/204139653-d7428c22-17ed-40ab-a4f7-e909218a49e6.png)

#### 3. 서체
- `TextStyle` 객체 정의해서 일부 텍스트의 스타일을 지정하는 데 필요한 정보를 정의할 수 았음
  ```kotlin
  private val Montserrat = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_medium, FontWeight.W500),
    Font(R.font.montserrat_semibold, FontWeight.W600)
  )

  private val Domine = FontFamily(
    Font(R.font.domine_regular),
    Font(R.font.domine_bold, FontWeight.Bold)
  )
  ...
  val JetnewsTypography = Typography(
    h4 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),
    ...
  )  
  ```
  ```kotlin
  @Composable
  fun JetnewsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
      colors = LightColors,
  +   typography = JetnewsTypography,
      content = content
    )
  }
  ```
  ![image](https://user-images.githubusercontent.com/101886039/204140179-69bd00dd-5078-49aa-953e-1f3000f0f989.png)
  <br>

#### 4. 도형
- `RoundedCornerShape`, `CutCornerShape` 클래스를 사용해 도형 테마 정의 가능
  ```kotlin
  val JetnewsShapes = Shapes(
    small = CutCornerShape(topStart = 8.dp),
    medium = CutCornerShape(topStart = 24.dp),
    large = RoundedCornerShape(8.dp)
  )
  ```
  ```kotlin
  @Composable
  fun JetnewsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
      colors = LightColors,
      typography = JetnewsTypography,
  +   shapes = JetnewsShapes,
      content = content
    )
  }
  ```
  ![image](https://user-images.githubusercontent.com/101886039/204140673-44f3fa94-7654-451a-943b-2c5501e491cd.png)
  <br>

#### 5. 어두운 테마
```kotlin
private val DarkColors = darkColors(
    primary = Red300,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = Red300,
    onSecondary = Color.Black,
    error = Red200
)

@Composable
fun JetnewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    MaterialTheme(
        colors = if(darkTheme) DarkColors else LightColors,
        typography = JetnewsTypography,
        shapes = JetnewsShapes,
        content = content
    )
}
```
![image](https://user-images.githubusercontent.com/101886039/204141381-7ffa58ed-dc01-41b5-8556-a2ecd54718c3.png)

<br>

### 색상 사용
#### 1. 원색
- Compose는 `Color` 클래스를 제공함. 이름 로컬에서 만들고 `object` 등에 보관도 가능
  ```kotlin
  Surface(color = Color.LightGray) {
    Text(
      text = "Hard coded colors don't respond to theme changes :(",
      textColor = Color(0xffff00ff)
    )
  }
  ```
#### 2. 테마 색상
  - 더 유연한 접근 방식은 테마에서 색상을 가져오는 것
  ```kotlin
  Surface(color = MaterialTheme.colors.primary)
  ```
  - 테마의 각 색생이 `Color` 인스턴스이ㅣ므로 `copy` 메소드를 사용해서 색상을 쉽게 파생할 수 있음
  ```kotlin
  val derivedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
  ```
    - 정적 색상을 하드코딩하지 않고 다양한 테마에서 색상을 사용할 수 있음
#### 3. 표면 및 콘텐츠 색상
- 많은 구성요소가 한 쌍의 색상 및 콘텐츠 색상을 허용함
  ```kotlin
  Surface(
    color: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(color),
    ...

  TopAppBar(
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    ...
  ```
  - 컴포저블의 색상 설정 + 콘텐츠의 기본 색상 제공 가능
  - `contentColorFor` 메소드는 테마 색상에 적절한 'on' 색상을 가져옴
    - 예) `primary` 배경을 설정하면 `onPrimary`가 콘텐츠 색상으로 반환됨
    ```kotlin
    Surface(color = MaterialTheme.colors.primary) {
      Text(...) // default text color is 'onPrimary'
    }
    ```
  - `LocalContentColor`, `CompositionLocal`을 사용해서 현재 배경과 대비되는 색상을 가져올 수 있음
    ```kotlin
    BottomNavigationItem(
      unselectedContentColor = LocalContentColor.current ...
    )
    ```
  - 요소의 색상을 설정할 땐 `Surface`를 사용하는 것이 좋음
    - 적절한 콘텐츠 색상 `CompositionLocal`값을 설정하기 때문
    - cf) `Modifier.background`는 적절한 콘텐츠 색상을 설정하지 않기 때문에 직접 호출 시 주의해야함
#### 4. 콘텐츠 알파
- Material Design에서는 다양한 수준의 불투명도를 사용해 다양한 중요도 수준을 전달하도록 권장
- `LocalContentAlpha` 사용
- `ContentAlpha`객체에 의해 모델링된 일부 표준 알파 값(`high`, `medium`, `disabled`)을 지정
  ```kotlin
  CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
    Text(...)
  }
  ```
- 게시물의 계층 구조 명확히 하는데 사용
#### 5. 어두운 테마
- 밝은 테마에서 실행중인지 확인
  ```kotlin
  val isLightTheme = MaterialTheme.colors.isLight
  ```
- Material의 어두운 테마에서는 고도가 높은 표면이 고도 오버레이를 수신함
  - 어두운 색상 팔레트를 사용하면 자동으로 구현됨

<br>

### 텍스트 사용
#### 여러 스타일
- 일부 텍스트에 여러 스타일을 적용해야 하는 경우 마크업을 적용하는 `AnnotatedString` 클래스 사용하면 `SpanStyle`을 텍스트 범위에 추가할 수 있음
  ```kotlin
  val text = buildAnnotatedString {
    append("This is some unstyled text\n")
    withStyle(SpanStyle(color = Color.Red)) {
      append("Red text\n")
    }
    withStyle(SpanStyle(fontSize = 24.sp)) {
      append("Large text")
    }
  }
  ```

<br>

### 도형 사용
- 자체 구성요소를 만들 때 도형(예: `Surface`, `Modifier.clip`, `Modifier.background`, `Modifier.border` 등)을 허용하는 컴포저블이나 `Modifier`를 사용하여 직접 도형을 사용
  ```kotlin
  Image(
    modifier = Modifier.clip(shape = MaterialTheme.shapes.small),
  )

<br>

### '스타일' 구성요소
- Compose는 Android 뷰 스타일이나 CSS 스타일과 같이 구성요소의 스타일을 추출하는 명시적인 방법을 제공하지 않음
- 따라서 맞춤 설정된 구성요소의 자체 라이브러리를 만들어 앱 전체에서 사용
