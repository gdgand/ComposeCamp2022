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
