# 목차
- [Hoisting 특성](#Hoisting-특성)
- [데이터 단방향 흐름](#데이터-단방향-흐름-udf)
- [Hoisting 규칙](#state-hoisting-규칙)

# State hoisting

`State hoisting`은 Composable에서 상태를 상위 호출자로 이동시켜 Composable을 `Stateless Composable`로 만드는 패턴입니다.  
이는 Composable의 재사용성을 높이고, 테스트를 용이하게 만들며, 어디에서든 그 상태를 저장할 수 있도록 해줍니다.

일반적으로 Compose에서 `State hoisting`을 구현하는 패턴은 상태 변수를 두 가지 매개변수로 대체하는 것입니다.

- `value: T`: 화면에 표시할 현재 값
- `onValueChange: (T) -> Unit`: 값이 변경될 것을 요청하는 이벤트, T 타입의 새로운 값이 제공됩니다.

## Hoisting 특성

### Single source of truth (단일 공급)
상태를 이동시키는 것으로, 정보의 정확성과 일관성을 보장합니다.   
즉, 동일한 상태가 여러 곳에서 중복되어 사용되는 것을 방지하여, 버그를 피하게 해줍니다.

### Encapsulated (캡슐화)
`Stateful Composable`만이 그 상태를 수정할 수 있습니다.   
이는 상태를 완전히 내부적으로 관리하므로, 외부에서 상태를 무분별하게 변경하는 것을 막아줍니다.

### Shareable (공유 가능)
호이스팅된 상태는 여러 Composable 사이에서 공유될 수 있습니다.   
즉, 하나의 상태를 여러 Composable에서 동시에 사용할 수 있어, 유연성과 재사용성을 증가시킵니다.

### Interceptable (가로채기 가능)
`Stateless Composable`을 호출하는 사용자는, 상태 변경 이벤트를 무시하거나 수정할 수 있습니다.   
이는 사용자가 상태 변경을 더 세밀하게 제어할 수 있게 해줍니다.

### Decoupled (분리 가능)
`Stateless Composable`의 상태는 어디에서든 저장될 수 있습니다.   
예를 들어, 상태를 `ViewModel`과 같은 별도의 저장 공간에 두어, 상태 저장 위치의 유연성을 높이고 Composable의 관심사를 분리할 수 있습니다.   
이는 Composable의 재사용성을 더욱 높이며, 코드를 더욱 쉽게 이해하고 테스트할 수 있게 해줍니다.


예를 들어, 아래는 `HelloContent`"에서 `name`의 상태를 호이스팅하여 `HelloScreen`으로 이동시킵니다.

```kotlin
@Composable
fun HelloScreen() {
    var name by rememberSaveable { mutableStateOf("") }

    HelloContent(name = name, onNameChange = { name = it })
}

@Composable
fun HelloContent(name: String, onNameChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello, $name",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text("Name") })
    }
}
```

이렇게 `State Hoisting`하면 Composable에 대한 이해가 쉬워지고, 다양한 상황에서 재사용할 수 있으며, 상태가 어떻게 저장되는지와 별개로 테스트가 가능해집니다.   
예를 들어, `HelloScreen`을 수정하거나 교체해도 `HelloContent`의 구현 방식을 변경할 필요가 없습니다.

## 데이터 단방향 흐름 (UDF)
<img src="../../resource/host_hoisting.png" width="30%" height="30%">

데이터의 단방향 흐름(Unidirectional data flow)은 `State Hoisting` 패턴의 중요한 부분입니다.   
상태는 아래로 이동하고 이벤트는 위로 이동합니다. 
이런 방식으로, 상태를 UI에 표시하는 Composable과 상태를 저장하고 변경하는 부분과 분리할 수 있습니다.

## State Hoisting 규칙
### 상태는 그 상태를 사용하는 모든 Composable의 가장 낮은 공통 부모로 최소한 호이스팅되어야 합니다.
이는 모든 상태를 사용하는 Composable 함수가 동일한 상태를 볼 수 있도록 하기 위한 것입니다.  
상태가 여러 Composable 함수에서 공유되어야 하는 경우, 이러한 Composable 함수의 공통 부모 레벨에서 상태를 정의해야 합니다. 
이렇게 하면 모든 자식 Composable 함수가 동일한 상태를 참조할 수 있습니다.

### 상태는 변경될 수 있는 가장 높은 레벨로 호이스팅되어야 합니다.
이는 상태를 변경하는 로직이 한 곳에 집중되도록 하기 위한 것입니다. 이를 통해 상태 변경이 어디에서 일어나는지 추적하기 쉬워집니다.   
상태가 여러 곳에서 변경될 수 있는 경우, 이 변경 로직을 가장 상위 레벨에서 처리해야 합니다.   
이렇게 하면 상태 변경 로직이 분산되는 것을 방지할 수 있습니다.

### 같은 이벤트에 반응하여 변경되는 두 상태는 함께 호이스팅되어야 합니다.
이는 상태 변경이 동기화되도록 하기 위한 것입니다.   
특정 이벤트가 여러 상태를 동시에 변경해야 하는 경우, 이러한 상태는 함께 호이스팅되어야 합니다.   
이렇게 함으로써 모든 관련 상태 변경이 동시에 일어나도록 할 수 있습니다.
