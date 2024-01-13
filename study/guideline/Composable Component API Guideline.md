## Before you create a component

**새로운 컴포넌트 생성 시 고려사항**

- 각 컴포넌트는 단 하나의 문제를 해결해야 함, 여러 문제를 해결하는 컴포넌트는 더 작은 '서브 컴포넌트'나 'building block'으로 분할 되어야 함
- 컴포넌트 개발 전, 실제로 필요한지 그리고 장기적인 지원과 API 발전에 정당화한 가치를 갖는지 고려해야 함  
    때로는 라이브러리를 사용하는 개발자가 직접 컴포넌트를 작성하는 것이 더 효율적일 수 있음

### Component's purpose

> - 'Composable component'
>   - 단일 책임을 가져야 함, 즉 하나의 문제를 해결해야 함
>   - 하나 이상의 문제가 있는 경우, 하위 수준의 'building block'이나 '서브 컴포넌트'로 분할해야 함
> - Lower-level 'building block' or 'sub-component'
>   - 특정한 단일 기능을 가지며, 다른 컴포넌트와 쉽게 결합될 수 있도록 설계되어야 함
>   - 자체적으로 복잡한 기능을 수행하지 않지만, 서로 결합되어 더 크고 복잡한 UI 요소를 구성할 수 있음
>   - 예시 : `Text`, `Image`, `TextField` 등 단일 기능
> - Higher-level component
>   - Lover-level 'building block'이나 'sub-component'를 결합하여 더 큰 기능을 제공함
>   - 특정한 사용 사례나 기능에 맞춰져 있으며, 여러 하위 컴포넌트를 결합하여 '사용할 준비가 된 상태'를 제공함
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