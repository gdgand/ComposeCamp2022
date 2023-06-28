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
