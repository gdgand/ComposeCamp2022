## Composable 함수
Compose를 사용하면, Composable 함수를 통해 데이터를 UI 요소로 변환하고 그려내는 작업을 수행합니다.

아래는 Composable 함수의 간단한 예제입니다.

```kotlin
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
```

### Composable 함수의 특징
| 특징 | 설명 |
| --- | --- |
| `@Composable` | 이 Annotation이 붙은 함수는 Compose 컴파일러에게 이 함수가 UI를 그리는 데 사용됨을 알려줍니다. |
| 매개변수 | Composable 함수는 매개변수를 받을 수 있으며, 이를 통해 UI를 동적으로 구성하는 데 사용합니다. |
| 함수 호출 | Composable 함수 내에서는 다른 Composable 함수를 호출하여 UI 계층을 구성할 수 있습니다. |
| 반환값 없음 | Composable 함수는 아무것도 반환하지 않습니다. 그 Composable 함수의 목적은 UI를 그리는 것입니다. |
| 동일한 동작 | 동일한 매개변수를 가진 Composable 함수는 여러 번 호출되어도 동일하게 동작합니다. |
| 외부 상태 사용 제한 | Composable 함수는 가능한 한 순수하게 유지되어야 합니다. 즉, `전역 변수`, `random()`과 같은 외부 상태를 사용하지 않는 것이 좋습니다. 이를 통해 함수의 동작을 예측 가능하게 하고, 버그를 줄일 수 있습니다. |
| 재사용 가능 | Composable 함수는 재사용 가능합니다. 즉, 동일한 Composable 함수를 여러 곳에서 호출할 수 있습니다. |
| 테스트 용이 | Composable 함수는 단위 테스트하기 쉽습니다. |