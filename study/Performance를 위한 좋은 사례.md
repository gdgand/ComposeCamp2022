# Follow Best Practices

### 비용이 많이드는 계산을 최소화 하기 위해 `remember` 사용
Composable은 Animation의 모든 프레임마다 실행되는 정도로 자주 재실행될 수 있습니다. 
이러한 이유로, composable의 내부는 가능한 적은 계산을 수행하도록 설계해야 합니다.

이를 위해서 Compose에서는 `remember`라는 기능을 제공합니다.
`remember`는 계산된 결과를 저장하는것으로 이 계산은 1번만 수행되며, 필요할 때 마다 결과를 가져올 수 있습니다.

다음 코드는 정렬한 연락처를 목록으로 표시하지만, 정렬을 비효율적으로 처리하는 예시 입니다.

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

`ContactsList()`가 재구성될 때마다, 전체 연락처 목록이 다시 정렬되지만, 목록 자체는 변하지 않았습니다. 
사용자가 목록을 스크롤하면, 새로운 행이 나타날 때마다 Composable이 재구성됩니다.

이 문제를 해결하기 위해, `LazyColumn` 외부에서 목록을 정렬하고, 정렬된 목록을 `remember`를 통해 저장해야 합니다.

```kotlin
@Composable
fun ContactList(
    contacts: List<Contact>,
    comparator: Comparator<Contact>,
    modifier: Modifier = Modifier
) {
    val sortedContacts = remember(contacts, sortComparator) {
        contacts.sortedWith(sortComparator)
    }

    LazyColumn(modifier) {
        items(sortedContacts) {
            // ...
        }
    }
}
```

이제, `ContactList()`가 처음 구성될 때 목록이 한 번 정렬됩니다. 
만약 연락처나 비교자가 변경되면, 정렬된 목록이 재생성됩니다. 
그렇지 않다면, composable은 캐시된 정렬된 목록을 계속 사용할 수 있습니다.

---

### Lazy Layout 사용시 Key 적용

`Lazy` 접두어를 사용하는 레이아웃들은 아이템을 최대한 재사용하고, 필요할 때만 재성성하거나 재구성하도록 합니다.   
추가로, 개발자들은 Key를 사용하여 이러한 동작을 더 최적화할 수 있습니다.

```kotlin
@Composable
fun NotesList(notes: List<Note>) {
    LazyColumn {
        items(
            key = { note -> note.id },
            items = notes
        ) { note ->
            NoteRow(note)
        }
    }
}
```
위처럼 `key`를 적용하지 않은 경우, `note` 목록이 변경될 경우 모든 `note`를 재구성하겠지만, 
위와 같이 해당 아이템에 `key`를 제공함으로써, 재구성 시 변경된 노트만 재구성을 하여 불필요한 재구성을 피할 수 있습니다. 

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