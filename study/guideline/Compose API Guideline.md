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