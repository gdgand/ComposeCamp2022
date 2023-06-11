# 재구성(Recomposition)

명령형 UI 모델에서 위젯을 변경하려면, 해당 위젯의 setter를 호출해야 합니다.
그러나 Compose에서는 새 데이터와 함께 Composable 함수를 다시 호출하면 됩니다.

이를 통해 해당 함수가 재구성되며, 필요한 경우 새 데이터로 위젯이 다시 그려집니다.   
Compose 프레임워크는 변경된 컴포넌트만 지능적으로 재구성(recompose)할 수 있습니다.

예를 들어, 다음과 같은 버튼을 표시하는 Composable 함수가 있습니다.

```kotlin
@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times")
    }
}
```

버튼을 클릭할 때마다 호출자는 clicks 값을 업데이트합니다. Compose는 Text 함수의 람다를 다시 호출하여 새 값을 표시합니다.
이 과정을 재구성(recomposition)이라고 합니다. 이때, 값에 의존하지 않는 다른 함수들은 재구성되지 않습니다.

UI 트리 전체를 재구성하는 것은 계산적으로 비용이 많이 들며, 컴퓨팅 파워와 배터리 수명을 소비합니다.   
Compose는 이러한 문제들을 지능적인 재구성을 통해 해결합니다.

### Compose 작동 원리

재구성(recomposition)은 Composable 함수의 입력이 변경될 때 발생하는 과정입니다.   
Compose는 새로운 입력을 기반으로 재구성할 때 변경될 수 있는 함수나 람다만 호출하고 나머지는 건너뜁니다. 이를 통해 가능한 많은 Composable 함수와 람다를 건너뛰어 효율적으로 재구성합니다.

### [Side-Effect](../../study/Compose의 Side-Effect.md) 관리

Composable 함수의 실행으로 인해 발생하는 Side-Effect에 의존해서는 안 됩니다.   
이런 Side-Effect들은 공유 객체에 write 작업을 수행하거나, ViewModel에서 Observable을 업데이트하거나, SharedPreferences를 업데이트하는 것 등이 있습니다.

Compose에서는 이러한 Side-Effect를 관리하기 위해 `LaunchedEffect`와 같은 Effect Handler를 제공합니다.

다음은 `LaunchedEffect`를 사용하여 Side-Effect를 관리하는 예시입니다.

```kotlin
@Composable
fun ExampleComposable(viewModel: ExampleViewModel) {
    var data by remember { mutableStateOf(null) }

    LaunchedEffect(viewModel) {
        data = viewModel.getDataFromNetwork()
    }

    if (data != null) {
        // render your UI here
    }
}
```

### Compose 애니메이션과 재구성

Composable 함수는 애니메이션을 렌더링 할 때와 같이 매 프레임마다 자주 다시 실행될 수 있습니다.
원활한 애니메이션을 위해 Composable 함수는 빠르게 실행되어야 합니다. 만약 비용이 많이 드는 작업을 수행해야 한다면,
백그라운드 코루틴에서 이를 처리하고 결과 값을 매개변수로 Composable 함수에 전달하는 방식으로 처리해야 합니다.

예를 들어, `SharedPreferences`에서 값을 가져와 UI 업데이트를 하는 Composable 함수가 있습니다.
여기서는 Composable 함수 자체에서는 `SharedPreferences`에서 읽거나 쓰지 않아야 하며, 읽고 쓰는 작업은 `ViewModel`에서 작성 합니다.
읽고 쓰는 작업을 통해 데이터가 업데이트되면 업데이트 된 데이터를 Composable 함수로 값을 전달하는 로직까지 `ViewModel`에서 작성해줍니다.

```kotlin
@Composable
fun SharedPrefsToggle(
    text: String,
    value: Boolean,
    onValueChanged: (Boolean) -> Unit
) {
    Row {
        Text(text)
        Checkbox(checked = value, onCheckedChange = onValueChanged)
    }
}
```

### Compose 고려사항

- Composable 함수는 임의의 순서로 실행될 수 있습니다.
- Composable 함수는 병렬로 실행될 수 있습니다.
- 재구성(recomposition)은 낙관적으로 수행되며 취소될 수 있습니다.
- Composable 함수는 애니메이션의 각 프레임마다 실행될 수 있습니다.

---

## Composable 함수의 실행 순서

```kotlin
@Composable
fun ButtonRow() {
    MyFancyNavigation {
        StartScreen()
        MiddleScreen()
        EndScreen()
    }
}
```

위 예제를 보면 위에서 아래순으로 Composable 함수가 실행되는것처럼 보이지만,
위 Composable 함수들은 어떠한 순서로든 실행될 수 있습니다.

예를 들어, `StartScreen()`이 어떠한 `luanchedEffect`를 통해 전역변수를 설정하고 `MiddleScreen()`이 그 전역변수를 활용하는 경우,  
`StartScreen()`에서 `launchedEffect`를 통해 데이터 얻는것에 딜레이가 생겨 `MiddleScreen()`이 먼저 실행되면 `StartScreen()`에서 설정한 전역변수를 활용할 수
없습니다.

위와 같은 경우가 있기에 각각의 함수들은 각각 **독립적**으로 작동해야 합니다.

---

## Composable 함수의 병렬 실행

Jetpack Compose는 여러 코어를 활용하고 화면 밖에서 실행되는 composable 함수를 낮은 우선순위로 실행함으로써 성능을 최적화할 수 있습니다.
이는 composable 함수가 백그라운드 스레드 풀에서 실행될 수 있음을 의미합니다.

```kotlin
@Composable
fun ExpensiveComposable() {
    // 많은 리소스를 소모하는 작업
    // API 통신 후 리소스를 얻는 작업 등
}

@Composable
fun MainScreen() {
    ExpensiveComposable()  // 이 작업은 백그라운드에서 병렬로 실행될 수 있습니다.
    Text("This is the main screen") // 이 Text Composable은 ExpensiveComposable이 완료되기 전에 그려질 수 있습니다.
}
```

### Composable 함수의 동작 관리

Composable 함수는 순수하게 UI 구성하는데 집중하고, 데이터를 변경하거나 네트워크 요청과 같은 Side-Effect의 발생은 Composable 함수 밖에서 관리 하는것이 원칙입나다.

그럼에도 불구하고 UI에서는 사용자 상호작용을 수행햐야 하는 경우가 있습니다. (`onClick`과 같은)  
이러한 상호작용(`onClick`)들은 항상 UI 스레드에서 실행되므로 이러한 위치에서 상태가 변경되어도 안전하게 처리가 가능합니다.

```kotlin
@Composable
fun ButtonWithClickCounter() {
    var clickCount by remember { mutableStateOf(0) }

    Button(
        onClick = { clickCount++ } // onClick 콜백에서 동작이 발생합니다. 이는 UI 스레드에서 실행되기에 이런 동작은 허용됩니다.
    ) {
        Text("You've clicked $clickCount times")
    }
}
```

### Composable 함수의 스레드 안전성

Composable 함수는 다른 스레드에서 호출될 수 있기 때문에, 모든 Composable 함수는 스레드에 안전해야 합니다.

```kotlin
@Composable
fun UnsafeComposable() {
    var items = 0  // 이 변수는 여러 스레드에서 접근될 수 있습니다.

    for (item in list) {
        items++  // 이런 식으로 변수를 변경하는 것은 스레드에 안전하지 않습니다.
    }
}
```

### Composable 함수 내의 부적절한 동작

Composable 함수 내에서 로컬 변수를 변경하는 것은 허용되지 않습니다.
이는 스레드에 안전하지 않고, Composable 함수의 호출 시점에 따라 예상하지 못한 결과를 초래할 수 있습니다.

```kotlin
@Composable
@Deprecated("Example with bug")
fun ListWithBug(myList: List<String>) {
    var items = 0

    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            for (item in myList) {
                Text("Item: $item")
                items++
            }
        }
        Text("Count: $items")  // items 변수는 Composable의 recomposition에 따라 변경되므로 잘못된 값을 표시할 수 있습니다.
    }
}
```

---

## Composable 함수의 재구성의 효과적인 생략
Compose는 UI의 일부가 무효화되었을 때 가능한 한 최소한의 부분만 업데이트 하려고 합니다.   
이는 UI 트리에서 한 `Button`의 Composable만 다시 실행하고, 그 위나 아래의 다른 Composable들은 건너뛸 수 있음을 의미합니다.
 
아래의 예제는 리스트를 렌더링할 때 일부 요소를 건너뛸 수 있는 `ReComposition`을 보여줍니다.

```kotlin
@Composable
fun NamePicker(
    header: String,
    names: List<String>,
    onNameClicked: (String) -> Unit
) {
    Column {
        // [header]가 변경될 때 이 부분이 recompose됩니다. 
        // [names]가 변경되어도 이 부분은 recompose되지 않습니다.
        Text(header, style = MaterialTheme.typography.bodyLarge)
        
        LazyColumn {
            items(names) { name ->
                // 아이템의 [name]이 업데이트되면, 해당 아이템의 adapter가 recompose됩니다.
                // [header]가 변경되어도 이 부분은 recompose되지 않습니다.
                NamePickerItem(name, onNameClicked)
            }
        }
    }
}

@Composable
private fun NamePickerItem(name: String, onClicked: (String) -> Unit) {
    Text(name, Modifier.clickable(onClick = { onClicked(name) }))
}
```

이처럼 `ReComposition`이 발생될 때, 전체 UI가 아닌 변경된 부분만 업데이트 됩니다.

각 Composable 함수나 람다는 독립적으로 `recompose` 될 수 있으므로, 필요한 부분만 빠르게 업데이트할 수 있어 효율적입니다.   
Compose는 위와 같은 특징으로 UI의 업데이트 성능을 극대화합니다.

---

## ReComposition == 낙관적

### ReComposition의 시작과 종료 
Recomposition은 Composable 함수의 매개 변수가 변경되었을 수 있다고 Compose가 판단할 때마다 시작됩니다.

Recomposition은 낙관적이라는 의미로, Compose는 매개 변수가 다시 변경되기 전에 ReComposition을 완료하려고 합니다.
만약 매개 변수가 ReComposition이 완료되기 전에 변경된다면, Compose는 진행 중이던 ReComposition을 취소하고 새로운 매개 변수로 다시 시작할 수 있습니다.

### Recomposition의 취소
Recomposition이 취소되면, Compose는 ReComposition에서의 UI 트리를 폐기합니다. 
UI가 표시되는 것에 의존하는 어떠한 Side-Effect가 있어도, ReComposition이 취소되더라도 해당 Side-Effect는 적용됩니다. 이는 앱 상태의 불일치를 초래할 수 있습니다.

### 낙관적인 ReComposition 처리
낙관적인 ReComposition을 처리하기 위해 모든 Composable함수와 람다가 멱등성을 가지고 있고 Side-Effect이 없도록 해야 합니다.
 
예를 들어, 사용자가 버튼을 눌러 데이터를 불러오는 과정에서 데이터를 가져오는 동안 사용자가 다른 버튼을 눌러 다른 데이터를 요청했다면,  
처음의 데이터 로딩은 취소되고 새로운 데이터 로딩이 시작됩니다.   

이때 Compose는 원래의 UI 트리를 폐기하고 새로운 데이터에 맞게 UI를 다시 그리게 됩니다.   
이 과정에서 발생하는 모든 Side-Effect들이 적용되므로, 이전 데이터 로딩으로 인해 발생했던 UI 변경이 새로운 UI 변경에 영향을 줄 수 있습니다.  
이러한 문제를 방지하기 위해 모든 Composable 함수와 람다가 멱등성을 가지고 있어야 하며 Side-Effect가 없어야 합니다.
 
Composable 함수들은 [멱등성](../용어.md#멱등성)을 통해 여러번 recompose가 발생하더라도 항상 동일한 결과를 보장하고, 앱의 상태를 일관성있게 유지할 수 있습니다.