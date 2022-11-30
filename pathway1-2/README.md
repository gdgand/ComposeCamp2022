# BasicStateCodeLab

## Compose State, Event
- 상태에 따라 특정 시점에 UI에 표시되는 항목이 결정됩니다.
- Android 앱에서는 이벤트에 대한 응답으로 상태가 업데이트됩니다.
- 즉 앱 상태로 UI에 표시할 항목에 관한 설명을 제공하고, 이벤트라는 메커니즘을 통해 상태가 변경되고 UI도 변경됩니다.

<img width="646" alt="image" src="https://user-images.githubusercontent.com/34837583/204146220-492c9b83-d2d0-4314-a072-a16ce08f9685.png">

<br>

## Composition
Compose 앱은 Composable 함수를 호출하여 데이터를 UI로 변환합니다. 

- Composition : Jetpack Compose에서 Composable 함수를 호출하여 빌드된 UI에 대한 설명, UI를 기술하는 컴포저블의 트리 구조
- Initial Composition : 처음 Composable 실행 시에 컴포지션 생성
- ReComposition : 데이터 변경 시 Composition을 업데이트하기 위해 Composable 함수를 다시 실행하는 것

이렇게 하기 위해서는 Compose가 ``추적할 상태``를 알아야 합니다. 그래야 업데이트 시 리컴포지션을 예약할 수 있습니다.

<br>

### State, MutableState, remember
- Compose에서는 State, MutableState 유형을 사용하여 Compose에서 상태 관찰할 수 있도록 함.
- 하지만 State, MutableState만 다시 사용해서는 Recomposition 할 뿐 이전 상태를 유지하기에 똑같은 UI가 나타나게 됩니다. 따라서 리컴포지션 간에 상태를 유지할 방법이 필요합니다.
- 이를 위해 Composable 한 remember 를 사용할 수 있습니다. remeber로 계산된 값을 초기 컴포지션 중에 컴포지션에 저장되고 저장된 값을 리컴포지션 간에 유지됩니다.

<br>

## State 기반 UI
<img width="696" alt="image" src="https://user-images.githubusercontent.com/34837583/204148424-e107eb0e-296b-4a42-b0f8-c980bd87c98d.png">

- 컴포지션 내 컴포저블의 수명 주기를 나타내는 그림으로 컴포저블은 컴포지션을 시작하고 0회 이상 Recomposition이 되고 추후에 컴포지션을 종료합니다.
- 상태가 변경될 때 UI 구성요소를 업데이트하게 되고, 컴포저블이 결국 컴포지션을 시작하거나 종료할 수 있습니다. 이런 선언형 UI 프레임워크를 통해 직접 업데이트 하는 것보다 오류 발생 가능성을 현저히 낮출 수 있게 됩니다.

<br>

## Composition Remember
- remember는 컴포지션에 객체를 저장하고, remember가 호출되는 소스 위치가 리컴포지션 중에 다시 호출되지 않으면 객체를 삭제합니다.
- remeber는 Configuration Change 발생 시에는 상태가 유지되지 않습니다. 이를 위해서는 rememberSaveable을 사용해야 합니다.
- rememberSaveable은 Bundle에 저장할 수 있는 모든 값을 자동으로 저장합니다. 다른 값의 경우에는 맞춤 Saver 객체를 전달해야 합니다.

<br>

## State hoisting
- remeber를 사용하여 객체 저장하는 컴포저블을 Stateful 컴포저블로 내부 상태로 인해 재사용성이 적고 테스트하기가 어려운 경향이 있습니다.
- 상태를 보유하지 않는 컴포저블을 Stateless 컴포저블이라고 합니다.
- Compose에서는 State hoisting은 컴포저블을 Stateless로 만들기 위해 상태를 호출자로 옮기는 패턴입니다. State hoisting을 위한 일반적인 패턴은 상태 변수를 두 개의 매개변수로 바꾸는 것입니다.
    - value : T - 값
    - onValueChanage: (T) -> Unit - 값을 변경하도록 요청하는 이벤트

<img width="280" alt="image" src="https://user-images.githubusercontent.com/34837583/204148980-7ce6dbc1-3cf2-490f-b81b-c40bdd3d6216.png">

- 위  그림처럼 상태는 내려가고 이벤트는 올라가는 패턴을 Undirectional Data Flow(UDF) 라고 합니다.

<br>

### State hoisting의 장점
- 단일 소스 저장소 : 소스 저장소가 하나여서 버그 방지에 도움
- 공유 가능함 : 여러 컴포저블과 공유 가능
- 가로채기 가능 : 상태를 통해 컴포저블 재구성
- 분리됨 : Stateless함수의 상태는 어디에든 저장할 수 있음.

<br>

## 관찰 가능한 MutableList
- LazyColumn, LazyRow 사용 시 리스트 자체가 변경될 때 단순히 ArrayList, mutableListOf를 사용한다고 Recomposition이 발생하지 않습니다.
- Compose에서 관찰할 수 있는 MutableList 인스턴스를 만들어야 하고 toMutableStateList()와 mutableStateOf 함수를 사용하는 것입니다.

```kotlin
// Don't do this!
val list = remember { mutableStateListOf<WellnessTask>() }
list.addAll(getWellnessTasks())

// Do this instead
val list = remember{
    mutableStateListOf<WellnessTask>().apply { addAll(getWellnessTasks()) }
}
```
- 위 코드에서처럼 기존 리스트에 toMutableStateList()를 사용하는 것이 좋고 mutableStateOf 사용 시에 초깃값을 사용하는것이 성능 최적화가 됩니다.

<br>

## ViewModel 사용
- 이전에 remember은 Bundle 객체에 저장할 수 있는 데이터만 가능하다고 하였습니다. 다른 데이터 형식을 저장하기 위해서는 맞춤 Saver를 제공해야 합니다.
- 하지만 직렬화, 역직렬화가 필요한 데이터 구조를 저장하는데서는 rememberX를 사용해서는 안됩니다. Activity의 onSavedInstanceState를 사용할 때도 유사한 규칙으로 정말 필요한 간단한 형식의 데이터만 저장해야 합니다.
- ViewModel은 UI 상태와 앱의 다른 레이어에 있는 비즈니스 로직에 대한 액세스 권한을 제공합니다. 또한 ViewModel은 구성 변경 후에도 유지되므로 컴포지션보다 전체 기간이 더 깁니다.

<br><br><br>

# ThemingCodelab

## Material Theming
- Jetpack Compose는 디지털 인터페이스를 ㅁ나들기 위한 Material Design을 제공
- Material Design 컴포넌트(버튼, 카드, 스위치 등)은 Material Theming 기반으로 빌드되어 설정
- Material Theming은 색상, 서체, 도형 속성으로 구성

<br>

## MaterialTheme
- Jetpack Compose에서 테마 설정을 구현하는 핵심 여소는 MaterialTheme 컴포저블
- 이 컴포저블을 Compose 계층 구조에 배치하면 그 안 모든 구성 요소는 자동으로 색상, 서체, 도형 맞춤 지정
```kotlin
@Composable
fun MaterialTheme(
    colors: Colors,
    typography: Typography,
    shapes: Shapes,
    content: @Composable () -> Unit
) { ...
```
- colors, typography, shapes 속성을 알기 위해서는 MaterialTheme object에 접근하여 프로퍼티로 가져옴
- 커스텀 테마를 만들어 전체적으로 적용을 하려고 한다면 MaterialTheme을 래핑하고 구성하는 컴포저블 구현
- 어두운 테마 지원하기 위해서 Theme 루트 컴포저블에 새로운 매개변수를 추가하고 ``isSystemInDarkTheme()`` 메소드를 통해 기기 쿼리

<br>

## Working with Color
- Raw Colors : Color 객체로 정의 후 사용
- Theme Colors : MaterialTheme.colors로 부터 가져옴, copy 메소드를 통해서 일부만 변경 가능 ex) alpha
- Surface & Content Colors 
    - 모든 구성요소는 일반적으로 color와 contentColor 속성 설정
    - 이를 통해 컴포저블 색상과 동시에 내부의 컴포저블까지 기본 색상 제공
    - ``contentColorFor`` 메소드는 테마 색상에 적절한 ``on``color를 가져옴
```kotlin
// 정의
Surface(
  color: Color = MaterialTheme.colors.surface,
  contentColor: Color = contentColorFor(color),
  ...
)
TopAppBar(
  backgroundColor: Color = MaterialTheme.colors.primarySurface,
  contentColor: Color = contentColorFor(backgroundColor),
  ...
)

// 사용 예시
Surface(color = MaterialTheme.colors.primary) {
  Text(...) // default text color is 'onPrimary'
}
Surface(color = MaterialTheme.colors.error) {
  Icon(...) // default tint is 'onError'
}
```
- 요소의 색상을 설정할 때는 Surface 사용, content 컴포저블까지 색상 설정 가능
- Modifier.background를 활용하여 색상 설정 시 주의 필요 (content 컴포저블 설정 x)
- 콘텐츠를 갖오할 때는 LocalContentAlpha 사용

<br>

## Working with Text
- 구성요소가 직접 텍스트를 표시하지 않는 경우가 있기에 ``ProvideTextStyle`` 컴포저블을 사용하여 현재 TextStyle로 설정
- Theme Text : Color와 마찬가지로 MaterialTheme object에서 가져옴
- 일부 텍스트에 여러 스타일 적용 원할 시 ``AnnotatedString`` 사용

<br>

## Working with Shape
- 모든 Material 구성요소는 기본 매개변수를 사용하여 도형 속성 정의, 필요에 따라 새로운 값으로 설정
- 자체 구성요소 만들 시에 ``Surface, Modifier.clip .. ``을 받아들이는 Modifier 사용하거나 직접 컴포넌트 생성

<br>

## Style
- Compose에서는 Android View 스타일을 별도로 정의하거나 하지 않음. 이미 Compose 구성요소가 재사용 가능하여 해당 컴포저블 사용하면 됨.
```kotlin
@Composable
fun Header(
  text: String,
  modifier: Modifier = Modifier
) {
  Surface(
    color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
    contentColor = MaterialTheme.colors.primary,
    modifier = modifier.semantics { heading() }
  ) {
    Text(
      text = text,
      style = MaterialTheme.typography.subtitle2,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
    )
  }
}
```
- Header는 Text의 변형된 스타일로 필요한 곳 어디서든 사용 가능

<br><br><br>

# MigrationCodelb
- Compose에서 UI 렌더링하려면 Activity/Fragment가 필요
- Android 뷰와 공존하는 하는데에서 Compose UI를 호스팅 하기 위해서는 ComposeView를 사용해야 함.
- ComposeView의 setContent 메소드를 통해 컴포저블 호출

<br>

## ViewModel Migration
- 일반적으로 Activity/Fragment에 ViewModel 인스턴스가 존재함으로 컴포저블 함수에 매개변수로 전달만 하면 됨.
- 매개변수 방식이 아닐 경우에는 컴포저블 함수 내에서 ``viewModel`` 을 사용하여 동일 인스턴스 가져옴

<br>

## LiveData
- 컴포저블에서 LiveData를 관찰하기 위해서는 ``LiveData.observeAsState()`` 함수 사용
- ``LiveData.observeAsState()``는 LiveData로 관찰 시작하고 State 객체를 통해 값을 사용
- LiveData의 값이 변경 시마다 State가 업데이트되어 컴포지션 재구성

<br>

## ViewCompositionStrategy
- Activity/Fragment에서 View가 Detach될 때 컴포지션이 삭제됩니다.
- 따라서, 컴포지션은 Compose UI 유지를 위해 라이프사이클 내에 상태를 저장해야 합니다.
- 또한 View가 Detach되더라도 Compose UI 요소가 유자되도록 해야합니다.
- 이를 위해 ViewCompositionStrategy를 사용하여야 하고 라이프사이클이 파괴될 때까지 유지하는 Strategy로 ViewComppsitionStrategy.DisposeOnViewTreeLifecycleDestroyed 사용하면 됩니다.