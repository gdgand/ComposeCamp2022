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