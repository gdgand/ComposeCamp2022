# Locally scoped data with CompositionLocal

`CompositionLocal`은 `Composition` 내에서 데이터를 암시적으로 전달하는 도구 입니다.

## Introducing CompositionLocal

> - `CompositionLocal` : UI 요소의 속성 중 '색상', '스타일' 등과 같이 많은 컴포저블에서 사용되는 값을 암시적으로 전달하는 방법
> - `CompositionLocal`의 '현재 값'은 해당 Composable 기준 상위 레벨이 제공하는 가장 까가운 값
> - `CompositionLocalProvider`와 `provides` infix 함수를 통해 `CompositionLocal`에 새로운 값 제공
>   - 새로운 값 업데이트 시 `CompositionLocal`을 읽는 Composable에 한정으로 ReComposition 수행

---

보통 컴포즈에서 '데이터'는 각 컴포저블에 대한 파라미터로 사용되며 UI 트리를 통해 위에서 아래로 전달됩니다.  
이로 인해 컴포저블의 의존성이 명확해지지만, '색상'이나 '타입 스타일'과 같이 많은 컴포저블에서 자주 사용되는 데이터는 이런 방식이 번거로울 수 있습니다.

```kotlin
@Composable
fun MyApp() {
    // Theme 정보는 일반적으로 앱의 Root Composable에서 정의
    val colors = colors()
}

// Composition 계층의 깊은 곳에 있는 Composable
@Composable
fun SomeTextLabel(labelText: String) {
    Text(
        text = labelText,
        color = colors.onPrimary // ← 여기서 Color 접근
    )
}
```

컴포즈는 명시적으로 '색상' 파라미터를 전달 할 필요가 없도록 [UI 트리](#ui-트리)를 통해 데이터가 암시적으로 흐를 수 있도록 `CompositionLocal`을 제공합니다.
`CompositionLocal`는 'tree-scoped named object'를 생성합니다. 

`CompositionLocal` 요소들은 보통 UI 트리의 특정 노드에서 값과 함께 제공됩니다.  
이 값은 컴포저블에 `CompositionLocal`을 파라미터로 선언하지 않고도 하위 컴포저블들이 사용할 수 있습니다.

'Material Theme'의 경우, 실제로 내부적으로 `CompositionLocal`을 사용하고 있습니다.  
`MaterialTheme` 객체는 'color', 'typography', 'shape'와 같은 디자인 요소를 UI 트리 전체에 제공하여,  
컴포저블들이 'Material 디자인 시스템'에 쉽게 접근하고 일관된 스타일을 적용할 수 있도록 합니다.

예를 들어 위 3가지 속성은 각각 `LocalColors`, `LocalTypography`, `LocalShapes` 인스턴스를 통해 접근이 가능합니다.

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

`CompositionLocal` 인스턴스는 `Composition` 내에서 특정 범위를 가지며, 이를 통해 'UI 트리의 각각 다른 레벨'에서 '다른 값을 제공' 할 수 있습니다.   
`CompositionLocal`의 현재 값은 해당 컴포저블 기준, 상위 레벨이 제공하는 가장 가까운 값을 가집니다.

새로운 값을 `CompositionLocal`에 제공하고자 할 땐, `CompositionLocalProvider`를 사용합니다.  
이것은 `provides` infix 함수와 함께 사용되어, 특정 `CompositionLocal`의 키와 값에 연결합니다.

이제, 이 컴포저블 안에서 `CompositionLocal`의 `current` 프로퍼티에 접근하면, 
`CompositionLocalProvider`에 의해 제공된 '새로운 값'을 얻을 수 있습니다.

만약 `Compositionlocal`의 값을 어떤 부분에서 업데이트하게 된다면, 
컴포즈는 `CompositionLocal`을 읽는 컴포저블 대상으로 `ReComposition`을 수행합니다. 

```kotlin
@Composable
fun CompositionLocalExample() {
    MaterialTheme { // MaterialTheme Alpha 기본값 ContentAlpha.high 
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

<img src=../../resource/compositionlocal-alpha.png width="50%">

추가로 아래 예제는 `CompositionLocal`의 `LocalContext`를 통해 현재 `Context`를 가져오는 일반적인 패턴입니다.

```kotlin
@Composable
fun FruitText(fruitSize: Int) {
    // Get `resources` from the current value of LocalContext
    val resources = LocalContext.current.resources
    val fruitText = remember(resources, fruitSize) {
        resources.getQuantityString(R.plurals.fruit_title, fruitSize)
    }
    Text(text = fruitText)
}
```

## Creating your own CompositionLocal

> - `CompositionLocal` UseCase 중 하나로, '중간 계층 Composable'에 데이터 의존성 없이 '하위 계층'으로 데이터 전달 시 사용
>   - 특정 Composable에서만 사용되는 '권한 요청'과 같이 전역적으로 사용되는 작업
> - `CompositionLocal`의 단점
>   - `CompositionLocal` 값을 검증한 뒤 사용해야 하기에 Composable 행동 추론이 어려움
>   - 명확한 의존성이 없어 SSOT가 없을 가능성이 있고 `Composition` 어느 부분에서든 수정이 가능해 디버깅이 어려움
> - `CompositionLocal` 사용을 결정하기 전, 아래 조건을 만족하는 지 확인
>   - `CompositionLocal`이 적절한 'DefaultValue'를 갖는지 확인
>   - `CompositionLocal`이 몇몇 특정 컴포저블이 사용하는 것이 아닌, 모든 하위 컴포저블이 잠재적으로 사용할 수 있는지 확인
>   - 'Bad practice' : `CompositionLocal`을 통해 `ViewModel`을 하위 컴포저블들이 접근할 수 있게 하는 패턴은 나쁜 패턴임
> - `CompositionLocal` 생성을 위한 2가지 API
>   - `compositionLocalOf` : 값 업데이트 시 값을 사용하는 Composable만 'ReComposition' 수행
>   - `staticCompositionLocalOf` : 값을 추적하지 않기에, `CompositionLocal`이 제공한 `content` 람다 전체를 'ReComposition' 수행

---

`CompositionLocal`은 `Composition`을 통해 데이터를 효과적으로 전달하기 위한 도구입니다.  
주로 그 데이터가 트리의 많은 레벨에 걸쳐 공통적으로 필요할 때 사용되며, `Composition`의 여러 계층에 걸쳐 암시적으로 전달되도록 합니다.

`CompositionLocal`을 사용하는 케이스 중 하나로, 파라미터가 '교차 관심사(cross-cutting)'일 때 입니다. 

교차 관심사는 여러 계층에 걸쳐서 고려되어야 할 필요가 있는 사항을 말하며, 
이런 경우에 `CompositionLocal`을 사용하면 중간 계층이 해당 파라미터의 존재를 알 필요 없이 데이터를 하위 계층으로 전달할 수 있습니다.  
이렇게 하면, 중간 계층을 데이터 전달의 매개체로 만들지 않음으로써, 해당 컴포저블의 재사용성과 분리를 유지할 수 있습니다.

예를 들어 '권한 요청' 작업은 앱의 많은 부분에서 공통적으로 필요할 수 있습니다.  
각 컴포저블이나 화면에서 직접 권한을 처리하는 로직을 구현하기보단, `CompositionLocal`을 사용하여 이를 내부적으로 처리할 수 있습니다.

그러나, `CompositionLocal`은 몇 가지 단점이 있기에 남용하는 것은 좋지 않습니다.

1. `CompositionLocal`은 컴포저블의 행동을 추론하기 어렵게 만듭니다.
이들이 암시적 의존성을 생성하기에, `CompositionLocal`을 사용하는 컴포저블을 호출하는 개발자들은 모든 `CompositionLocal`의 값이 만족하는지 검증해야 합니다.

2. 의존성에 대한 명확한 SSOT(Single Source of Truth)가 없을 가능성이 있고, `Composition`의 어느 부분에서든 수정이 가능합니다.
따라서 문제가 발생한 경우 디버깅에 어려움이 있을 수 있습니다.

---

### Deciding whether to use CompositionLocal

`CompositionLocal` 사용 유무 결정하기 전, 특정 조건을 만족하면 좋은 패턴이 될 수 있습니다.

첫째, `CompositionLocal`은 적절한 기본값을 가져야 합니다.  
만약 기본 값이 없다면, 개발자가 실수로 `CompositionLocal` 값을 제공하지 않는 상황이 발생하지 않도록 해야 합니다.  
기본 값이 없을때 테스트나 Preview를 생성 시 항상 해당 값을 명시적으로 제공해야 하는 불편함이 발생할 수 있습니다.

둘째, `CompositionLocal`은 모든 하위 컴포저블이 잠재적으로 사용할 수 있을 때 의미가 있습니다.  
만약 몇몇 특정 컴포저블만이 값을 사용하면, `CompositionLocal`은 적절한 해결책이 아닐 수 있습니다.

'Bad practice' 예시로, 특정 화면의 `ViewModel`을 갖는 `CompositionLocal`을 생성하여,  
그 화면의 모든 컴포저블들이 일부 로직을 수행하기 위해 `ViewModel`을 참조할 수 있게 허용 하는 것입니다.  
이는 특정 UI 트리 아래의 모든 컴포저블들이 `ViewModel`에 대해 알 필요가 없기에 좋지 않은 방법입니다.

'Best practice'는 상태가 아래로 흐르고 이벤트가 위로 흐르는 패턴을 따라 컴포저블에 필요한 정보만을 전달하는 것입니다.  
이를 통해 컴포저블을 더 재사용하기 쉽고 테스트하기 쉽게 만들 수 있습니다.

---

### Creating a CompositionLocal

컴포즈는 `CompositionLocal` 생성을 위한 2가지 API를 제공합니다.

`compositionLocalOf`는 값이 변할 때 값을 사용하는 컴포저블만 'ReComposition' 되도록 합니다.  
따라서, 제공된 값이 자주 변할 가능성이 있다면 `compositionLocalOf`를 사용하는 것이 좋습니다.

`staticCompositionLocalOf`는 제공되는 값을 추적하지 않기에, 값이 변경되면 `CompositionLocal`이 제공한 `content` 람다의 전체가 'ReComposition' 됩니다. 
이는 값이 거의 변경되지 않을 때 사용하기 적합하며, 불필요한 추적과 'ReComposition'을 방지하여 성능을 향상 시킬 수 있습니다.

예를 들어, 앱의 디자인 시스템은 컴포넌트에 '그림자'를 사용하여 컴포저블이 돋보이게 하는 방식을 적용할 때,  
다양한 'elevations'이 UI 트리 전체에 전파되어야 하므로 `CompositionLocal`을 사용할 수 있습니다.  
`CompositionLocal` 값이 시스템 테마에 따라 변경될 수 있기 때문에, `compositionLocalOf`를 사용하는 것이 적합합니다.

```kotlin
data class Elevations(val card: Dp = 0.dp, val default: Dp = 0.dp)

// 기본값을 가진 CompositionLocal 글로벌 객체 정의
// 이 인스턴스는 앱 내의 모든 composables에서 접근할 수 있음
val LocalElevations = compositionLocalOf { Elevations() }
```

---

### Providing values to a CompositionLocal

`CompostionLocalProvider` 컴포저블은 주어진 계층에 대한 `CompositionLocal` 인스턴스의 값들을 바인딩 합니다.  
`CompositionLocal`에 새로운 값을 제공하려면, `provides` infix 함수를 사용하여 `CompositionLocal`의 키와 해당 값을 연결하면 됩니다.

```kotlin
class MyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // 시스템 테마에 따라 elevation 계산
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

### Consuming the CompositionLocal

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

---

---

---

#### UI 트리
Composition 과정에 의해 구성되고, 업데이트되며, 유지되는 'LayoutNode Tree'