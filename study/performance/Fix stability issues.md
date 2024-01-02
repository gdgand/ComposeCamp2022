## Annotate with Stable or Immutable

`@Stable` 또는 `@Immutable`을 사용하여 'Unstable class'의 안전성 문제를 해결할 수 있습니다.  
단, 이러한 어노테이션은 클래스를 자체적으로 불변하거나 안정적으로 만들지 않습니다. 대신 'Compose 컴파일러'와의 계약에 동의하는 행동입니다.
만약 해당 어노테이션을 잘못 사용하면 'ReComposition'이 제대로 동작하지 않을 수 있습니다.  
어노테이션 없이 'Stable class'로 만들 수 있다면, 그 방법을 사용하여 안전성을 보장하는 것이 좋습니다.

```kotlin
@Immutable
data class Snack(...)
```

위와 같이 `@Immutable` 또는 `@Stable`을 사용하면 Compose 컴파일러는 `Snack` 클래스를 'Stable class'로 간주합니다.

---

## Annotated classes in collections

컬렉션 내에서 어노테이션된 클래스를 사용하는 경우,
특히 Composable 파라미터가 컬렉션 타입일 때 Compose 컴파일러는 해당 컬렉션의 안정성을 보장할 수 없습니다.

```kotlin
restartable scheme ("[androidx.compose.ui.UiComposable]") fun HighlightedSnacks(
    ...,
    unstable snacks: List<Snack>,
    ...
)
```

여기서 `Snack` 클래스에 `@Immutable`을 어노테이션해도 `List<Snack>`은 여전히 'Unstable type'입니다.  
이와 같이 Compose 컴파일러는 파라미터가 'Stable type'의 컬렉션이라도 `List` 타입의 파라미터를 항상 'Unstable type'으로 간주합니다.

이러한 문제를 해결하는 방법은 여러가지 있습니다.

### Immutable Collection

컴파일 시간의 불변성을 보장하기 위해, `List` 대신 'kotlinx Immutable Collection'을 사용할 수 있습니다.

```kotlin
@Composable
private fun HighlightedSnacks(
    snacks: ImmutableList<Snack>
)
```

### Wrapper

'Immutable Collection'을 사용할 수 없는 경우, 직접 만들 수 있습니다.  
이를 위해 `List`를 'Stable class'로 래핑합니다. 

```kotlin
@Immutable
data class SnackCollection(
    val snacks: List<Snack>
)

///....

@Composable
private fun HighlightedSnacks(
    index: Int,
    snacks: SnackCollection
)
```

위와 같은 방식으로 해결하면 Compose 컴파일러는 `HighlightedSnacks`을 Skip 가능하고 안정적으로 만들 수 있습니다.

```kotlin
restartable skippable scheme ("[androidx.compose.ui.UiComposable]") fun HighlightedSnacks(
    stable index: Int,
    stable snacks: ImmmutableList<Snack>
)
```

---

## Stability configuration file

Compose 컴파일러 '1.5.5' 이상 부터는 컴파일 시간에 'Stable class'로 간주할 목록을 제공하는 'configuration file'을 지원합니다.  
이 기능을 통해 'LocalDateTime'과 같이 직접 제어할 수 없는 표준 라이브러리 클래스들을 'Stable class'로 만들 수 있습니다.

'configuration file'은 행마다 하나의 클래스를 나열한 텍스트 파일입니다.  
주석, 단일 및 이중 와일드카드가 지원됩니다.

```kotlin
// LocalDateTime Stable class
java.time.LocalDataTime
// Kotlin collection stable class
kotlin.collections.*
// 내 Data Layer와 모든 하위 모듈을 안정적으로 간주
com.yong.data
// Generic class 첫 번째 타입 파라미터에 기반하여 안정적으로 간주
com.model.GenericClass<*,_>
```

이 기능을 활성화하려면 'configuration file path'를 Compose 컴파일러 옵션에 전달하면 됩니다.

```kotlin
kotlinOptions {
    freeCompilerArgs += listOf(
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:stabilityConfigurationPath=" +
        "${project.absolutePath}/compose_compiler_config.conf"
    )
}
```

Compose 컴파일러는 프로젝트의 각 모듈에서 별도로 실행되므로, 필요에 따라 다른 모듈에 다른 configuration file을 제공할 수 있습니다.  
또는 프로젝트의 루트 수준에 'configuration file'을 배치하고, 모든 모듈에서 동일한 파일을 참조할 수 있습니다.