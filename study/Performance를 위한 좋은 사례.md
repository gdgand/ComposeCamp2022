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

## Use `derivedStateOf` to limit recompositions

> - 'State' 변화가 빠르게 일어날 때, `derivedStateOf`를 사용하여 필요한 경우에만, UI 'ReComposition' 하는 것이 효율적

`derivedStateOf`를 사용하여 'ReComposition'을 제한하는 것은 효율적인 UI 업데이트를 위한 중요한 방법입니다.  
사용자가 스크롤하는 것과 같이 'State' 변화가 매우 빠르게 일어날 때, `derivedStateOf`를 사용하여 필요한 경우에만 UI를 'ReComposition' 하는 것이 효율적 입니다.

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

위 예시와 같이 `listState.firstVisibleItemIndex > 0`는 스크롤 할 때마다 'State'가 변경될 수 있기에 불필요한 'ReComposition'이 발생되며 성능 저하를 야기할 수 있습니다.

이를 위한 해결책으로 `dervidedStateOf`를 사용하여 실제 중요한 'State' 변화 시, 'ReComposition'을 트리거 할 수 있습니다.

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

## Defer reads as long as possible

> - UI 성능 문제가 발생할 때, 'State Read' 지연이 도움이 될 수 있음
> - 'State Read'를 'lambda'로 감싸 'Child Composable'에서 실제 'State'가 필요할 때만 'State Read' 지연 가능
> - 'lambda modifier'를 통해, 'State Read'를 'Composition 단계'를 건너뛰어 'Layout 단계'에서 발생하도록 하여 성능 개선 가능

성능 문제가 확인되면, 'State Read'를 지연시키는 것이 도움이 될 수 있습니다.  
'State Read'가 지연되면 'Compose'는 최소한의 'Composable'만 'ReComposition' 합니다.  

예를 들어 'Composable Tree' 상단에 'Hoist State'가 있고, 해당 'State'를 'Child Composable'에서 읽을 때,  
'State Read'를 'lambda'로 감싸게 되면 실제로 'Child Composable'에서는 해당 'State'가 필요할 때만 'State Read'가 발생됩니다.

'Scroll Offset'을 알아야 하는 `Title`의 '최적화 전'의 예시를 살펴보면 다음과 같습니다.

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

`scroll`이 변경될 때, 'Compose'는 'Nearest parent Recomposition scope'를 찾아 'Invalidate' 합니다.  
위 예제의 경우 `SnackDetail`이 'Nearest parent ReComposition scope'에 해당됩니다.

따라서 'Compose'는 `SnackDetail`과 그 내부의 '모든 Composable'을 'ReComposition' 합니다.  
그러나 코드를 변경하여 실제로 사용하는 곳에서만 'State Read'를 하면 'ReComposition'을 최소화 할 수 있습니다.

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
        modifier = Modifier.offset(y = offset)
    ) {
        // ...
    }
}
```

`Title`에서 `scroll` 파라미터를 'lambda'로 변경하면, `Title`은 'Hoist State'를 여전히 참조할 수 있고, 실제 필요한 `Title` 내부에서 '값'을 읽을 수 있습니다.

결과적으로 `scroll` 값이 변경될 때, 'nearest parent recomposition scope'는 `Title`로 변경되고 'Compose'는 전체 `Box`를 'ReComposition' 할 필요가 없어집니다.

이는 좋은 개선이지만, Composable의 단순한 're-layout' 또는 're-draw'를 위해 'ReComposition'을 일으키는 경우에는 'Layout 단계'에서 수행 시키게 되면 더 좋은 개선이 될 수 있습니다.

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

전 코드에서는 `Modifier.offset(x: Dp, y: Dp)`를 사용 했지만, 이는 `Offset`을 파라미터로 받습니다.  
이를 'lambda Modifier'로 전환하면, 'Layout 단계'에서 'State Read'가 발생하는 것을 확실하게 할 수 있습니다.

그 결과, 'scroll state'가 변경될 때 'Compose'는 Frame 구성 중 'Composition 단계'를 건너뛰고 바로 'Layout 단계'로 넘어 갈 수 있습니다.
이와 같이 자주 변경되는 'State'를 'Modifier'에 전달할 떄는 가능한 'lambda Modifier'를 사용하는 것이 효율적 입니다.  

아래는 'lambda modifier'의 또 다른 예시로, 최적화 되지 않는 코드 입니다.

```kotlin
val color by animateColorBetween(Color.Cyan, Color.Magenta)

Box(
    Modifier
        .fillMaxSize()
        .background(color)
)
```

위와 같이 `Box`의 배경색은 2가지 색상을 빠르게 전환합니다. 이는 'State'가 매우 자주 변경됨을 알 수 있습니다.  
'Composable'은 `Background`에서 이 'State'를 읽고 있으며, 그 결과 `Box`는 매 Frame 마다 'ReComposition' 됩니다. 

이를 위해 'lambda Modifier'인 `drawBehind`를 사용하여 개선 할 수 있습니다.  
`drawBehind`는 'Layout 단계'에서만 색상 'State Read'를 수행하도록 합니다.

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

## Avoid backwards writes

> - 'backwards write' : 'Composition 단계'에서 'State write' 하는 것
> - 'backwards write'는 무한 'ReComposition'이 발생됨
> - 'backwards write' 방지
>   - 'Composition 단계'에서의 'State write' 코드 제거
>   - `onClick`과 같은 'Event'에 반응하여 'lambda' 내에서 'State write'

'Compose'에는 'State'를 이미 읽은 후에는 결코 'State'에 'write' 하지 않을 것이라는 핵심 가정이 있습니다.  
이를 위반 하는 것을 'backwards write'라고 하며, 이는 끊임 없이 매 Frame 마다 'ReComposition'을 발생시킬 수 있습니다.

아래 코드는 'backwards write'를 발생시키는 예시 입니다.

```kotlin
@Composable
fun BadComposable() {
    var count by remember { mutableStateOf(0) }

    // 클릭 시 ReComposition 발생
    Button(
        onClick = { count++ }, 
        modifier = Modifier.wrapContentSize()
    ) {
        Text("Recompose")
    }

    Text("$count")
    count++ // backwards write, 무한 ReComposition 발생
}
```

위 코드는 'State'를 읽은 후 'State'를 업데이트 하고 있습니다.  
`Button`을 클릭하면, 'Composable'이 'ReComposition'을 발생시키고 `count`가 무한으로 증가하는 것을 알 수 있습니다.

'State'를 'Composition 단계'에서 'write'하는 것을 피함으로써 'backwards write'를 방지할 수 잇습니다.  
또한 가능하다면, 위 `onClick`과 같이 'Event'에 반응하여 'lambda' 내에서 'State write'를 하는 것이 좋습니다. 