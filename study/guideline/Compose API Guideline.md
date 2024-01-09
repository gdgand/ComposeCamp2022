## Kotlin Style

### Baseline style guidelines

> - Compose는 kotlin 기반이기에 기본적으로 kotlin coding-conventions을 따라야 함
>   - 'Declarative', 'Reactive' 패러다임을 올바르게 적용하면서 Compose 개념을 효과적으로 표현하기 위해
>   - 'Reactive' 패러다임에 맞게 'Data Flow'를 효율적으로 관리하고 명확하게 표현하기 위해
>   - 기존 Kotlin 코드의 일관성과 가독성을 유지하면서 Compose만의 스타일과 패턴을 적절히 통합하기 위해
> - 'singleton', 'constant', 'sealed class, 'Enum class value'를 PascalCase로 작성해야 함
>   - UI 요소가 동적으로 변할 수 있으므로, '코드 작성 방식'도 이러한 '동적 변화'를 '지원'해야 함
>   - 코드 일관성 유지를 위해 '모든 타입의 객체'가 '동일한 방식'으로 '처리'되어야 함

---

**1. Compose는 'kotlin 기반' 새로운 패러다임과 개념을 도입하였기에 기존의 'kotlin coding-conventions'을 따르는 것이 중요합니다.**

**Why?**

1-1. Compose는 기존 '명령형 프로그래밍'에서 '선언형 프로그래밍'과 '반응형 프로그래밍' 이라는 새로운 'mental model'을 도입했습니다. 
이런 새로운 패러다임을 '올바르게 적용'하기 위해선 'kotlin coding-conventions'을 지키면서 Compose 개념을 효과적으로 표현해야 합니다.

1-2. Compose는 반응형 프로그래밍 패러다임에 따라서, 'Data Flow'에 맞춰 UI를 자동으로 업데이트 합니다.  
이런 'Data Flow'를 효율적으로 관리하고 명확하게 표현하기 위해선 'kotlin coding-conventions'을 따라야 합니다.

1-3. Compose는 'Kotlin 기본 문법과 기능'을 **확장**하여 UI 개발을 위한 새로운 방식을 제공합니다.  
이런 확장은 Compose 만의 고유한 패턴과 관례를 형성하기에, 'kotlin coding-conventions'을 따름으로써 기본적인 일관성과 가독성을 유지하면서, 
Compose만의 스타일과 패턴을 적절히 통합할 수 있습니다.

---

**2. 'singleton', 'constant', 'sealed class, 'Enum class value'를 PascalCase로 작성해야 합니다.**

**Why?**

2-1. Compose는 선언형 프로그래밍 패러다임을 따르기에, UI 구조와 레이아웃을 선언하고, 상태가 변경되면 UI를 자동으로 업데이트 합니다.
이 패러다임에서는 **UI 요소가 동적으로 변할 수 있으므로, 코드 작성 방식도 이러한 동적 변화를 지원**해야 합니다.  
'PascalCase'는 이러한 동적 UI 구성에 적합한 코드 스타일을 제공하며, 또한 Compose의 Reactive, Declarative 특성을 반영하여 일관되고 예측 가능한 코드 작성을 가능하게 합니다.

2-2. 전통적으로 'Singleton object'와 'constant'는 서로 다르게 취급되었습니다. 
따라서 'Singleton object'는 'CamelCase'로 작성하고, 'constant'는 'UPPER_SNAKE_CASE'로 작성했습니다. 
그러나, Compose에서는 이러한 구분이 크게 중요하지 않습니다.   
Compose에서는 코드 일관성을 유지하기 위해 모든 타입의 객체가 동일한 방식으로 처리되어야 합니다. 
따라서 'PascalCase'를 사용하면 'Singleton', 'constant', 'enum class', 'sealed class' 등 모든 타입의 객체를 동일한 방식으로 다룰 수 있습니다.

**Do**

```kotlin
const val DefaultKeyName = "key"
// const val DEFAULT_KEY_NAME = "__defaultKey"

val StructurallyEqual : ComparisonPolicy = StructurallyEqualsImpl()
// val STRUCTURALLY_EQUAL: ComparisonPolicy = StructurallyEqualsImpl()

object ReferenceEqual: ComparisonPolicy { ... }

sealed class LoadResult<T> {
    object Loading: LoadResult<Nothing>()
    class Success(val result: T): LoadResult<T>()
    class Error(val cause: Throwable): LoadResult<Nothing>()
}

enum class Color {
    Red, Green, Blue
}

// enum class Color {
//     RED, GREEN, BLUE
// }
```

---

## Compose baseline

Compose의 'compiler plugin'과 'runtime'은 kotlin 언어에 추가적인 기능을 제공 합니다.  
'compiler plugin'은 kotlin 코드를 분석하고, `@Composable`, '상태 관리' 등을 처리합니다.  
'runtime'은 이러한 코드가 실행될 때 필요한 동작을 관리합니다.  
예를 들어 실제 UI 요소들이 어떻게 보여지는지, 데이터 변화에 따라 적절히 UI를 업데이트 하도록 관리합니다.

Compose는 '선언형 프로그래밍 모델'을 제공하고, 이 모델은 시간에 따라 변화하는 가변 트리 데이터 구조를 구성하고 관리하는데 사용됩니다.  
이는 UI 요소들이 트리 구조의 노드로 표현되어 상호 연결됨을 의미합니다.

'Compose UI'는 'runtime'이 관리할 수 있는 트리의 한 예시입니다.  
이는 UI를 트리 구조로 조직화하고, 다양한 UI 요소들을 통합하여 복잡한 인터페이스를 구성할 수 있게 합니다.

### Naming Unit @Composable functions as entities

> - `@Composable`이 붙은 모든 함수는 'PascalCase'로 작성, `Unit`을 반환, 함수의 이름은 '명사'여야 함
>   - 'Composable'은 'declarative entities'로 간주되기에 '명사'를 사용하는 것이 적합
>   - 'Composable'은 UI에서 추가되거나 제거될 수 있지만, '일관된 Identity'를 유지되기에 '명사'를 사용하는 것이 적합

---

**`@Composable`이 붙은 모든 함수는 'PascalCase'로 작성, `Unit`을 반환, 함수의 이름은 '명사'여야 합니다.**

`Unit`을 반환하는 'Composable'은 **'declarative entities'로 간주**됩니다.  
즉, `Unit`을 반환하는 'Composable'은 'Composition'에서 UI의 일부로 존재하거나 존재하지 않을 수 있습니다.  
예를 들어, 특정 조건에 따라 UI의 특정 부분을 표시하거나 숨길 수 있습니다.  
이러한 특성으로 인해, `Unit`을 반환하는 'Composable'은 UI 구성 요소를 나타내는 실체로 볼 수 있기에, '명사'를 사용하는 것이 적합 합니다.

'Composable'의 존재 또는 부재는 해당 'Composable'을 호출하는 코드의 제어 흐름에 따라 결정됩니다.  
이는 'Composable'이 'ReComposition'을 거치는 동안 '일관된 Identity'를 유지하고, 해당 'Identity'에 대한 생명주기를 가진다는 것을 의미합니다.  
즉, 'Composable'은 상태 변화나 다른 요인에 따라 UI에서 추가되거나 제거될 수 있지만, **그 자체는 '일관된 entities'로 유지** 됩니다.  
이러한 특성으로 인해, 'Composable'이 UI 내에서 '특정 Entities'나 구성 요소를 대표한다는 개념과 일치하기에 '명사'를 사용하는 것은 'Composable'의 역할과 정체성을 명확하게 전달하는 데 도움이 됩니다.

**Do**

```kotlin
// 시각적인 UI 요소로써 PascalCase 명사입니다.
@Composable
fun FancyButton(text: String, onClick: () -> Unit)

// 비-시각적인 요소로써 PascalCase 명사입니다.
@Composable
fun BackButtonHandler(onBack: () -> Unit)
```

**Don't**

```kotlin
// PascalCase X, 명사 O 
@Composable
fun fancyButton(text: String, onClick: () -> Unit)

// PascalCase O, 명사 X
@Composable
fun RenderFancyButton(text: String, onClick: () -> Unit)

// PascalCase X, 명사 X
@Composable
fun drawProfileImage(image: Asset)
```

### Naming @Composable functions that return values

> - 값을 반환하는 Composable은 'Factory function exemption' 규칙을 사용하면 안됨
>   - 'Composable'의 역할과 정체성을 명확하게 전달하지 못함
>   - 'Composable'이 단순한 생성자처럼 객체를 생성하고 반환하는 것처럼 잘못 해석될 수 있음
>   - 'Virtual DOM'과 혼동되어, 'UI Entity'를 반환하는 것처럼 잘못 해석될 수 있음

`Unit`이 아닌 값을 반환하는 'Composable'은 'kotlin coding-convention'을 따라야 합니다.  
일반적인 'kotlin coding-convention'에서는 객체를 생성하고 반환하는 함수는 'Factory function exemption' 규칙을 허용합니다.  
이 규칙에 따르면, 함수는 생성되는 객체 타입을 반영하는 'PascalCase' 이름을 가질 수 있습니다.  

그러나 **Compose에서는 'Factory function exemption' 규칙을 사용해서는 안됩니다.**

**Why?**

1-1. 'Composable'은 단순히 객체를 생성하는 것이 아닌, 더 복잡한 역할을 수행하기에, 
'Factory function exemption' 규칙을 사용할 경우 'Composable'의 역할과 정체성을 명확하게 전달하지 못합니다.

1-2. 값을 반환하는 'Composable'은 Compose 생명주기에 맞추어 객체의 상태를 관리하거나, `CompositionLocal`을 사용하여 객체에 필요한 값을 제공하는 등의 역할을 수행할 수 있습니다.   
이런 경우에 'Factory function exemption' 규칙을 적용하면, 함수가 단순히 생성자처럼 객체를 생성하고 반환하는 것처럼 잘못 해석 될 수 있습니다. 
이는 'Composable'이 수행하는 실제 역할과 다를 수 있기에, 'Factory function exemption' 규칙을 사용해선 안됩니다. 

1-3. 'Composable'에 'Factory function exemption' 규칙을 적용하면, Web 프레임워크에서 사용되는 'Virtual DOM'과 혼동될 수 있습니다.  
'Virtual DOM'에서는 'UI Entity'가 메모리에 유지되며 상태 변경에 따라 자동으로 UI를 업데이트 합니다.   
그러나 'Composable'은 UI 구성이 선언적으로 이루어지며, 'UI Entity'의 반환이 아닌 '구성'을 중점으로 둡니다.  
이처럼 'Composable'에 'Factory function exemption' 규칙하면, 'UI Entity'를 반환하는 것처럼 잘못 해석될 수 있습니다.  
이런 방식은 선언형 프로그래밍 패러다임과 일치하지 않으며, 'UI Entity' 상태는 'Hoisting State'를 사용하여 관리하는 것이 더 바람직 합니다.

**Do**

```kotlin
// CompositionLocal 설정을 기반으로 Style 값을 반환
// 값의 출처가 명확함
@Composable
fun defaultStyle(): Style { ... }
```

**Don't**
```kotlin
// CompositionLocal 적용을 위한 Style 값을 반환
// Style 타입의 새로운 인스턴스를 생성하고 반환하는 것으로 보임
@Composable
fun Style(): Style { ... }
```

### Naming `@Composable` functions that `remember {}` the objects they return

> - Composable에서 `remember`를 사용하여 가변 객체를 반환할 떄 함수 이름 앞에 `remember` 접두어를 붙여야 함
>   - 'ReComposition'을 거쳐도 값이 유지되는 특징 등, 호출자에게 명확하게 전달하기 위해
>   - 내부적으로 `remember`를 통해 객체를 캐싱하고 있음을 호출자에게 알려 다시 `remember`로 감싸지 않도록 안내하기 위해
> - 객체 반환만을 이유로 'Composable Factory function'으로 간주하기 보단 함수의 '주요 목적'을 고려해야 함
>   - `Flow<T>.collectAsState()`의 주요 목적은 `Flow` 구독 설정, 반환되는 `State<T>`객체의 `remember` 작업은 부차적인 작업일 수 있음

내부에 `remember` API를 사용하여 가변 객체를 반환하는 'Composable'은 함수 이름 앞에 'remember' 접두어를 붙여야 합니다.

**why?**

1-1. `remember`를 사용하여 가변 객체를 반환하는 'Composable'은 해당 가변 객체가 시간이 지남에 따라 변경될 수 있고, 
'Recomposition'을 거쳐도 지속되는 'observable side effects'를 가지고 있습니다.
즉, 객체의 상태가 변경될 떄 UI에 영향을 줄 수 있기에, 이러한 특성을 호출자에게 명확하게 전달하기 위해 'remember' 접두어를 사용합니다.

1-2. 함수 이름에 'remember' 접두어를 사용하는 것은 해당 함수가 내부적으로 `remember` API를 사용하여 객체를 캐싱하고 있음을 나타냅니다.  
이는 호출자가 해당 객체를 다시 `remember`로 감싸서 유지할 필요가 없음을 안내할 수 있습니다.

**Note**

Compose에서 객체를 반환하는 것만으로 'Composable'을 'Factory function'으로 간주하기 보다는 함수의 '주요 목적'을 고려해야 합니다.  
예를 들어 `Flow<T>.collectAsState()`의 주요 목적은 `Flow`에 대한 구독을 설정하는 것이며, 반환되는 `State<T>`객체를 `remember`하는 것은 부차적인 작업일 수 있습니다.

**Do**

```kotlin
@Composable
fun rememberCoroutineScope() : CoroutineScope { ... }
```

**Don't**
```kotlin
@Composable
fun createCoroutineScope() : CoroutineScope { ... }
```

### Naming CompositionLocals

> - `CompositionLocal`의 'Key' 명명 시, 'CompositionLocal' 또는 'Local'을 명사 접미사로 사용 X
> - 그 값에 기반한 서술적인 이름을 가져야 함, 더 서술적인 이름이 적합하지 않을 경우 'Local'을 접두사로 사용할 수 있음
>   - LocalTheme → 'key'가 지역적으로 제한된 Theme 값을 나타냄을 명확하게 표현

`CompositionLocal`은 'Composition-Scope' 내의 'key-value' 테이블의 접근을 위한 'key' 역할을 합니다.  
또한 'Composition'의 특정 하위 트리에 전역적인 값들을 제공하는 데 사용될 수 있습니다.

`CompositionLocal`의 'key'를 명명할 때 'CompositionLocal' 또는 'Local'을 명사 접미사로 사용하지 말고, 
그 값에 기반한 서술적인 이름을 가져야 합니다.
만약 더 서술적인 이름이 적합하지 않을 경우, `CompositionLocal`의 'Key' 이름에 'Local'을 접두사로 사용할 수 있습니다.

**Do**

```kotlin
// 'Local' 형용사, 'Theme' 명사
val LocalTheme = staticCompositionLocalOf<Theme>()
```

**Don't**

```kotlin
// 'Local'을 명사로 사용
val ThemeLocal = staticCompositionLocalOf<Theme>()
```

### Stable Types

> - 'Compose runtime'은 `@Stable`, `@Immutable`을 제공, 타입과 함수의 안정성을 표시하여 'Compose compiler'에 의해 최적화 가능
>   - 최적화 : Composable 파라미터가 변경되지 않는 한 'ReComposition' 시 해당 Composable의 호출을 건너뛸 수 있음
> - 'Compose compiler'는 원시 타입, 불변 객체 (`@Immutable`로 표기된 객체)의 안정성을 자동으로 추론 가능
>   - 'interface', '안정성 추론이 어려운 타입'의 경우 `@Stable`, `@Immutable`을 사용하여 명시적으로 타입 안정성 표시 가능
> - `@Immutable`
>   - 객체 상태가 불변임을 의미, 메서드는 참조 투명성을 가져야 함
>   - `const` 표현식에 사용될 수 있는 모든 타입들은 자동으로 `@Immutable`로 간주
> - `@Stable`
>   - 가변 타입을 적용할 수 있고, 해당 타입의 '속성' 또는 '메서드 동작'이 이전 호출과 다른 결과를 낼 때 '알림'을 받음
>   - '알림'은 'snapshot system'을 통해 지원되며, `@Stable MutableState`도 이러한 알림 메커니즘에 의존
>   - `@Stable`로 표시된 타입의 '속성'들은 다른 `@Stable` 또는 `@Immutable`로 표시된 타입을 사용해야 함
> - `@Stable` 타입의 두 객체 a와 b에 대해, `a.equals(b)`의 결과는 시간이 지나도 변하지 않아야 함
>   - a와 b가 서로 영향을 받음을 의미, a의 상태가 변경되면, b에 대해서도 동일한 변경이 반영되어야 함

'Compose runtime'은 2가지 어노테이션을 제공하여 타입이나 함수를 안정적인 것으로 표시할 수 있으며,  
이는 'Compose compiler plugin'에 의해 최적화될 수 있습니다.
즉, 'Compose runtime'은 'Composable'의 파라미터가 변경되지 않는 한 'Composable'의 결과가 변경되지 않음을 알기에 'Composable'의 호출을 건너뛸 수 있습니다. 

'Compose compiler'는 원시 타입이나 불변 객체(`@Immutable`로 표시된 객체) 같이 명확한 경우 자동으로 안정성을 추론할 수 있습니다.  
그러나 인터페이스나 안정성을 추론하기 어려운 다른 타입들의 경우, 자동으로 추론되지 않습니다.  
이런 경우 `@Stable` 또는 `@Immutable`을 사용하여 명시적으로 타입 안정성을 표시할 수 있습니다.  

`@Immutable`은 객체 상태가 불변임을 의미하며, 이러한 객체의 모든 메서드는 참조 투명성(referential transparency)을 가져야 합니다.  
이는 메서드가 동일한 입력에 대해 항상 동일한 결과를 반환해야 함을 의미합니다. 또한 `const` 표현식에 사용될 수 있는 모든 타입들은(원시 타입, 문자열 등) 자동으로 `@Immutable`로 간주됩니다.

`@Stable`은 적용된 타입이 가변일 수 있지만, 'Compose runtime'은 해당 타입의 'public properties' 또는 '메서드 동작'이 이전 호출과 다른 결과를 낼 떄 알림을 받습니다.
이 알림은 실제로 'snapshot system'을 통해 지원되며, `@Stable MutableState`는 이러한 알림 메커니즘에 의존합니다.

또한 `@Stable`로 표시된 타입이 가지고 있는 'properties'는 다른 `@Stable` 또는 `@Immutable` 타입을 사용해야 합니다.  
이는 해당 'properties'가 안정적이거나 불변의 특성을 유지해야 함을 의미합니다. 
예를 들어, `@Stable` 타입 내부에 가변 리스트나 컬렉션을 가지는 경우, 
해당 리스트나 컬렉션은 `@Stable` 또는 `@Immutable`로 표시된 타입으로 구성되어야 합니다.

---

`@Stable` 타입에 대해 `equals()`는 항상 일관된 결과를 반환해야 합니다.  
즉, `@Stable` 타입의 두 객체 a와 b에 대해, `a.equals(b)`의 결과는 시간이 지나도 변하지 않아야 합니다.  
이는 a와 b가 서로 영향을 받음을 의미하며, a의 상태가 변경되면, b에 대해서도 동일한 변경이 반영되어야 합니다.

이전 안정적인 릴리스에서 `@Stable` 또는 `@Immutable`으로 선언된 타입은 해당 어노테이션을 제거하면 안됩니다.  
또한 해당 어노테이션 없이 제공되었던 'non-final' 타입에 `@Stable` 또는 `@Immutable`을 추가해서는 안됩니다.

**Why?**

`@Stable`과 `@Immutable`은 'Compose compiler plugin'에 의해 생성된 코드의 '바이너리 호환성'에 영향을 줍니다.
바이너리 호환성은 새로운 버전의 라이브러리가 이전 버전과 호환되어 기존 어플리케이션에서 문제 없이 작동할 수 있도록 하는 것을 말합니다.   

여기서 라이브러리가 기존의 'non-final' 타입에 `@Stable` 또는 `@Immutable`을 선언하면, 이미 릴리즈된 구현체들이 올바르게 구현되지 못할 리스크가 있습니다. 
이는 기존 코드와의 호환성 문제를 일으킬 수 있으며, 라이브러리를 사용하던 개발자들이 이를 인지하지 못하는 경우 치명적일 수 있습니다.

### Emit XOR return a value

> - 'Composable'은 'Composition'에 UI 요소를 추가하거나, 값을 반환하는 역할 중 하나만 수행해야 함
>   - 선언형 프로그래밍은 UI 구조와 상태를 명확하게 '선언' 해야함, 만약 그 외의 역할을 수행하면 선언적 UI 원칙이 흐려질 수 있음
> - 'Composable'에 추가적인 제어 기능 및 콜백 제공 시, 'Composable'의 파라미터로 제공되어야 함
>   - 'Composable'이 값을 반환하여 '다른 Composable'과 정보를 주고 받으면, 선언적 UI 흐름의 순서와 명확성을 흐리게 할 수 있음

'Composable'은 UI 요소를 'Composition'에 추가하는 역할을 하거나, 값을 반환하는 역할 중 하나만 수행해야 합니다.  
또한 'Composable'은 호출자에게 추가적인 제어 기능이나 콜백을 제공해야 하는 경우, 'Composable'의 파라미터로 제공되어야 합니다.

**Why?**

'Compose'는 선언형 프로그래밍 패러다임을 따르기에, UI 구조와 상태는 코드를 통해 명확하게 '선언'됩니다.  
만약 'Composable'이 UI 요소를 배치하는 작업과 동시에 값을 반환한다면, 이는 선언적 UI 원칙이 흐려질 수 있습니다.

'Composable'이 값을 반환하여 호출자와 정보를 주고 받으면, 이는 선언적 UI 흐름의 순서와 명확성을 흐리게 할 수 있습니다.  
예를 들어, A가 B를 호출하여 B에 반환된 값에 따라 A 동작이 결정된다면, 이는 A의 UI 구성이 B의 반환 값에 의존하게 될 수 있습니다.  
또한 반환 값이 존재하는 'Composable'은 호출 구조가 제한 되어 '다른 Composable'과 자유롭게 조합하여 사용하기 어려워 집니다.

**Do**
```kotlin
@Composable
fun InputField(inputState: InputState) { /* ... */ }
    
// inputFiled와 통신은 순서에 의존하지 않음
val inputState = remember { InputState() }

Button("Clear input", onClick = { inputState.clear() })

InputField(inputState)
```

**Don't**
```kotlin
@Composable
fun InputField(): UserInputState { /* ... */ }

// inputFiled와 통신이 어려움
Button("Clear input", onClick = { TODO() })
val inputState = InputField()
```

---

## Compose UI API structure

'Compose UI'는 'Compose runtime' 상 구축된 'UI 툴킷'을 말합니다.  
이 섹션은 'UI 툴킷'을 사용하고 확장하는 API 가이드라인을 설명합니다.

### Compose UI elements

> - 'Composable'은 자신의 'root UI node'를 생성해야 함
>   - 다른 'Composable'을 호출하여 해당 함수가 생성한 'UI 노드' 사용
>   - `emit()`을 통해 'UI 트리'에 새로운 노드를 직접 추가
> - 값을 반환하지 않는 'Composable'은 'declarative entities'로 간주되어 'Composition'의 일부로 포함됨
>   - 'Composition'에서 벗어나면 'UI 트리'에서 제거되어 UI에 표시되지 않음
> - 'Composable'의 파라미터를 통해 상태와 동작을 제공함으로 써, 상태 관리를 'Composable' 외부로 이동시키고 재사용성을 높임 
> - 'Composable'은 반드시 `Modifier` 파라미터를 받아야 함
>   - `Modifier`를 통해 공통 동작•스타일을 분리하여 세부적으로 관리할 수 있고 여러 'Composable'에 재사용 할 수 있음
>   - `Modifier` 체인을 형성하여 여러 개의 `Modifier`를 조합하여 하나의 `Modifier`로 생성 후 사용 가능
> - 'Composable Content'가 자연스러운 최소 크기를 가지는 경우, 기본 값으로 `companion object Modifier` 사용
>   - `companion object Modifier` 사용 시, 'Composable Content'의 기본적인 크기와 동작을 보장 (예 : `Text` 컴포넌트)
> - 'Composable Content'가 측정 가능한 최소 크기를 가지지 않는 경우, 기본 값 없이 `Modifier` 파라미터를 요구할 수 있음
>   - 'Content' 크기나 모양이 외부에서 전달되는 `Modifier`에 의해 결정되어야 하는 상황에서 사용 (예 : `Canvas`)
> - `Modifier` 체인을 끝에서 추가하여 `Modifier`의 적용 순서를 유지해야 함
>   - 패딩을 배경색 전에 적용하면 배경색이 패딩 영역에도 적용되지만, 반대의 경우 배경색이 패딩 영역에 적용되지 않음

Compose에서 'UI 트리 노드'를 생성하는 하나의 'Composable'을 `element`라고 합니다.

```kotlin
@Composable
fun SimpleLabel(
    text: String,
    modifier: Modifier = Modifier
)
```

#### Elements return Unit

'element'는 자신의 'root UI node'를 생성해야 하며, 2가지 방법으로 수행될 수 있습니다.  
1. `emit()`을 호출하여 'UI 트리'에 새로운 노드를 직접 추가합니다.  
2. 다른 'Composable'을 호출하여, 그 함수가 생성하는 'UI 노드'를 '자신의 노드'로 사용합니다.

여기서 'element'는 값을 반환해서는 안됩니다.   
이는 'Composable'이 UI 구성에 집중하며, 외부로부터 데이터를 반환하거나 전달하는데 사용되지 않음을 의미합니다.

현재 'Composition'의 상태에서 'element'의 동작이나 상태를 직접적으로 접근할 수 없는 경우, 'element'에 전달된 파라미터를 통해 제공해야 합니다.
이는 'element'가 외부 상태에 의존하거나, 특정 동작을 필요로 하는 경우, 이러한 요구 사항을 파라미터로 전달되어야 함을 의미합니다.

**why?**

값을 반환하지 않는 'element'는 'declarative entities'로 간주되어, 'UI Composition'의 일부로 포함됩니다.  
이 'element'들은 'Composition'에 포함되거나 제외되는 것으로 최종 UI에 어떻게 표시될 지를 결정합니다.

'element'를 제어하는 방법은 파라미터로 제공되어야 하며, 'element'를 호출함으로써 반환되어서는 안됩니다.  
이를 통해 상태와 'element'의 결합을 줄이고, 'element'의 재사용성을 높일 수 있습니다.

**Do**

```kotlin
@Composable
fun FancyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
)
```

**Don't**
```kotlin
interface ButtonState {
    val clicks: Flow<ClickEvent>
    val measuredSize: Size
}

@Composable
fun FancyButton(
    text: String,
    modifier: Modifier = Modifier
): ButtonState { ... }
```

#### Elements accept and respect a Modifier parameter

모든 'element'는 `Modifier` 타입의 파라미터를 받아야 합니다. 
이 파라미터는 'modifier'라는 이름을 가지며 파라미터 목록에서 첫 번째 파라미터로 나타나야 합니다.
또한 'element'는 여러 개의 `Modifier` 파라미터를 받아서는 안됩니다.

'element'의 'content'가 자연스럽게 최소 크기를 가질 경우, `Modifier` 파라미터의 기본 값은 `companion object`의 `Modifier` 타입으로 설정되어야 합니다.
('natural minimum size' == 'minWidth - minHeight'의 제약이 0일 때, 즉 'non-zero size'로 측정되는 경우)  
측정 가능한 'content' 크기가 없는 'element'는 기본 값 없이, `Modifier` 파라미터를 요구할 수 있습니다.

'element'는 생성하는 'Compose UI node'에 'modifier' 파라미터를 전달해야 합니다.  
이는 직접적으로 'UI node'를 생성하거나 다른 'element'를 호출할 때 적용됩니다.

'element'는 전달 받은 'modifier' 파라미터에 추가적인 'modifier'들을 연결할 수 있으며, 이는 주로 파라미터 체인의 끝에 추가됩니다.  
전달 받은 `Modifier` 파라미터의 시작 부분에 추가적인 'modifier'들을 연결해서는 안됩니다.

**why?**

`Modifier`는 'Compose UI'에서 'element'의 외부 동작, 레이아웃, 스타일 등을 추가하는 표준 수단 입니다.  
이를 통해, 공통되는 동작이나 스타일을 'individual element' 또는 'base element' API로부터 분리할 수 있습니다.  
결과적으로, 'element'를 더 작고 집중적인 표준 동작으로 만들 수 있으며, `Modifier`를 사용하여 'element'에 표준 동작을 장식할 수 있습니다.

단일 `Modifier` 파라미터를 사용함으로써, `Modifier` 체인을 통해 필요한 변경사항을 효율적으로 적용할 수 있습니다.

기본 값으로 `companion object Modifier`를 사용하게 되면, 'element'에 추가적인 스타일이나 동작을 적용하지 않아도, 기본적인 크기와 동작을 가지도록 보장합니다.
기본 값 없이 `Modifier`를 요구하는 경우, 'element'의 크기나 모양이 외부에서 전달되는 `Modifier`에 의해 결정되어야 하기 때문에, 명시적으로 스타일이나 동작을 정의 받아야 합니다.

`Modifier` 체인을 만드는 이유는 여러 개의 `Modifier`를 조합하여 하나의 'element'에 다양한 동작과 스타일을 적용하기 위함입니다.  
여기서 `Modifier` 체인의 끝에만 추가적인 'modifier'를 연결하는 이유는, `Modifier`의 적용 순서를 유지하기 위함입니다.  
이는 'modifier'의 순서가 'element'의 동작과 스타일에 영향을 줄 수 있기 때문입니다.

**Do**

```kotlin
@Composable
fun FancyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier
            .surface(eleveation = 4.dp)
            .clickable(onClick = onClick)
            .padding(16.dp)
    )
}
```

### Compose UI layouts

> - layout : N개 이상의 `@Composable` 파라미터를 받는 Composable
> - `@Composable` 파라미터 이름은 'content' 사용
> - `@Composable` 파라미터가 N개의 경우, 가장 중요하거나 일반적인 파라미터에 'content' 이름 사용
> - 'trailing lambda syntax' 사용을 위해 `@Composable` 파라미터는 항상 마지막 파라미터로 위치해야 함

'Compose UI'에서 하나 이상의 `@Composable` 파라미터를 받는 'element'를 'layout(레이아웃)'으로 부릅니다.  
'layout'은 UI 구조를 정의하는데 사용됩니다.

```kotlin
@Composable
fun SimpleRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
)
```

'layout'에서 오직 하나의 `@Composable` 파라미터를 받는 경우, 코드 일관성과 가독성을 위해서, 이 파라미터의 이름을 'content'로 사용해야 합니다.
만약 여러 개의 `@Composable` 파라미터를 받는 경우, 가장 중요하거나 일반적인 파라미터의 이름을 'content'로 사용해야 합니다. 
'content' 이름을 가진 파라미터는 해당 'layout'의 핵심 콘텐츠를 나타내는 경우가 많기 때문입니다.

kotlin의 'trailing lambda' 문법을 이용할 수 있도록 하기 위해, 'layout'에서 `@Composable` 파라미터는 항상 마지막 파라미터로 위치해야 합니다.