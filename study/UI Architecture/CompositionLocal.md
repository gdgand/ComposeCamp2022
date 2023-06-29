# CompositionLocal을 이용한 지역 범위 데이터

`CompositionLocal`은 Composition을 통해 데이터를 암시적으로 전달하는 도구입니다.

## CompositionLocal 소개
Compose에서는 데이터가 각각의 Composable의 파라미터로써 UI 트리를 통해 아래로 흐릅니다.  
이로 인해 Composable의 의존성이 명확해지지만, 'Color', 'Style'과 같이 빈번하고 넓게 사용되는 데이터에 대해서 계속해서 파라미터를 지정하는 것이 번거로울 수 있습니다.

```kotlin
@Composable
fun MyApp() {
    // 테마 정보는 일반적으로 애플리케이션의 루트 부근에서 정의
    val colors = colors()
}

// 계층 구조에서 깊은 곳에 있는 Composable
@Composable
fun SomeTextLabel(labelText: String) {
    Text(
        text = labelText,
        color = colors.onPrimary // ← 여기서 Color 접근
    )
}
```
대부분의 Composable에 명시적인 파라미터 종속성으로 색상을 전달 할 필요가 없도록 지원하기 위해, Compose는 `CompositionLocal`을 제공합니다.

`CompositionLocal`은 Tree Scope의 명명된 객체를 생성하여 UI 트리를 통해 데이터가 흐르도록 암시적인 방법을 사용할 수 있습니다.

`CompositionLocal`은 일반적으로 UI 트리의 특정 노드에서 값으로 제공됩니다.  
그 값은 Composable에서 `CompositionLocal`을 파라미터로 선언하지 않아도 하위의 Composable에서 사용할 수 있습니다.

`CompositionLocal`은 Material Theme을 내부적으로 사용하는 것입니다.  
`MaterialTheme`은 세 개의 `colors`, `shapes`, `typography` Property를 제공하여 
Composition의 하위 부분에서 색상(colors), 타이포그래피(typography), 형태(shapes) 등의 각 속성에 접근할 수 있습니다.

```kotlin
@Composable
fun MyApp() {
    MaterialTheme {
        // 색상, 타이포그래피, 형태에 대한 새로운 값이 
        // MaterialTheme의 content 람다에서 사용 가능

        // ... content here ...
    }
}

@Composable
fun SomeTextLabel(labelText: String) {
    Text(
        text = labelText,
        // `primary`는 MaterialTheme의 LocalColors CompositionLocal에서 얻음
        color = MaterialTheme.colors.primary
    )
}
```

#### CompositionLocal 범위 지정
- `CompositionLocal`의 인스턴스는 Composition의 일부 범위에 지정됩니다.
- `CompositionLocal`은 범위 지정(scoping) 특성으로 UI 트리의 다른 레벨에서 서로 다른 값을 제공할 수 있습니다.

#### CompositionLocal 'current' 값
- `CompositionLocal`의 `current` 값은 해당 부분의 Compose에서 가장 가까운 조상(composable)이 제공하는 값을 참조합니다.  
  하지만, 부모 composable과 그 자식들이 같은 `CompositionLocal` 인스턴스를 가지고 있지만 각각 다른 값을 가지고 있다면, 자식 composable은 부모의 값을 참조하지 않고 자신의 값을 참조합니다.

#### CompositionLocalProvider
- `CompositionLocalProvider`는 `CompositionLocal`에 새로운 값을 제공하는 역할을 합니다.
- `provides`는 중위 함수로서 `CompositionLocal`의 키와 값을 연결하며, 이를 통해 `CompositionLocal`에 특정 값을 제공할 수 있습니다.
- 새로운 값이 `CompositionLocal`에 제공되면, Compose는 `CompositionLocal`을 읽는 Composable들을 자동으로 재구성합니다.

아래 예제에서는 `CompositionLocalProvider`가 Composition의 다른 부분에 대해 다른 값을 제공하는 데 사용됩니다.

```kotlin
@Composable
fun CompositionLocalExample() {
    MaterialTheme { // MaterialTheme은 기본적으로 ContentAlpha.high 설정
        Column {
            Text("Uses MaterialTheme's provided alpha")
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("Medium value provided for LocalContentAlpha")
                Text("This Text also uses the medium value")
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                    DescendantExample()
                }
            }
        }
    }
}

@Composable
fun DescendantExample() {
    // CompositionLocalProviders는 composable 함수를 통해 동작
    Text("This Text uses the disabled alpha now")
}
```

<img src=../../resource/compositionlocal-alpha.png width="50%" height="auto">

> `CompositionLocal` 객체나 상수는 일반적으로 `Local`이라는 접두어로 시작하여 IDE에서 자동완성을 통해 더 쉽게 발견할 수 있도록 합니다.

## 고유한 CompositionLocal 만들기
`CompositionLocal`은 Composition을 통해 데이터를 암시적으로 전달하는데 사용됩니다.

`CompositionLocal`을 사용하는 주요 케이스 중 하나는 파라미터가 여러 계층을 관통(cross-cutting)하고 중간 구현 계층이 해당 파라미터를 인식할 필요가 없는 경우입니다.  
이는 중간 계층이 해당 파라미터를 인식하게 되면, 그 composable의 활용성이 제한될 수 있기 때문입니다.

예를 들어, 안드로이드에서 권한 요청을 처리하는 작업은 내부적으로 `CompositionLocal`을 이용합니다.   
여기서 'Media Picker Composable'은 디바이스에서 권한으로 보호된 컨텐츠에 액세스하는 새로운 기능을 추가할 수 있습니다.
이는 Media Picker Composable이 추가 기능을 구현할 때 그 기능을 사용하는 다른 코드에서 API 변경을 인지하거나, 추가된 컨텍스트에 대해 알 필요가 없습니다.

이러한 상황에서는 `CompositionLocal`을 사용하여 특정 composable의 내부 구현 세부사항을 숨기고 동시에 새로운 기능을 추가할 수 있습니다. 
그 결과, composable의 API는 동일하게 유지되지만 그 아래의 구현은 `CompositionLocal`에 의해 암시적으로 제어됩니다. 
이렇게 하면 composable이 가지고 있는 기능을 확장하거나 변경하는 데 필요한 유연성을 제공하게 됩니다.

### CompositionLocal 단점
`CompositionLocal`이 항상 최선의 해결책은 아닙니다. 
`CompositionLocal`의 과도한 사용은 여러 단점을 가지고 있기에 사용에 주의해야 합니다.

#### Composable의 동작을 이해하기 어려움
`CompositionLocal`은 암시적인 의존성을 만들어내기 때문에, 이를 사용하는 composable 함수를 호출하는 개발자들은 모든 `CompositionLocal`에 대한 값을 만족시키는 것을 확인해야 합니다.
즉, `CompositionLocal`에 값이 제공되어야 하는데, 그렇지 않으면 런타임 에러가 발생할 수 있습니다.

#### 의존성에 대한 명확한 근거가 없을 수 있음
`CompositionLocal`은 Composable의 어느 부분에서든 변경될 수 있기 때문에, 이 의존성에 대한 명확한 근거가 없을 수 있습니다. 
이로 인해 문제가 발생했을 때 디버깅이 어려울 수 있습니다.

예를 들어, 문제가 발생한 Composable이 특정 `CompositionLocal`의 `current` 값을 사용하고 있다고 가정해보겠습니다. 
이 경우 문제를 해결하기 위해서는 `current` 값이 어디에서 제공되었는지 확인해야 합니다. 
이는 Composition의 계층을 거슬러 올라가며 확인해야 하므로 디버깅이 어려울 수 있습니다.

---

### CompositionLocal 사용시기 결정

아래의 조건이 충족될 때 `CompositionLocal`은 요구 사항에 적절한 해결책이 될 수 있습니다.

1. `CompositionLocal`은 적절한 기본값을 가져야 합니다.
기본값이 없다면, 개발자가 `CompositionLocal`에 대한 값을 제공하지 않는 상황을 가능한 피할 수 있도록 해야 합니다.
기본값을 제공하지 않으면, 해당 `CompositionLocal`을 사용하는 composable을 테스트하거나 미리보기 할 때 항상 명시적으로 제공해야 하므로 문제와 혼란을 야기할 수 있습니다.

2. `CompositionLocal`은 트리 범위 또는 하위 계층 범위의 개념에 사용되어야 합니다. 
즉, `CompositionLocal`은 특정 몇 개의 항목에 의해 사용되는 것이 아닌 모든 후손에 의해 잠재적으로 사용될 수 있을 때 의미가 있습니다.

`CompositionLocal`의 나쁜 사용 예로는, 특정 화면의 `ViewModel`을 보유하도록 만드는 것이 있습니다.   
이 경우 해당 화면의 모든 composable들이 `ViewModel`에 대한 참조를 얻어 어떤 로직을 수행하게 되지만, 
이는 모든 composable이 `ViewModel`에 대해 알 필요가 없다는 점 때문에 좋지 않은 방법입니다.

올바른 접근법은, [상태가 아래로 흐르고 이벤트가 위로 흐르는 패턴](../용어.md#단방향-데이터-흐름)을 따라 composable에 필요한 정보만 전달하는 것입니다. 
이 방식은 composable을 더욱 재사용하기 쉽게 만들고 테스트를 용이하게 합니다.

---

### CompositionLocal 생성하기

| 생성 API                     | 설명                                                                                                                                                                                             | 
|----------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `compositionLocalOf`       | 재구성(recomposition) 중에 제공된 값이 변경되면, **`current` 값을 읽는 `content`만** 무효화 합니다.                                                                                                                     |
| `staticCompositionLocalOf` | `compositionLocalOf`와 달리, `staticCompositionLocalOf`의 읽기는 Compose에 의해 추적되지 않습니다. </br> 값이 변경되면 `CompositionLocal`이 제공된 `content` 람다의 전체가 재구성되고, Composition에서 `current` 값을 읽는 위치만 재구성되지 않습니다.  |

**`CompositionLocal`에 제공된 값이 거의 변하지 않거나 절대로 변경되지 않을 경우, 성능의 이점을 얻기 위해 `staticCompositionLocalOf`를 사용하는것이 좋습니다.**

예를 들어, 다음과 같이 그림자에 대한 값을 UI 트리 전체에 전파되어야 할때에 다음과 같이 `CompositionLocal`을 사용합니다. 
`CompositionLocal` 값은 시스템 테마에 따라 조건적으로 유도되므로, `compositionLocalOf`를 사용합니다.

```kotlin
// LocalElevations.kt 파일

data class Elevations(val card: Dp = 0.dp, val default: Dp = 0.dp)

// 기본값을 가진 CompositionLocal 글로벌 객체 정의
// 이 인스턴스는 앱 내의 모든 composables에서 접근할 수 있음
val LocalElevations = compositionLocalOf { Elevations() }
```

위의 코드에서 `Elevations`은 `card`와 `default`라는 두 가지 상태를 가지고 있으며, 각각의 기본값은 `0.dp`입니다.   
`LocalElevations`는 이 `Elevations`를 기본값으로 가지는 `CompositionLocal`이며, 앱의 어느 곳에서나 접근하여 사용할 수 있습니다.   
`compositionLocalOf`를 사용하므로, 이 값이 변경되면 해당 `CompositionLocal`을 참조하는 composable 함수들이 재구성됩니다.

### CompositionLocal으로 값 제공

`CompositionLocalProvider` Composable은 주어진 계층에 대해 `CompositionLocal` 인스턴스에 값을 연결합니다.
`CompositionLocal` 키를 `value`에 연결하는 `provides`를 사용하여 새 값을 제공할 수 있습니다.

```kotlin
class MyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // 시스템 Theme에 따라 elevation 계산
            val elevations = if (isSystemInDarkTheme()) {
                Elevations(card = 1.dp, default = 1.dp)
            } else {
                Elevations(card = 0.dp, default = 0.dp)
            }

            // LocalElevation의 값으로 elevations 연결
            CompositionLocalProvider(LocalElevations provides elevations) {
                // Composable 작성...
                // 이 Composition의 일부(즉, 내부의 모든 Composable)는 
                // LocalElevations.current에 접근할 때 `elevations` 인스턴스를 확인할 수 있음
            }
        }
    }
}
```

### CompositionLocal 사용
`CompositionLocal.current`은 `CompositionLocal`에 값을 제공하는 가장 가까운 `CompositionLocalProvider`가 제공한 값을 반환합니다.

```kotlin
@Composable
fun SomeComposable() {
    Card(elevation = LocalElevations.current.card) {
        // Content
    }
}
```

---

## CompositionLocal 대안

`CompositionLocal`은 일부 사용 사례에 대해 과도한 해결책이 될 수 있습니다.   
[CompositionLocal 사용 시기 결정](#compositionlocal-사용시기-결정)에 명시된 기준을 충족하지 않는다면, 다른 해결책이 더 적합할 가능성이 큽니다.

### 명시적인 파라미터 전달

composable의 종속성에 대해 명확하게 표현하는 것은 좋은 습관이며, 분리와 재사용을 위해 각 Composable은 최소한의 정보만을 가지고 있어야 합니다.

### 제어 역전

composable에 불필요한 종속성 전달을 피하는 또 다른 방법은 제어 역전을 하는 방법입니다.
 
어떤 로직을 실행하기 위한 종속성을 하위 Composable에게 전달하지 않고, 상위 Composable이 로직을 실행하도록 하는 제어 역전 원칙을 사용하세요.

```kotlin
@Composable
fun MyComposable(myViewModel: MyViewModel = viewModel()) {
    ReusableLoadDataButton(
        onLoadClick = { myViewModel.loadData() }
    )
}

@Composable
fun ReusableLoadDataButton(onLoadClick: () -> Unit) {
    Button(onClick = onLoadClick) {
        Text("Load data")
    }
}
```

이러한 접근법은 하위 Composable을 상위 Composable들로부터 분리하는 것으로, 일부 사용 사례에 더 적합할 수 있습니다. 
상위 composable은 더 유연한 하위 composable을 가지기 위해 더 복잡해지는 경향이 있습니다.

유사하게, `@Composable`의 `content lambda`도 같은 이점을 얻기 위해 다음과 같은 방식으로 사용될 수 있습니다.

```kotlin
@Composable
fun MyComposable(myViewModel: MyViewModel = viewModel()) {
    // ...
    ReusablePartOfTheScreen(
        content = {
            Button(
              onClick = { myViewModel.loadData() }
            ) {
                Text("Confirm")
            }
        }
    )
}

@Composable
fun ReusablePartOfTheScreen(content: @Composable () -> Unit) {
    Column {
        // ...
        content()
    }
}
```