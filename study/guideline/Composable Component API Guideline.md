## Before you create a component

**새로운 컴포넌트 생성 시 고려사항**

- 각 컴포넌트는 단 하나의 문제를 해결해야 함, 여러 문제를 해결하는 컴포넌트는 더 작은 '서브 컴포넌트'나 'building block'으로 분할 되어야 함
- 컴포넌트 개발 전, 실제로 필요한지 그리고 장기적인 지원과 API 발전에 정당화한 가치를 갖는지 고려해야 함  
    때로는 라이브러리를 사용하는 개발자가 직접 컴포넌트를 작성하는 것이 더 효율적일 수 있음

### Component's purpose

> - 'Composable component'
>   - 단일 책임을 가져야 함, 즉 하나의 문제를 해결해야 함
>   - 하나 이상의 문제가 있는 경우, 'lower-level building block'이나 'sub-component'로 분할해야 함
> - Lower-level 'building block' or 'sub-component'
>   - 특정한 단일 기능을 가지며, 'other component'와 쉽게 결합될 수 있도록 설계되어야 함
>   - 자체적으로 복잡한 기능을 수행하지 않지만, 서로 결합되어 더 크고 복잡한 UI 요소를 구성할 수 있음
>   - 예시 : `Text`, `Image`, `TextField` 등 단일 기능
> - Higher-level component
>   - 'lower-level building block'이나 'sub-component'를 결합하여 더 큰 기능을 제공함
>   - 특정한 사용 사례나 기능에 맞춰져 있으며, '여러 sub-component'를 결합하여 '사용할 준비가 된 상태'를 제공함
>   - 예시 : 로그인 폼, `TextField`(이메일 및 비밀번호 입력), `Button`(로그인 버튼), `Text`(Regex 오류) 등 조합

"새로운 컴포넌트 생성 시 고려사항"과 같이 컴포넌트는 하나의 문제만 해결하고, 그 곳에서 해결되야 됩니다.  
만약 컴포넌트가 하나 이상의 문제를 해결한다면, 해당 컴포넌트를 하위 계층이나 하위 컴포넌트로 분할할 수 있는지 고려해야 합니다.

하위 수준의 'building block'과 '서브 컴포넌트'는 간단하고 특정한 단일 기능을 가지며, 다른 컴포넌트와 쉽게 결합될 수 있도록 설계됩니다. 
예를 들어, `Text`, `Image`, `TextField` 등이 여기에 해당됩니다.  
이런 요소들은 자체적으로 복잡한 기능을 수행하지 않지만, 서로 결합되어 더 크고 복잡한 UI 요소를 구성할 수 있습니다.  
각각의 'building block'이 간단하고 명확한 기능을 가지므로, 이를 조합하여 새로운 기능을 만드는 것이 용이합니다.

상위 수준의 컴포넌트는 하위 수준의 'building block'이나 '서브 컴포넌트'를 결합하여 더 큰 기능을 제공합니다. 
예를 들어, 로그인 폼, 사용자 프로필 카드, 채팅 메시지 리스트 등이 있을 수 있습니다.  
상위 컴포넌트는 특정한 사용 사례나 기능에 맞춰져 있으며, 여러 하위 컴포넌트를 결합하여 '사용할 준비가 된 상태'를 제공합니다.  
로그인 폼은 `TextField`(이메일 및 비밀번호 입력), `Button`(로그인 버튼), `Text`(Regex 오류) 등 여러 하위 컴포넌트를 결합하여 만들 수 있습니다.

**Don't**

```kotlin
@Composable
fun Button(
    onClick: () -> Unit = { },
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit
)
```

**Do**

```kotlin
@Composable
fun Button(
    onClick: () -> Unit
)

@Composable
fun ToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
)
```

### Component layering

> - 'Composable component' 생성
>   - 순서 : 'lower-level building block' 생성 → 'higher-level component' 생성
>   - lower → higher 이동됨에 따라 구체적인 패턴이 제시되고, 커스터마이징 옵션을 줄여야 함

컴포넌트 생성 시, 컴포넌트가 작동하는 데 필요한 'single purpose building block'을 먼저 제공해야 합니다.  
또한 하위 수준에서 상위 수준으로 이동함에 따라 구체적인 패턴을 제시하고, 커스터마이징 할 수 있는 옵션을 줄여야 합니다. 

하위 수준의 컴포넌트는 개발자가 많은 부분을 자유롭게 조정할 수 있으며, 기본적이고 범용적인 기능을 제공하여 다양한 상황에 맞춰서 사용할 수 있도록 합니다. 
반면, 상위 수준의 컴포넌트는 특정한 사용 사례나 목적에 맞게 미리 설정된 옵션과 스타일을 가지고 있습니다. 이는 개발자가 더 빠르고 쉽게 특정 기능을 구현할 수 있지만 커스터마이징 할 수 있는 옵션은 적습니다. 

`@Composable`은 Compsoe에서 쉽게 생성될 수 있도록 설계 되었으므로, 개발자가 단일 목적 컴포넌트를 생성하고 필요에 따라 조정할 수 있습니다.

**Do**

```kotlin
// single purpose building blocks component
@Composable
fun Checkbox(...) { ... }

@Composable
fun Text(...) { ... }

@Composable
fun Row(...) { ... }

// high level component
@Composable
fun CheckboxRow() {
    Row {
        Checkbox(...)
        Text(...)
    }
}
```

### Do you need a component?

컴포넌트를 생성하기 전 실제로 필요한지, 아래와 같은 고민을 해야 합니다.

- 상위 컴포넌트 생성 전, 실제로 해결할 문제가 있는지 또는 기존에 있는 'building block'으로 해결 가능한 지?
  - 단순한 기능 추가로 인한 새로운 컴포넌트 생성은 비효율적
- 상위 컴포넌트 생성 전, 'basic building block'으로 컴포넌트로 구현 가능한 지?
  - 구현이 가능하다면, 이는 더 큰 유연성을 가지며, 불필요한 복잡성을 피할 수 있음
- 컴포넌트를 직접 구현하는 것보다, 상위 컴포넌트를 생성하여 사용하는 것이 더 나은 가치를 제공하는 지?
  - 컴포넌트 생성 시, 해당 컴포넌트의 구현을 배워야만 하는 부담이 있을 수 있음

예를 들어, `RadioGroup` 컴포넌트를 만들 때, 다양한 레이아웃과 데이터 유형을 지원해야 하기 위해, 다음과 같이 설계 할 수 있습니다.

```kotlin
@Composable 
fun <T> RadioGroup(
    options: List<T>,
    orientation: Orientation,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    optionContent: @Composable (T) -> Unit
)
```

위 `RadioGroup` 컴포넌트를 'basic building block'을 사용하여 다음과 같이 작성할 수 있습니다.

```kotlin
Column(
    modifier = Modifier.selectableGroup()
) {
    options.forEach { item -> 
        Row(
            modifier = Modifier.selectable(
                selected = (select.value == item),
                onClick = { select.value = item }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = item.toString())
            RadioButton(
                selected = (select.value == item),
                onClick = { select.value = item }
            )
        }
    }
}
```

위 예시와 같이 `Row`, `Text`, `RadioButton` 등의 'basic building block'을 사용하여 직접 구현할 수 있습니다.  
이런 구현은 필요한 레이아웃을 정의하거나 커스터마이징이 가능한 유연성을 얻을 수 있습니다.  
이로 인해, `RadioGroup`을 도입하는 것이 좋지 않은 선택일 수 있습니다.

이처럼, 새로운 컴포넌트 생성은 개발, 테스팅, 장기적인 지원 및 API 업데이트 등 대한 비용이 많이 발생할 수 있기에,
새로운 컴포넌트를 만드는 것이 실제로 필요한지, 그리고 그 가치가 이런 비용을 정당화할 수 있는지 고려해야 합니다.

### Component or Modifier

> - Component 사용 시기
>   - 'UI 구조적 변경' 및 'other component에 적용 불가능한 독특한 UI 구성'이 필요한 경우
>   - 'Composable' 계층 구조 변경이 필요한 경우 (enter/leave Composition)
> - Modifier 사용 시기
>   - 임의의 단일 컴포넌트에 특정 동작이나 기능을 추가하는 경우

다른 컴포넌트에 적용할 수 없는 독특한 UI 또는 UI의 구조적 변경('add/remove other component')이 필요한 경우에만 컴포넌트를 만들어야 합니다.

그 외, 임의의 단일 컴포넌트에 동작이나 기능을 추가해야 하는 경우, `Modifier`를 사용해야 합니다.  
이는, 특정 기능을 동시에 여러 UI 컴포넌트에 적용할 때 예상치 못한 동작을 할 수 있으므로, `Modifier`를 사용하는 것이 적절합니다.

**Don't**

```kotlin
@Composable
fun Padding(allSides: Dp) { 
    // Impl 
}

Padding(12.dp) {
    UserCard()
    UserPicture()
}
```

**Do**

```kotlin
fun Modifier.padding(allSides: Dp): Modifier = // implementation

UserCard(modifier = Modifier.padding(12.dp))
```

특정 기능이 어떤 'Composable'에도 적용이 가능하지만, 'Composable' 계층 구조를 변경해야 하는 경우(enter/leave Composition)
`Modifier`는 계층 구조를 변경할 수 없으므로, 컴포넌트를 사용해야 합니다.

**Do**

```kotlin
@Composable
fun AnimatedVisiblity(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // implementation
}

// usage
AnimatedVisiblity(visible = false) { 
    UserCard()
}
```