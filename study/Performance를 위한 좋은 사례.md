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