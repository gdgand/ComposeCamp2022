# Stability in Compose

'Compose'는 'Composable'의 파라미터를 'stable type'과 'unstable type'으로 분류하며,  
타입 안전성은 'ReComposition' 중 해당 값의 변경 여부를 알 수 있는지에 따라 결정됩니다.

또한 'Compose'는 타입 안정성을 기반으로 'ReComposition' 중 'Composable'을 'skip' 할 수 있는지 결정합니다.

- 'Composable'이 'stable parameter'를 가지고 있고, 이들이 변경되지 않았다면, 해당 'Composable'을 'skip' 할 수 있습니다.
- 'Composable'이 'unstable parameter'를 가지고 있다면, 'Compose'는 'Parent Composable'이 'ReComposition' 될 때마다 항상 해당 'Composable'을 'ReCompose' 합니다.

앱에 불필요한 컴포넌트가 많이 포함되어 있고, 'Compose'가 이들을 항상 'ReCompose' 한다면, 성능 문제 및 기타 문제가 발생 할 수 있습니다.

---

## Immutable object

`Contact`는 모든 파라미터가 `val`로 정의된 원시 타입이기 때문에 'Immutable data class'입니다.  
`Contact`의 인스턴스를 생성하고 나면, 'properties' 값을 변경할 수 없습니다.

```kotlin
data class Contact(val name: String, val number: String)
```

`ContactRow`는 `Contact` 타입의 파라미터를 가집니다.

```kotlin
@Composable
fun ContactRow(contact: Contact, modifier: Modifier = Modifier) {
    var selected by remember { mutableStateOf(false) }
    
    Row(
        modifier = modifier
    ) {
        ContactDetails(contact)
        ToggleButton(selected) { selected = !selected }
    }
}
```

사용자가 `ToggleButton`을 클릭하고, `selected`가 변경될 떄를 생각해보면, 다음과 같습니다.

1. 'Compose'는 `ContactRow` 내부 코드를 'ReCompose'할지 평가합니다.
2. `ContactDetails`의 유일한 'argument'는 `Contact` 타입 입니다.
3. `Contact`는 'Immutable data class'이므로, 'Compose'는 `ContactDetails`의 'argument'가 변경되지 않았다고 확신합니다.
4. 따라서 'Compose'는 `ContactDetails`를 'skip'하고 'ReCompose' 하지 않습니다.
5. 반면에, `ToggleButton`의 'argument'는 변경되었으므로 'Compose'는 `ToggleButton`을 'ReCompose' 합니다.

---

만약 위 `Contact`를 'Mutable data class'로 변경한다면, `ContactRow`는 어떻게 될까요?

```kotlin
data class Contact(var name: String, var number: String)
```

위와 같이 `Contact`의 각 파라미터는 `var`로 선언 되었기에 'Mutable data class'입니다.  
'Compose'는 'Properties'의 변경을 인지하지 못하기에 'unstable type'으로 간주합니다.

따라서 'Compose'는 `Contact`를 사용하는 모든 'Composable'에 대해서,  
'ReComposition' 중 `Contact`가 변경되지 않았다고 확신할 수 없기에 해당되는 'Composable'을 'skip' 할 수 없습니다.

따라서 이전 예시의 `ContactRow`는 `selected`가 변경될 때마다 'ReCompose' 됩니다.

---

## Implementation in Compose

Compose 컴파일러는 코드를 실행할 때 각 함수와 타입에 여러 태그 중 하나를 부여하며,  
이 태그는 ReComposition 중 Compose가 함수 또는 타입을 어떻게 처리하는지를 나타냅니다.

### Functions

Compose는 함수를 'skippable', 'restartable'으로 표시할 수 있습니다.  
하나의 함수는 '둘 중 하나', '둘 다', '둘 다 아닌 것'으로 표시될 수 있습니다.

- 'skippable Composable'은 모든 'argument'가 이전 값과 동일하면 'ReComposition' 중 해당 'Composable'을 건너뛸 수 있습니다.
- 'restartable Composable'은 'ReComposition'을 시작할 수 있는 'Scope'로 표시됩니다.  
    즉, 상태 변경 후 'ReComposition'을 위한 코드 재실행 진입점 역할을 할 수 있습니다.

### Types

Compose는 타입을 'immutable type' 또는 'stable type'으로 표시하며, 각 타입은 '둘 중 하나'로 표시됩니다.

- 'immutable type' : 타입의 속성 값이 절대 변경되지 않고, 모든 메서드가 '참조 투명성(referentially transparent)'을 가진 경우 'immutable type'으로 표시합니다. 
  이 떄 모든 원시 타입은 'immutable type'으로 표시됩니다. (`String`, `Int`, `Float`, ...)

- 'stable type' : 생성 후 속성이 변경될 수 있으며, 런타임 중 속성이 변경되면 'Compose'는 변경 사항을 인지할 수 있습니다.

'skippable Composable'이 되려면 파라미터가 불변 타입인 것이 이상적이지만, Compose Runtime이 모든 상태 변경 사항을 인지할 수 있다면 'mutable type'도 'skippable Composable'이 될 수 있습니다.

이를 위해 Compose는 `MutableState`, `SnapshotStateMap`, `SnapshotStateList` 같은 가변 클래스를 제공합니다.  
위 클래스들은 Compose가 모든 변경을 감지하고, 필요한 경우 Composable을 자동으로 업데이트할 수 있도록 해줍니다.