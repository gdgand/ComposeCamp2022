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

---

## Name of a Component

### BasicComponent vs Component

> - BasicComponent
>   - 디자인이나 추가적인 기능이 최소화 되어 있으며, 컴포넌트의 기본적인 기능을 제공함
>   - 필요한 스타일링 또는 기능을 추가하여 프로젝트 요구사항에 맞게 커스터마이징 할 수 있음
> - Component
>   - 특정 디자인 시스템이나 스타일링을 가지고 있으며, 추가적인 작업 없이 바로 사용 가능
>   - 더 복잡한 디자인 가이드라인을 따르며, 프로젝트의 일관된 UI 구성을 위해 사용 

'Basic' 접두사를 가진 컴포넌트는 가장 간단한 형태로 구현되어 제공합니다. 즉, 복잡한 스타일이나 추가적인 기능을 포함하지 않습니다. 
이는 개발자가 'BasicComponent'에 스타일링이나 기능을 추가하여 커스터마이징 할 수 있음을 의미합니다.  

이와 대조적으로 접두사가 없는 컴포넌트는 어떠한 디자인 시스템이나 스타일링을 가지고 있으며, 바로 사용할 준비가 된 컴포넌트를 나타냅니다.
즉, 추가적인 커스타마이징 없이도 바로 사용될 수 있으며, 더 복잡하고 구체적인 디자인 가이드라인이 적용된 컴포넌트 입니다.

**Do**

```kotlin
@Composable
fun BasicTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    ...
)

@Composable
fun TextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    ...
)
```

### Design, Usecase or Company/Project specific prefixes

> - Component 이름에 Company, Module 이름 등의 접두사를 사용하지 않는 것이 좋음
> - Compose Layer 중 'foundation', 'ui' 등을 기반으로 컴포넌트 구축 시, 비접두사로(`Button`, `Icon` 등) 제공 가능
> - 컴포넌트 래핑 또는 다른 디자인 시스템을 확장하여 컴포넌트 구축 시, 'Usecase'에서 파생된 이름 사용 권장
>   - 불가능하거나, 기존 컴포넌트와 충돌 발생 시, `GlideImage`와 같이 '모듈/라이브러리' 접두사 사용 가능
> - 특정 디자인 시스템이 유사한 컴포넌트를 제공하지만 다양한 스타일을 갖는 경우, 'specification' 접두사 사용 권장
> - 여러 컴포넌트에서 각각 다른 접두사를 가지고 있다면, 기본 컴포넌트를 선택하여 접두사 없이 사용 권장

컴포넌트 이름에 회사 이름(GoogleButton)이나 모듈 이름(WearButton)과 같은 접두사를 사용하는 것을 피하는 것이 좋습니다.  
이는 컴포넌트의 범용성을 제한하고, 특정 회사나 모듈에만 국한된 느낌을 줄 수 있습니다.  
필요한 경우, 컴포넌트가 수행하는 'UseCase 또는 Domain'을 반영하는 이름을 사용하는 것이 좋습니다.

만약 'compose-foundation'이나 'compose-ui'와 같은 'basic building block' 컴포넌트를 기반으로 구축 하는 경우,   
대부분의 비접두사 이름(`Button`, `Icon` 등)은 개발자에게 충돌 없이 제공 될 수 있습니다.  
이러한 이름은 컴포넌트가 프로젝트 내에서 중요하고 기본적인 요소로 인식될 수 있도록 합니다. ('first-class' 처럼 느끼기 해줌)

'building block' 컴포넌트를 래핑하거나, 'Material'과 같이 다른 디자인 시스템을 기반으로 구축하는 경우, 
`ScalingLazyColumn`, `CurvedText`과 같이, 'Usecase'에서 파생된 이름을 사용하는 것을 권장합니다.  
만약 'Usecase' 기반 이름 사용이 불가능하거나, 새로운 컴포넌트가 기존 컴포넌트와 충돌하는 경우, 
`GlideImage`와 같이 '모듈/라이브러리' 접두사를 사용할 수 있습니다.

특정 디자인 시스템이 유사한 컴포넌트를 제공하지만, 다양한 스타일을 갖는 경우,  
`ContainedButton`, `OutlinedButton`, `SuggestionChip`와 같이, 'specification' 접두사를 사용하여 '스타일' 패턴을 피하고 API를 단순하게 유지할 수 있습니다.

만약 여러 컴포넌트가 있고 각각 다른 접두사를 가지고 있다면, 그 중에서 가장 일반적으로 사용될 것으로 예상되는 컴포넌트를 선택하여 기본 컴포넌트로 선택하여 접두사 없이 사용하는 것이 좋습니다.
예를 들어, `Button`, `OutlinedButton`, `TextButton`과 같은 컴포넌트가 있다면, `Button`을 접두사 없이 사용하고, 나머지 컴포넌트는 접두사를 사용하는 것이 좋습니다.

**Do**

```kotlin
// ContainedButton.kt

// `ContainedButton`으로 명시 되었지만, 가장 일반적인으로 사용되는 버튼이므로 접두사 없이 사용
@Composable
fun Button(...)

@Composable
fun OutlineButton(...)

@Composable
fun TextButton(...)

@Composable
fun GlideImage(...)
```

'compose-foundation' 기반 라이브러리

```kotlin
// Package com.company.project
// 해당 컴포넌트들은 material, material3 의존 하지 않고, foundation 라이브러리 기반 구축

@Composable
fun Button(...) // 기본 버튼

@Composable
fun TextField(...) // 기본 텍스트 필드
```

---

## Component dependencies

### Prefer multiple components over style classes

> `ComponentStyle` 또는 `ComponentConfiguration`과 같은 일반적인 스타일 파라미터나 클래스 사용을 피하고, 
> 'styling' 또는 'Usecase'에 맞는 더 명확한 'Composable'을 제공하는 것이 좋음

`ComponentStyle` 이나 `ComponentConfiguration`과 같은 일반적인 스타일 파라미터나 클래스 사용을 피하고,
의존성을 세밀하고 의미있게 표현해야 합니다.

'동일 타입 서브 컴포넌트 집합'이 동일한 구성이나 스타일을 가져야 할 때, 
개발자는 컴포넌트를 래핑하거나 'lower-level building block'을 사용하여 맞춤형 컴포넌트를 만드는 것이 좋습니다. 
이렇게 하면 각 컴포넌트가 특정한 목적이나 스타일에 맞춰 조정될 수 있습니다.

`ComponentStyle`을 사용하여 다양한 컴포넌트 타입을 지정하는 대신, 
스타일링과 usecase에서의 차이를 나타내는 별도의 `@Composable` 함수를 제공하는 것이 좋습니다. 


**Don't**

```kotlin
class ButtonStyles(
    background: Color,
    border: BorderStroke,
    textColor: Color,
    shape: Shape,
    contentPadding: PaddingValues
)

val PrimaryButtonStyle = ButtonStyle(...)
val SecondaryButtonStyle = ButtonStyle(...)
val AdditiveButtonStyle = ButtonStyle(...)

@Composable
fun Button(
    onClick: () -> Unit,
    style: ButtonStyle = PrimaryButtonStyle
) {
    // implementation
}

// usage
val myLoginStyle = ButtonStyle(...)
Button(onClick = { /*...*/ }, style = myLoginStyle)
```

**Do**

```kotlin
@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    background: Color,
    border: BorderStroke,
    // other relevant parameters
) {
    // impl
}

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    background: Color,
    border: BorderStroke,
    // other relevant parameters
) {
    // impl
}

// usage 1:
PrimaryButton(
    onClick = { /*...*/ },
    background = Color.Blue,
    border = BorderStroke(1.dp, Color.Black)
)

// usage 2:
@Composable
fun MyLoginButton(
    onClick: () -> Unit
) {
    SecondaryButton(
        onClick = onClick,
        background = Color.Red,
        border = BorderStroke(1.dp, Color.Black)
    )
}
```

### Explicit vs Implicit dependencies

> - 컴포넌트의 '명시적 입력'은 동작 예측과 재정의, 테스트, 사용성을 향상시킴
>   - 명시적 입력 : Composable 파라미터 전달
> - 컴포넌트의 '암시적 입력'은 사용성을 복잡하게 하고, 값의 출처를 추적하기 어렵게 만듬
>   - 암시적 입력 : `CompositionLocal` 또는 `Local`을 통해 값 제공
>   - Theme, Typography, ColorScheme 등, Application-Screen 단위에 일관된 값을 제공해야 하는 경우 `CompositionLocal` 사용을 고려 할 수 있음
>   - `CompositionLocal` 사용 시, 'Composable' 파라미터의 기본 값으로 사용하여 개발자가 쉽게 재정의 할 수 있도록 해야 함

컴포넌트에서는 'explicit input'과 'configuration options'을 선호해야 합니다.   
컴포넌트의 'explicit input'은 컴포넌트의 동작을 예측하고, 조정하고, 테스트하고, 사용하기 쉽게 만듭니다.

`CompositionLocal` 또는 다른 유사한 메커니즘을 통해 제공되는 'implicit input'을 피해야 합니다.  
'implicit input'은 컴포넌트의 사용성을 복잡하게 하고, 개발자가 커스터마이징의 출처를 추적하기 어렵게 만듭니다.  
'implicit input'을 피하기 위해서는, 개발자가 원하는 'explicit input'의 부분 집합으로 커스터마이징 컴포넌트를 쉽게 만들 수 있게 해야 합니다.

**Don't**

```kotlin
// 특정 컴포넌트 커스터마이징을 위한 CompositionLocal 피하기
// CompositionLocal은 암시적이기에, 컴포넌트의 변경, 테스트, 사용을 어렵게 함
val LocalButtonBorder = compositionLocalOf<BorderStroke>(...)

@Composable
fun Button(
    onClick: () -> Unit,
) {
    val border = LocalButtonBorder.current
}
```

**Do**

```kotlin
@Composable
fun Button(
    onClick: () -> Unit,
    // explicit parameter, default value를 가짐 
    border: BorderStroke = ButtonDefaults.borderStroke,
) {
    // impl
}
```

MaterialTheme, Typography, ColorScheme 등과 같이 Application, Screen 전체에 일관되게 적용해야 하는 경우, `CompositionLocal`을 사용하여 암시적으로 제공할 수 있습니다.
이를 수행 할 때, `CompositionLocal`이 컴포넌트 파라미터의 기본 값으로만 사용되고, 개발자가 필요에 따라 쉽게 재정의 할 수 있도록 해야 합니다.
이는 개별 컴포넌트의 유연성과 전체 앱의 일관성을 유지하는데 도움이 됩니다.

따라서 컴포넌트는 자체 구현에서 직접적으로 `CompositionLocal`을 읽어오기보다는, 파라미터 기본 값에서 읽어오는 것이 좋습니다.

**Don't**

```kotlin
class Theme(val mainAppColor: Color)
val LocalAppTheme = compositionLocalOf { Theme(Color.Green) }

@Composable
fun Button(
    onClick: () -> Unit,
) {
    val buttonColor = LocalAppTheme.current.mainAppColor
    // use theme
}
```

**Do**

```kotlin
class Theme(val mainAppColor: Color)
val LocalAppTheme = compositionLocalOf { Theme(Color.Green) }

@Composable
fun Button(
    onClick: () -> Unit,
    backgroundColor: Color = LocalAppTheme.current.mainAppColor
) {
    // use theme
}
```

---

## Component parameters

### Parameters vs Modifier on the component

> '파라미터'는 컴포넌트의 기본 동작이나 특징 설정에 중점, `Modifier`는 스타일링 또는 레이아웃 조정에 중점

파라미터는 컴포넌트의 기본적인 동작이나 특징을 설정하는데 중점을 두어야 합니다.  
반면, `Modifier`는 스타일링 또는 레이아웃을 조정하는데 사용되어야 합니다.

**Don't**

```kotlin
@Composable
fun Image(
    bitmap: ImageBitmap,
    // 핵심 기능이 아닐 경우, `Modifier.clickble`을 통해 추가 가능 
    onClick: () -> Unit = { },
    modifier: Modifier = Modifier,
    // `Modifire.clip(CircleShape)`을 통해 지정 가능
    clipToCircle: Boolean = false
)
```

**Do**

```kotlin
@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary
)
```

### `modifier` parameter

> - 컴포넌트는 `Modifier` 타입 파라미터를 가져야 하며, 단 하나의 `Modifier` 파라미터만 가져야 함
> - 컴포넌트는 첫 번째 optional parameter 이어야 함
> - 컴포넌트는 no-op `Modifier`를 기본 값으로 가져야 함
> - 컴포넌트는 `Modifier`는 컴포넌트의 가장 루트에 가까운 레이아웃에 첫 번째로 한 번 적용되어야 함

UI를 생성하는 컴포넌트는 모두 `Modifier` 파라미터를 가져야 하며, 다음을 준수하여야 합니다.

- `Modifier` 타입을 가져야 합니다.
  - 컴포넌트가 다양한 `Modifier`를 수용할 수 있도록 하여, 컴포넌트 스타일링과 동작을 유연하게 할 수 있습니다.
- 첫 번째 'optional parameter' 이어야 합니다.
  - 컴포넌트가 기본 크기를 가진 경우, `modifier`를 선택 파라미터로 받을 수 있습니다.
  - 컴포넌트의 기본 크기가 0인 경우, `modifier`를 필수 파라미터로 받아야 합니다.
  - `modifier`는 모든 컴포넌트에 권장되어 자주 사용되므로, 이를 첫 번째로 두면 명명된 파라미터 없이 설정할 수 있으며, 모든 컴포넌트에서 일관성 있게 사용할 수 있습니다.
- no-op(연산 없음) `Modifier`를 기본 값으로 가집니다.
  - `modifier` 파라미터가 제공되지 않았을 때 컴포넌트의 기본 동작이나 외형에 영향을 주지 않도록 합니다.
- `Modifier` 타입의 파라미터가 하나만 있어야 합니다.
  - `Modifier`는 컴포넌트의 외형이나 동작을 수정하는데 사용되므로, 하나의 `Modifier` 만으로 충분합니다.
  - 여러 `Modifier`가 필요하다고 판단된 경우, 컴포넌트의 설계를 다시하는 것이 좋습니다. (예 : 컴포넌트를 두 개로 분할)
- `Modifier`는 컴포넌트의 가장 루트에 가까운 레이아웃에 첫 번째로 한 번 적용되어야 합니다.
  - `Modifier`가 체인의 첫 번째로 적용되면, 이를 기반으로 모든 후속 `modifier`를 적용합니다.
  - 파라미터로 전달된 `modifier`에 다른 `modifier`를 체인으로 연결할 수 있습니다.

**Why?**

Compose를 사용하는 개발자들은 `Modifier`를 통해 컴포넌트의 크기, 위치, 패딩, 클릭 이벤트 등을 쉽게 조절할 수 있음을 알고 있습니다.
본질적으로 파라미터로 입력되는 `modifier`는 외부 컴포넌트의 동작과 외형을 수정하는 방법을 제공하는 반면, 컴포넌트 구현에서는 내부 동작과 외형을 책임집니다.

**Don't**

```kotlin
// modifier parameter 없음
@Composable
fun Icon(
    bitmap: ImageBitmap,
    tint: Color = Color.Black,
)

// 첫 번째 modifier optional parameter가 아님
// 개발자가 modifier를 설정하는 즉시 padding이 손실됨
@Composable
fun Icon(
    bitmap: ImageBitmap,
    tint: Color = Color.Black,
    modifier: Modifier = Modifier.padding(8.dp),
)

// 아래 modifier들은 CheckboxRow 외부 동작을 지정하기 위해 의도된게 아닌, 
// 하위 부분을 수정하는 데 사용됨
@Composable
fun CheckboxRow(
    checked: Boolean,
    onChckedChange: (Boolean) -> Unit,
    rowModifier: Modifier = Modifier,
    checkboxModifier: Modifier = Modifier,
)

// modifier는 가장 가까운 루트 레이아웃에 첫 번째로 적용해야 하고,
// 이를 체인의 첫 번째 요소로 사용해야 함
@Composable
fun IconButton(
    buttonBitmap: ImageBitmap,
    modifier: Modifier = Modifier,
    tint: Color = Color.Black
) {
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            bitmap = buttonBitmap,
            tint = tint,
            modifier = Modifier
                .aspectRatio(1f)
                .then(modifier)
        )
    }
}
```

**Do**
```kotlin
@Composable
fun IconButton(
    buttonBitmap: ImageBitmap,
    modifier: Modifier = Modifier,
    tint: Color = Color.Black
) {
    Box(
        modifier = modifier.padding(16.dp)
    ) {
        Icon(
            bitmap = buttonBitmap,
            tint = tint,
            modifier = Modifier.aspectRatio(1f)
        )
    }
}

@Composable
fun ColoredCanvas(
    modifier: Modifier,
    color: Color = Color.White,
) {
    Box(
        modifier = modifier.background(color)
    ) {
        // ...
    }
}
```

### Parameters order

> - Component - parameter order
>   1. Required parameters
>   2. Single modifier: Modifier = Modifier
>   3. Optional parameters
>   4. (optional) trailing @Composable lambda
> - Required•Optional parameter group order
>   - 컴포넌트 데이터 우선순위에 따라 순서 조정 필요, 그 다음 metaData, customisation 파라미터 배치
>   - 의미적으로 관련된 파라미터 그룹화 (ex. 'color', 'arrangement-alignment' 등)

컴포넌트의 파라미터 순서는 다음과 같아야 합니다.

1. Required parameters.
2. Single modifier: Modifier = Modifier.
3. Optional parameters.
4. (optional) trailing @Composable lambda.

**Why?**

'required parameter'들은 컴포넌트의 핵심 기능을 위해 반드시 필요하므로, 개발자에게 해당 파라미터들을 제공해야만 컴포넌트가 제대로 동작함을 알리기 위해 가장 먼저 배치합니다.

'modifier parameter'는 UI 요소의 레이아웃, 스타일링, 동작을 조정하는데 사용됩니다.  
이는 컴포넌트의 외관과 동작에 중요한 영향을 미치므로 'first optional parameter'로 배치합니다.

'optional parameter'들은 컴포넌트의 추가 기능이나 커스터마이징을 위해 사용됩니다.  
개발자는 이 파라미터들을 필수적으로 제공할 필요는 없지만, 필요에 따라 재정의 할 수 있습니다.  
이들은 'required parameter'와 'modifier parameter' 다음에 배치함으로써, 
개발자가 컴포넌트의 기본 동작에 먼저 집중하고, 필요에 따라 추가 설정을 할 수 있도록 합니다.

'`@Composable` lambda'는 컴포넌트의 콘텐츠를 나타내며, 'content'로 명명됩니다.  
개발자에게 컴포넌트 안에 자신의 UI 콘텐츠를 제공할 수 있게 해주는 옵션입니다.  
`LazyColumn`과 같은 DSL 형식 컴포넌트에서는 `@Composable`이 아닌 람다를 사용하는 것이 허용됩니다.

---

'required' 및 'optional' 파라미터 그룹 내에서, 컴포넌트의 'What' 데이터 우선순위가 있을 것입니다.  
이런 우선순위를 고려하여 파라미터 그룹 내에서 순서를 정리하고, 그 다음 메타 데이터, 커스터미이징 등의 'How' 파라미터를 배치하는 것이 좋습니다.

또한 'required' 및 'optional' 파라미터 그룹 내에서 파라미터를 의미적으로 그룹화하는 것이 좋습니다.  
예를 들어, 여러 색상 파라미터('backgroundColor', 'contentColor')가 있다면, 커스터마이징 옵션을 쉽게 볼 수 있도록 이들을 함께 배치하는 것이 좋습니다.

**Do**

```kotlin
@Composable
fun Icon(
    // 아이콘 표현을 위한 필수 데이터, 가장 먼저 위치 
    bitmap: ImageBitmap,
    // 접근성을 위한 메타 데이터, 필수 데이터 다음에 위치
    contentDescription: String?,
    // modifier param = first optional parameter
    modifier: Modifier = Modifier,
    // optional param, Theme or CompositionLocal에서 제공되는 기본 값
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
)

@Composable
fun LazyColumn(
    // 'required param'이 없기에, 'modifier param'이 첫 번째로 위치 
    modifier: Modifier = Modifier,
    // list의 상태를 나타내며, '데이터'로 분류되기에 두 번째로 위치
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    // arrangement 및 alignment는 연관되어 있기에 함께 위치 
    verticalArrangement: Arrangement.Vertical = 
      if(!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    // 'trailing lambda' 콘텐츠, 마지막에 위치
    content: LazyListScope.() -> Unit
)
```

### Nullable parameter

> - 'nullable parameter' 도입 시, 파라미터 상태(default, empty, absent)를 구분하고, 어떤 의미인지 이해하는 것이 중요
>   - 'absent'로 표현 시, 'optional parameter'로 유연성을 제공할 수 있음 (ex : Image param - contentDescription)
>   - `default` 구현하라는 신호로 `null`을 활용하는 'nullable parameter' 생성 X
>   - 'empty'로 표현 시, 명확한 'default value'를 사용하여 표현하는 것이 좋음 (ex : backgroundColor = Color.White)

'nullable parameter' 도입 시, 파라미터가 가질 수 있는 다양한 상태(default, empty, absent)를 명확히 구분하고, 
이들이 각각 어떤 의미로 전달되는지를 이해하는 것이 중요합니다. 

'Nullability parameter'는 개발자에게 해당 파라미터가 'absence' 할 수 있음을 표현 할 수 있습니다.  
이는 파라미터가 선택적일 수 있음을 의미하며, 이를 통해 유연성을 제공할 수 있음을 의미합니다.  
그러나, 이는 잠재적인 'null' 관련 오류 가능성을 증가시킬 수 있으므로 신중하게 사용되어야 합니다.

'default value'를 구현하라는 신호로 `null`을 활용하는 'nullable parameter'를 만들면 안됩니다.  
이는 컴포넌트의 사용을 복잡하게 만들고, 예기치 않은 오류를 발생시킬 수 있습니다.

값이 존재하지만 비어 있다는 신호로 'nullable parameter'를 만들면 안됩니다.  
대신에, 명확하게 'empty' 상태를 나타내는 'default value'를 사용하는 것이 좋습니다.  
예를 들어, 문자열 파라미터의 경우, 'empty' 상태를 나타내는 `""`를 사용하는 것이 좋습니다.

**Don't**

```kotlin
@Composable
fun IconCard(
    bitmap: ImageBitmap,
    elevation: Dp? = null,
) {
    val resolvedElevation = elevation ?: DefaultElevation
}
```

**Do**

```kotlin
@Composable
fun IconCard(
    bitmap: ImageBitmap,
    elevation: Dp = 8.dp,
) {
    // ...
}
```

**Or Do (null is meaningful here)**

```kotlin
@Composable
fun IconCard(
    bitmap: ImageBitmap,
    // null일 경우, contentDescription을 제공하지 않음을 의미
    contentDescription: String?,
) {
    // ...
}
```

### Default expressions

> - 'optional parameter'에 대한 'default expressions' 설정 시, 다음 관행을 따르는 것이 좋음
>   - 'default expressions'는 `private/internal` 호출 포함 X
>   - 'default expressions'는 'meaningful default value' 이여야 함
>     - 파라미터나 속성의 기본 값이 다른 개발자에게 직관적, 예측 가능, 합리적인 값을 가짐을 의미
>   - 'absence'를 나타내는 경우 `null` 허용, 'default value'를 사용하기 위한 '마커'로 `null` 사용 X
> - 'multiple default expressions'가 있는 경우, `ComponentDefaults`를 통해 '네임스페이스' 제공 권장

개발자들은 'optional parameter'에 대한 기본 표현식(default expressions)을 설정할 때 다음 관행을 따르는것이 좋습니다.

기본 표현식은 `private/internal` 호출을 포함해서는 안됩니다.   
이는 다른 개발자들이 해당 컴포넌트를 확장하거나 래핑할 때 동일한 '기본 값'을 제공할 수 있게 합니다.  
또는 개발자가 `if`문에서 해당 '기본 값'을 사용할 수 있습니다. : `if (condition) default else myUserValue` 

기본 표현식은 의미가 있고 명확해야 합니다.  
내부적으로 값의 'absence'를 나타내는 경우 `null`을 사용해야 하며, '기본 값'을 사용하기 위한 '마커'로 `null`을 사용하는 것은 피해야 합니다. 

여러 기본 표현식이 있는 경우, `ComponentDefaults` 객체를 사용하여 '네임스페이스'를 제공하는 것이 좋습니다.

**Don't**

```kotlin
@Composable
fun IconCard(
    bitmap: ImageBitmap,
    backgroundColor: Color = DefaultBackgroundColor,
    // null을 기본값으로 사용하여 '마커'로 사용하는 것을 피해야 함
    elevation: Dp? = null
) {
    val resolvedElevation = elevation ?: DefaultElevation
}

// 컴포넌트 래핑 시, 접근 할 수 없어 코드를 이해하기 어렵고, 수정하기 어려움 (private)
private val DefaultBackgroundColor = Color.White
private val DefaultElevation = 8.dp
```

**Do**

```kotlin
@Composable
fun IconCard(
    bitmap: ImageBitmap,
    backgroundColor: Color = IconCardDefaults.BackgroundColor,
    elevation: Dp = IconCardDefaults.Elevation
) {
    // ...
}

object IconCardDefaults {
    val BackgroundColor = Color.White
    val Elevation = 8.dp
}
```

컴포넌트 파라미터가 기본값이 짧고 예측 가능한 경우(`elevation = 0.dp`), 
`ComponentDefaults` 객체를 생략하고 간단한 인라인 상수를 사용할 수 있습니다.

### MutableState<T> as a parameter

> - `MutableState<T>` 파라미터 사용은 컴포넌트와 호출자 간 공동으로 상태를 소유하는 것을 유도하므로, 권장되지 않음  
>   - 가능하면 stateless 컴포넌트로 만들고 호출자에게 상태 변경을 위임하는 것이 좋음 
>   - 만약 컴포넌트에서 호출자가 소유한 상태 변경이 필요한 경우, `ComponentState` 클래스를 만들어 사용하는 것이 좋음 

`MutableState<T>` 타입의 파라미터 사용은 컴포넌트와 호출자 간의 상태에 대한 공동 소유권을 유도하므로, 권장되지 않습니다.
가능하다면 'stateless 컴포넌트'로 만들고 호출자에게 상태 변경을 위임하는 것이 좋습니다.  
만약 컴포넌트에서 호출자가 소유한 속성의 변경이 필요한 경우, `MutableState<T>`가 아닌, `ComponentState` 클래스를 만들어 사용하는 것이 좋습니다.

컴포넌트가 `MutableState<T>`를 파라미터로 받으면 상태를 변경하는 능력을 얻게 됩니다.  
이는 상태 소유권이 분리되어 있어, 상태를 소유하고 있던 호출자는 컴포넌트 구현 내에서 언제 어떻게 변경될지 제어할 수 없게 됩니다.

**Don't**

```kotlin
@Composable
fun Scroller(
    offset: MutableState<Float>
) { ... }
```

**Do (stateless version, if possible)**

```kotlin
@Composable
fun Scroller(
    offset: Float,
    onOffsetChange: (Float) -> Unit
) { ... }
```

**Or do (state-based component version, if stateless not possible)**

```kotlin
class ScrollerState {
    val offset: Float by mutableStateOf(0f)
}

@Composable
fun Scroller(
    state: ScrollerState
) { ... }
```

### State<T> as a parameter

> - `State<T>` 타입 파라미터는 객체 타입을 불필요하게 제한하므로 권장되지 않음, 대신 다른 대안 사용 권장
>   - `param: Float` : 상태 변경이 빈번하지 않고, 단순하게 상태 제공을 위해 사용
>   - `param: () -> Float` : 'delay read'를 통해 필요할 때만 값을 읽고, [불필요한 작업](../UI%20Architecture/Compose%20Phases.md#phases-state-reads)을 피할 수 있음
>     - `param = { myState.value }` : `State<T>`의 값을 읽음
>     - `param = { justValueWithoutState }` : `State<T>`를 사용하지 않는 단순 값
>     - `param = { myObject.offset }` : `mutableStateOf()`로 지원되는 커스텀 상태 객체

`State<T>` 타입 파라미터 사용은 컴포넌트에 전달되는 객체 타입을 불필요하게 제한하기에 권장되지 않습니다.  
이에 따라 `param: State<Float>` 대신, 2가지 대안이 있습니다. 

- `param: Float` : 파라미터가 자주 변경되지 않거나 컴포넌트에서 즉시 읽는 경우, 단순하게 파라미터를 제공하고, 변경 시 컴포넌트를 recompose 합니다.
- `param: () -> Float` : 나중에 `param.invoke()`를 통해 값을 읽을 수 있도록 람다를 파라미터로 제공합니다.  
  이는 컴포넌트의 개발자가 필요할 때만 값을 읽을 수 있으므로, 불필요한 작업을 피할 수 있습니다.  
  예를 들어, 그리기 작업 중에만 값을 읽는다면, 그리기 작업 중에만 다시 그리기가 발생합니다.  
  이는 개발자에게 `State<T>`의 값을 읽는 표현식을 제공할 수 있는 유연성을 남겨 줍니다.
  - `param = { myState.value }` : `State<T>`의 값을 읽음
  - `param = { justValueWithoutState }` : `State<T>`를 사용하지 않는 단순 값
  - `param = { myObject.offset }` : `mutableStateOf()`로 지원되는 커스텀 상태 객체

**Don't**

```kotlin
fun Badge(position: State<Dp>) {}
Badge(position = scrollState.offset)
```

**Do**
```kotlin
val myState = mutableStateOf(10f)
Badge(position = { myState.value })

// or

val justValueWithoutState = 15f
Badge(position = { justValueWithoutState })

// or

val state = rememberListState()
Badge(position = { state.offset })

/// impl

fun Badge(position: () -> Float) {
  val currentPosition = position()
  // ...
}
```

---

## Slot parameters

### What are slots

> Slot : 컴포넌트의 특정 하위 계층을 지정하는 `@Composable`의 람다 파라미터  
> 'Slot pattern'은 컴포넌트가 주요 기능을 유지하면서, 하위 계층 구성에 유연성을 가지기에 맞춤형 UI 컴포넌트 생성이 간단함

슬롯은 컴포넌트의 특정 하위 계층을 지정하는 `@Composable`의 람다 파라미터를 말합니다.  
예를 들어, `Button`의 'content slot'은 다음과 같습니다.

```kotlin
@Composable
fun Button(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) { ... }

// usage
Button(onClick = { /*...*/ }) {
    Text("Button")
}
```

이러한 패턴은 `Button` 컴포넌트가 내부의 'content'에 대해 특정 요구사항이나 스타일을 가지지 않으며, 기본적인 외관을 제공하고, 'click'과 'ripple'을 처리하도록 합니다. 
즉, 'Slot pattern'은 컴포넌트가 자신의 주요 기능을 유지하면서, 그 내용을 유연하게 변경할 수 있게 해주며, 맞춤형 UI 컴포넌트를 쉽게 만들 수 있게 해줍니다.

### Why slots

> - 'Slot pattern'이 아닌, 특정 타입을 파라미터로 받는 컴포넌트는 다음과 같은 유연성 문제가 있을 수 있음
>   - 스타일링 선택 제한 : `String`만 받는다면, `AnnotatedString`이나 다른 텍스트 정보를 사용할 수 없음
>   - 컴포넌트 선택 제한 : `String`만 받는다면, 로깅 이벤트를 하는 컴포넌트를 사용할 수 없음
>   - 오버로드 폭발 : 컴포넌트 유연성을 위해, `ImageBitmap`과 `VectorPainter`를 모두 받을 수 있도록 하려면, 이를 위한 추가 구현이 필요하며, 다른 타입이 더 있는 경우에는 더 많아 질 수 있음
>   - 컴포넌트 레이아웃 기능 제한 : 컴포넌트 내부에서 '배치'를 하기에, 패딩 추가 등과 같은 커스터마이징 불가능

`Text`와 `Icon`이 포함된 `Button` 컴포넌트 작성 시, 
다음과 같이 작성하는 것이 솔깃 할 수 있지만, 이는 권장되지 않습니다.

**Don't**

```kotlin
@Composable
fun Button(
    onClick: () -> Unit,
    text: String? = null,
    icon: ImageBitmap? = null,
)
```

이 경우, `Button`은 `text`와 `icon`을 받아, 둘 다 사용하거나, 둘 다 사용하지 않거나, 둘 중 하나만을 사용 할 수 있습니다.  
이런 방식은 기본적인 사용 사례에서는 잘 동작하지만, 아래와 같은 몇 가지 근본적인 유연성 문제가 있습니다.

스타일링 선택 제한 : `Button`은 `String`만 받기 때문에, `AnnotatedString`이나 다른 텍스트 정보를 사용할 수 없습니다.   
이를 위해 `Button`은 `TextStyle` 파라미터를 추가해야 하며, 이는 컴포넌트를 복잡하게 만듭니다.

컴포넌트 선택 제한 : `Button`에서 `MyTextWithLogging()`를 통해 '로깅 이벤트' 같은 추가 로직을 수행하고 싶을 수 있습니다.
여기서 `String`만을 받게되면 개발자는 `Button`을 수정 해야하는 상황에 직면하게 됩니다.

오버로드 폭발 : 컴포넌트 유연성을 위해, `Button`이 `ImageBitmap`과 `VectorPainter`를 모두 받을 수 있도록 하려면, 이를 위한 '오버로드가 추가'로 필요합니다.
이는 `text` 파라미터도 `String`, `AnnotatedString`, `CharSequence` 등 여러 유형을 지원하는 경우에도 동일합니다.

컴포넌트 레이아웃 기능 제한 : `Button`은 텍스트와 아이콘을 표시할 수 있지만, 텍스트와 아이콘의 '배치'는 `Button`이 결정합니다.
이는 곧, 텍스트와 아이콘 사이에 패딩을 추가하는 등의 커스터마이징이 불가능함을 의미합니다.

---

슬롯을 가진 컴포넌트들은 위 문제들로부터 자유로워 슬롯에 어떤 컴포넌트와 어떤 스타일링도 넣을 수 있게됩니다.   
이는 컴포넌트의 유연성을 높이고, 컴포넌트의 사용성을 높이며, 컴포넌트의 재사용성을 높일 수 있습니다.

**Do**

```kotlin
@Composable
fun Button(
    onClick: () -> Unit,
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
)
```

### Single 'content' slot overloads

여러 슬롯을 가지는 컴포넌트의 경우, 'content'로 명명된 'single slot' 오버로드를 제공하는 것이 좋습니다.
이는 레이아웃 로직을 변경 할 수 있기에, 사용 측면에서 더 많은 유연성을 제공합니다.

**Do**

```kotlin
@Composable
fun Button(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) { ... }

// usage
Button(onClick = { /*...*/ }) {
    Row {
        Icon(...)
        Text(...)
    }
}
```

### Layout strategy scope for slot APIs

> - 'single content overload'의 경우, 컴포넌트 사용성과 유연성을 높이기 위해 알맞은 레이아웃 전략 선택이 중요함  
> - 컴포넌트의 사용 패턴을 기반으로 `RowScope`, `ColumnScope`, `BoxScope` 등 레이아웃 전략 선택

'single content overload'의 경우, 컴포넌트를 쉽고 효과적으로 사용할 수 있도록 적합한 레이아웃 전략(layout strategy)를 선택하는 것이 중요합니다.
이는 컴포넌트의 사용성과 유연성을 높이는데 도움이 됩니다.

위 예시에서 `Button` 컴포넌트는 일반적으로 '단일 텍스트', '단일 아이콘', '행에 있는 아이콘과 텍스트', '행에 있는 텍스트와 아이콘' 등으로 사용 할 수 있습니다.
이런 사용 패턴을 기반으로, `RowScope`를 제공하면 `Button` 컴포넌트의 사용이 좀 더 쉬워집니다.

**Do**

```kotlin
@Composable
fun Button(
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) { ... }

// usage
Button(onClick = { /*...*/ }) {
    Icon(...)
    Text(...)
}
```

컴포넌트에 대한 다른 타입의 레이아웃 전략으로 `ColumnScope`와 `BoxScope` 등이 있습니다.  
컴포넌트의 저자는 슬롯에 여러 컴포넌트가 전달될 때 어떤 일이 발생할 지 항상 생각해야 하며, `Scope`를 통해 이런 행동을 사용자에게 전달하는 것을 고려해야 합니다.

### Lifecycle expectations for slot parameters

> - 슬롯 파라미터로 사용되는 'Composable lifecycle'은 다음을 따라야 함
>   - 해당 슬롯을 사용하는 '컴포넌트의 생명주기'와 '동일'해야 함
>   - 해당 슬롯을 사용하는 컴포넌트가 'View port'에서 보여지는 동안 슬롯 파라미터 'Composable'은 유지되어야 함
>   - 해당 슬롯을 사용하는 컴포넌트의 구조적 또는 시각적 변경에 따라 폐기되거나 다시 구성되어서는 안됨
>     - 내부 구조 변경이 필요한 경우, `remember{}`와 `movableContentOf()`를 사용해야 함

슬롯 파라미터로 사용되는 'Composable'은 해당 슬롯을 포함하는 컴포넌트의 생명주기와 동일하고, 
슬롯 파라미터의 생명주기는 컴포넌트가 'viewport'에서 가시성을 유지하는 동안 유지되어야 합니다.

슬롯 파라미터로 사용되는 'Composable'은 컴포넌트의 구조적 또는 시각적 변경에 따라 폐기되고 다시 구성되어서는 안됩니다.

슬롯 컴포저블의 생명주기에 영향을 미치는 내부 구조 변경이 필요한 경우, `remember{}`와 `movableContentOf()`를 사용해야 합니다.

**Don't**

```kotlin
@Composable
fun PreferenceItem(
    checked: Boolean,
    content: @Composable () -> Unit
) {
    if (checked) {
        Row {
            Text("Checked")
            content()
        }
    } else {
        Column {
            Text("Unchecked")
            content()
        }
    }
}
```

**Do**

```kotlin
@Composable
fun PreferenceItem(
    checked: Boolean,
    content: @Composable () -> Unit
) {
    Layout({
        Text("Preference item")
        content()
    }) {
        // checked 상태 변경에 따라 `content` 인스턴스를 다시 레이아웃
    }
}
```

**Or Do**

```kotlin
@Composable
fun PreferenceItem(
    checked: Boolean,
    content: @Composable () -> Unit
) {
    // `row`와 `column`에서 `content` 생명주기 유지, 불필요한 재구성 방지
    val movableContent = remember(content) { movableContentOf(content) }
    
    if (checked) {
        Row {
            Text("Checked")
            movableContent()
        }
    } else {
        Column {
            Text("Unchecked")
            movableContent()
        }
    }
}
```

**Do**

```kotlin
@Composable
fun PreferenceItem(
    checked: Boolean,
    checkedContent: @Composable () -> Unit
) {
    // `checkedContent`는 체크된 상태에서만 보이기에, 
    // 해당 슬롯이 존재하지 않을 때 폐기되고, 다시 존재할 때 다시 구성되는 것은 괜찮음
    if (checked) {
        Row {
            Text("Checked")
            checkedContent()
        }
    } else {
        Column {
            Text("Unchecked")
        }
    }
}
```