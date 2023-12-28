# Follow Best Practices

## Use `remember` to minimize expensive calculations

> - `remember`는 연결된 'Composable'이 'ReComposition' 될 때까지만 값 저장
> - 'Composable'이 'Composition'에서 제거되면 `remember`로 저장된 값도 초기화 됨
> - `remember`는 `Key`를 파라미터로 받을 수 있으며, 해당 `Key`가 변경될 때 저장된 값을 재계산
> - `remember` 사용 시, 큰 데이터 또는 복잡한 객체를 저장하면 '메모리 사용량이 증가'할 수 있기에 **주의**

'Composable'은 애니메이션의 각 프레임마다 자주 실행될 수 있기에, 'Composable' 내에서 수행되는 '계산'은 최소화해야 합니다.  
이를 위해 `remember`를 사용하여 '한 번만 계산을 실행'하고 그 '결과를 저장'하여 필요할 때마다 결과를 가져오게 할 수 있습니다.

예를 들어, 목록을 정렬하는데 많은 비용이 드는 방식으로 정렬을 수행하는 코드가 있다고 가정하면 다음과 같습니다.

```kotlin
@Composable
fun ContactList(
    contacts: List<Contact>,
    comparator: Comparator<Contact>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        // DON’T DO THIS
        items(contacts.sortedWith(comparator)) { contact ->
            // ...
        }
    }
}
```

위 코드는 `ContactList`가 'ReComposition' 될 때마다 전체 연락처 목록이 재정렬 됩니다.  
이는 사용자가 목록을 스크롤하여 새로운 행이 나타날 때마다 'Composable'이 'ReComposition' 되며, `contacts`가 변경되지 않아도 목록이 재정렬 됩니다.

이 문제를 해결하기 위해 `LazyColumn` 외부에서 목록을 정렬하고, 정렬된 목록을 `remember`로 저장하여 계산을 최소화 할 수 있습니다.

```kotlin
@Composable
fun ContactList(
    contacts: List<Contact>,
    comparator: Comparator<Contact>,
    modifier: Modifier = Modifier
) {
    val sortedContacts = remember(contacts, comparator) {
        contacts.sortedWith(comparator)
    }

    LazyColumn(modifier) {
        items(sortedContacts) {
            // ...
        }
    }
}
```

이제 연락처 목록은 `ContactList`가 Composition 될 때 1번만 정렬됩니다.  
만약 `contacts`나 `comparator`가 변경되면 `sortedContacts`를 재생성하게 됩니다.  
그런 경우가 아니라면 'Composable'은 캐싱된 `sortedContacts`를 계속 사용할 수 있습니다.

---

## Use Lazy layout keys

> - 'Compose'는 목록 중 하나의 항목이 이동되었을 때, 해당 항목만 'ReComposition' 하지 않고, 전체 목록을 'ReComposition' 함 
>   이를 위해 'Lazy Layout'에 Key를 제공하여 'ReComposition'을 최소화하여 해당 항목만 'ReComposition' 되도록 처리

'Lazy layout'은 목록에서 항목이 이동할 때마다 모든 항목을 'ReComposition' 또는 'ReGenerate' 하는 것을 피할 수 있도록 도와줍니다.  
특히, 목록에서 항목의 순서가 자주 변경되는 경우에 'Lazy 레이아웃'을 사용하는 것이 효율적 입니다.

아래 예시는 '수정한 시간 별'로 정렬된 노트 목록을 보여주고 있습니다.

```kotlin
@Composable
fun NotesList(notes: List<Note>) {
    LazyColumn {
        items(
            items = notes
        ) { note ->
            NoteRow(note)
        }
    }
}
```

이 때 사용자가 가장 아래에 있던 노트를 가장 최근에 수정하여 목록의 맨 위로 이동한다면,   
위 방식의 'Lazy layout'은 변경되지 않은 노트 항목들도 'ReComposition' 될 수 있습니다.  
이는 'Compose'가 단순히 목록에서 항목들이 이동헸다는 것을 인지하지 않고, 각 항목이 삭제되고 새로 생성된 것으로 간주하기 때문입니다.  
이런 방식은 효율성이 떨어지고 불필요한 'ReComposition'을 야기합니다.

이 문제를 해결하기 위해 각 항목에 대한 'Stable Key'를 제공하는 것이 좋습니다.  
이 'Key'는 각 항목을 '고유하게 식별'할 수 있게 해주며, 'Compose'가 항목이 단순히 이동했음을 인식하고 
데이터 변경이 없는 항목에 대해서는 'ReComposition'을 '스킵'할 수 있도록 합니다.

```kotlin
@Composable
fun NotesList(notes: List<Note>) {
    LazyColumn {
        items(
            items = notes,
            key = { note ->
                // Key 
                note.id
            }
        ) { note ->
            NoteRow(note)
        }
    }
}
```

---

### derivedStateOf을 사용하여 재구성 횟수 제한

Composition에서 상태를 사용할 때 발생할 수 있는 위험 중 하나는 상태가 급속하게 변경될 경우 UI가 필요 이상으로 재구성 될 수 있다는 점입니다.  
예를 들어, 스크롤 가능한 목록을 표시하는 경우가 있습니다. 목록의 상태를 검사하여 목록에서 첫 번째로 표시되는 항목을 확인합니다.

```kotlin
val listState = rememberLazyListState()

LazyColumn(state = listState) {
    // ...
}

val showButton = listState.firstVisibleItemIndex > 0

AnimatedVisibility(visible = showButton) {
    ScrollToTopButton()
}
```

여기에서 사용자가 목록 스크롤 시, 사용자의 손가락을 드래그하는 동안 `listState`가 계속 변경되는것이 문제입니다.
즉, 목록이 계속해서 재구성됩니다. 그러나 실제로는 새로운 항목이 하단에 보이게 될 때까지 재구성할 필요가 없습니다.
따라서 이는 많은 추가 계산을 하고 있음을 의미하며, 이로인한 UI 성능이 저하 됩니다.

해결책으로는 `derivedState`를 사용하는 것입니다. 이를 사용하면 어떤 상태의 변경이 실제로 재구성을 트리거해야 하는지 Compose에 알려줄 수 있습니다.  
위 경우엔, 첫 번째로 보이는 항목이 언제 변경되는지에 대한 관심이 있다고 명시되어 있기에, 그 상태 값이 변경되면 UI를 재구성해야 합니다.

```kotlin
val listState = rememberLazyListState()

LazyColumn(state = listState) {
    // ...
}

val showButton by remember {
    derivedStateOf {
        listState.firstVisibleItemIndex > 0
    }
}

AnimatedVisibility(visible = showButton) {
    ScrollToTopButton()
}
```

---

### 상태를 가능한 한 늦게 읽도록 지연
성능에 문제가 확인되면, 상태 읽기를 지연하는것이 도움이 될 수 있습니다. 
상태 읽기를 지연시키는 것은 Compose가 재구성 시 최소한의 코드만 실행하도록 보장합니다. 

예를 들어, UI에 상위 Composable 트리에 호이스팅된 상태가 있고, 
이를 하위 Composable에서 읽는다면, 이 상태를 읽는것을 람다 함수로 감쌀 수 있습니다.
이렇게 하면 실제로 필요할 때만 읽기가 발생합니다.

```kotlin
@Composable
fun SnackDetail() {
    // ...

    Box(Modifier.fillMaxSize()) { // Recomposition Scope Start
        val scroll = rememberScrollState(0)
        // ...
        Title(snack, scroll.value)
        // ...
    } // Recomposition Scope End
}

@Composable
private fun Title(snack: Snack, scroll: Int) {
    // ...
    val offset = with(LocalDensity.current) { scroll.toDp() }

    Column(
        modifier = Modifier
            .offset(y = offset)
    ) {
        // ...
    }
}
```

이 효과를 얻기 위해 `Title` Composable은 `Modifier`를 사용하여 자신을 오프셋하기 위해 스크롤의 `offset`을 알아야 합니다.

스크롤 상태가 변경되면, Compose는 가장 가까운 상위 재구성 범위를 찾아서 무효화 합니다.
이 경우, 가장 가까운 범위는 `SnackDetail()`입니다. 따라서 Compose는 `SnackDetail()`을 재구성하고, `SnackDetail()` 내부의 모든 Composable을 재구성합니다.

상태를 실제로 사용하는 곳에서만 읽도록 코드를 변경하면, 재구성이 필요한 요소의 수를 줄일 수 있습니다.

```kotlin
@Composable
fun SnackDetail() {
    // ...

    Box(Modifier.fillMaxSize()) { // Recomposition Scope Start
        val scroll = rememberScrollState(0)
        // ...
        Title(snack) { scroll.value }
        // ...
    } // Recomposition Scope End
}

@Composable
private fun Title(snack: Snack, scrollProvider: () -> Int) {
    // ...
    val offset = with(LocalDensity.current) { scrollProvider().toDp() }
    Column(
        modifier = Modifier
            .offset(y = offset)
    ) {
        // ...
    }
}
```

`scroll` 파라미터를 `scrollProvider` 람다로 변경하였고, 이는 `Title`이 호이스팅된 상태를 여전히 참조할 수 있지만, 값은 실제 필요한 `Title` 내부에서만 읽습니다.
결과적으로, 스크롤 값이 변경될 때 가장 가까운 재구성 범위는 `Title()`이며, Compose는 더 이상 `SnackDetail()`을 재구성할 필요가 없습니다.

이 정도만으로도 좋은 개선이지만, 더 좋은 방법이 있습니다.  
위 Composabledms `offset`만을 변경하고 있습니다. 이 작업은 Layout 단계에서 처리할 수 있습니다.
하지만, 현재 코드에서는 상태가 변경될 때마다 Composition 단계가 일어나므로, 실제보다 더 많은 작업을 함을 알 수 있습니다.

그래서 아래와 같이 `offset`을 람다 기반으로 전환하여 스크롤 상태가 변경될 때 Composition 단계가 아닌 Layout 단계에서 바로 처리할 수 있도록 변경할 수 있습니다. 

```kotlin
@Composable
private fun Title(snack: Snack, scrollProvider: () -> Int) {
    // ...
    Column(
        modifier = Modifier
            .offset { IntOffset(x = 0, y = scrollProvider()) }
    ) {
        // ...
    }
}
```

결과적으로 스크롤 상태가 변경되면 Compose는 Composition 단계를 전부 건너뛰고, 바로 Layout 단계로 이동하여 더 효율적으로 UI 작성을 합니다.
자주 변경되는 상태 변수를 `Modifier`로 전달할 때는 가능한 한 람다 기반 Modifier(lambda versions of the modifier)를 사용해야 합니다.

```kotlin
val color by animateColorBetween(Color.Cyan, Color.Magenta)

Box(
    Modifier
        .fillMaxSize()
        .background(color)
)
```

여기서 박스의 배경색은 2가지 색 사이를 빠르게 전환하고 있습니다. 이 상태는 어느 조건에 따라 매우 자주 변경될 수 있으며,
Composable이 `Modifier`의 `Background`에서 이 상태를 읽습니다. 결과적으로 `Box`는 `color`가 매 프레임 마다 변경되므로 매 프레임마다 재구성해야 합니다.

이를 개선하기 위해, 람다 기반 Modifier인 `drawBehind`를 사용할 수 있습니다.
이는 `color` 상태가 Drawing 단계에서만 읽히게 하여 Compose가 Composition과 Layout 단계를 전부 건너뛸 수 있도록 합니다.

```kotlin
val color by animateColorBetween(Color.Cyan, Color.Magenta)
Box(
    Modifier
        .fillMaxSize()
        .drawBehind {
            drawRect(color)
        }
)
```

---

### backwards write 피하기
Compose는 이미 읽은 상태에 대해서 다시 쓰기 작업을 하지 않을 것이라는 기본 가정을 가지고 있습니다.  
만약 이것을 수행하면, 이를 역방향 쓰기(backwards write)라고 하며, 이는 프레임마다 무한하게 재구성을 발생시킬 수 있습니다.

```kotlin
@Composable
fun BadComposable() {
    var count by remember { mutableStateOf(0) }

    // 클릭 시 재구성 발생
    Button(
        onClick = { count++ }, 
        modifier = Modifier.wrapContentSize()
    ) {
        Text("Recompose")
    }

    Text("$count")
    count++ // 역방향 쓰기, 이미 읽은 상태에 다시 쓰기
}
```
이 코드는 Composable의 끝에서 `count`를 업데이트합니다. 이는 이미 앞서 읽은 상태를 업데이트하므로 역방향 쓰기로 간주됩니다.

1. `BadComposable`가 처음 호출되면 `count`는 0으로 초기화됩니다.
2. 사용자가 버튼을 클릭하면 `onClick` 이벤트가 발생하고 `count` 상태가 증가합니다. 이 상태 변경은 Compose에게 이 Composable을 재구성하도록 지시합니다.
3. 재구성이 발생하면 `count` 상태는 이미 변경되었으므로 `Text("$count")`는 새로운 값을 표시합니다.
4. 그러나 이어서 `count++` 코드가 실행되면, `count` 상태는 이미 읽은 후에 다시 쓰여지게 됩니다. 즉, 역방향 쓰기가 발생합니다.
5. 이렇게 역방향 쓰기가 발생하면 Compose는 무언가 잘못됐다고 판단하고 이 Composable을 다시 재구성하도록 스케줄링합니다. 이로 인해 `count` 값이 무한히 증가하는 문제가 발생하며, 이 Composable은 끊임없이 재구성됩니다.

이 문제를 해결하는 방법은 역방향 쓰기를 피하는 것입니다. 
Composable 내에서 상태를 업데이트하는 대신, 가능하면 이벤트(`onClick` 이벤트)에 응답하여 상태를 업데이트해야 합니다. 
이렇게 하면 Compose는 상태 변경에 대해 알게 되고, 필요할 때만 Composable을 재구성하게 됩니다. 
따라서 UI가 필요 이상으로 빈번하게 재구성되는 것을 피할 수 있습니다.