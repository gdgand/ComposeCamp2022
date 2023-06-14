# 목차
 - [State와 Composition](#State와-Composition)
 - [Composable 안에 State](#Composable-안에-State)
   - [remember API](#remember-api)
   - [MutableState](#mutablestate)
   - [rememberSaveable](#remember의-한계와-remembersaveable)
 - [State의 다른 타입 지원](#state의-다른-타입-지원)
 - [rememberSaveable의 저장 방식들](#remembersaveable의-저장-방식들)
   - [parcelable](#parcelable)
   - [mapSavor](#mapsaver)
   - [listSavor](#listsaver)
 - [remember 블록의 재실행](#remember-함수-블록-재실행)
   - [remember calculation](#remember-calculation)

# Compose State

---

앱의 `State`(이하 상태)는 시간이 지남에 따라 변할 수 있는 모든 값을 의미합니다.  

이는 포괄적인 정의로 `Room DB`에서부터 `class`의 `property` 까지 다양한 것들을 포함합니다.
모든 Android 앱은 사용자에게 어떠한 상태를 보여줍니다.

Android 앱에서의 상태에는 다음과 같은 예시가 있습니다.

- 네트워크 연결이 끊어졌을 때 나타나는 SnackBar
- 블로그 포스트와 그에 딸린 댓글들
- 사용자가 클릭했을 때 재생되는 버튼의 Ripple 애니메이션
- 사용자가 이미지 위에 그릴 수 있는 스티커

---

## State와 Composition

Compose는 선언적 UI 도구입니다.
앱의 상태를 사용자에게 보여주게 됩니다. 상태는 앱에서 시간에 따라 변할 수 있는 모든 값들입니다.

여기서 Compose의 선언적 접근법은 상태가 변경될 때마다 해당 Composable을 다시 호출함으로써 UI를 업데이트 방식입니다.(=ReComposition)

예를들어, 사용자가 `TextField`에 문자를 입력하면, 해당 문자가 상태에 저장되고 이 상태의 변경은 Composable을 다시 호출하게 하여 UI를 업데이트합니다.

아래 예제를 보실까요?  

```kotlin 
@Composable
private fun HelloContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello!",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Name") }
        )
    }
}
```

위 예제는 `TextField`의 `value`가 고정된 빈 문자열이므로, 사용자가 문자를 입력하더라도 업데이트가 되지 않습니다.
이는 **Compose가 선언적**이라는 점 때문입니다.

`value`를 업데이트하려면 `onValueChange`에서 새로운 문자를 상태에 저장해야 합니다.
이렇게 상태가 변경되면 Composable이 다시 호출되어 `TextField`의 `value`가 업데이트됩니다.

흔히 위 처럼 수정하게 되면 이를 [**단방향 데이터 흐름**](../용어.md#단방향-데이터-흐름)이라고 합니다.
이렇게 하면 상태와 UI 사이의 일관성을 유지하므로써, 코드가 예측 가능하고 디버깅하기 쉬워집니다.

---

## Composable 안에 State

Composable에서는 `remember` API를 사용하여 메모리에 객체를 저장할 수 있습니다.

### remember API

- `remember`에 의해 계산된 값은 [initial composition](../용어.md#초기-컴포지션initial-composition) 중에 저장되며, recomposition 시에 저장된 값을 반환합니다.   
- `remember`는 가변과 불변 객체 모두 저장하는 데 사용할 수 있습니다. 

> `remember`를 호출한 composable이 Composition에서 제거되면, 저장된 객체를 지웁니다.  
> `remember` 함수는 `remember`를 호출한 Composable에 객체를 저장합니다.  
> 이는 ReComposition 과정에서 이전의 값을 재사용할 수 있게 해주는데, 이렇게 함으로써 UI 상태가 유지되고 성능이 향상됩니다.

### MutableState

- `mutableStateOf`는 Compose 런타임과 통합되어 사용할 수 있는 특별한 상태 저장 방식입니다. 이를 사용하면 Observable한 `MutableState<T>`객체를 생성합니다.
- `MutableState`는 저장된 값을 읽거나 변경할 수 있게 해주는 `Interface`입니다. 
- `value`에 대한 모든 변경은 `value`를 읽은 모든 Composable이 자동으로 ReComposition이 됩니다. 
  이러한 방식으로 상태의 변경이 UI를 자동으로 업데이트하게 해줍니다.

Composable에서 `MutableState` 객체를 선언하는 세 가지 방법이 있습니다.
```kotlin
val mutableState = remember { mutableStateOf(default) }
var value by remember { mutableStateOf(default) }
val (value, setValue) = remember { mutableStateOf(default) }
```

### remember + MutableState 사용하기
기억된 값은 다른 Composable의 매개변수로 사용될 수 있습니다.  
즉, 한 Composable에서 상태를 생성하고, 이를 다른 Composable에 전달하여 사용할 수 있습니다.   
이렇게 하면 여러 Composable이 같은 상태를 공유하고 참조할 수 있습니다.

또한, 기억된 값은 조건문 또는 논리 구조에 사용될 수 있습니다.   
예를 들어, 상태의 특정 값에 따라서 다른 Composable을 표시하거나 숨기는 등의 동작을 제어할 수 있습니다. 
이를 통해 동적인 UI를 구성할 수 있으며, 사용자의 입력이나 내부 로직에 따라 UI가 바뀌는 동적인 앱을 만들 수 있습니다. 

예를 들어, 이름이 비어 있으면 인사말을 표시하지 않으려면, if 문에서 state를 사용합니다.

```kotlin
@Composable
fun HelloContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        var name by remember { mutableStateOf("") }
        if (name.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
    }
}
```

### remember의 한계와 rememberSaveable
다시한번 이야기 하지만, `remember` 함수는 Composable이 재구성(recomposition)될 때 상태를 유지하는데 도움이 됩니다.   
즉, 데이터나 상태가 변경되어 해당 Composable이 다시 그려질 때, `remember`로 생성된 상태는 그 이전의 값을 계속 유지합니다.

그러나 `remember` 함수는 [구성 변경](../용어.md#구성-변경configuration-change)이 일어나면 상태를 유지하지 않습니다.  
즉, 구성 변경이 발생하면 앱의 `Activity`는 재생성됩니다. 따라서 이 경우에는 `remember`로 생성된 상태가 초기화되고, 이전의 값은 유지되지 않게 됩니다.

이러한 상황을 해결하기 위해 `rememberSaveable` 함수를 사용할 수 있습니다. 
`rememberSaveable` 함수는 [Bundle](../용어.md#bundle)에 저장할 수 있는 모든 값을 자동으로 저장합니다.  
따라서 `rememberSaveable`로 생성된 상태는 구성 변경 사이에서도 값을 유지할 수 있습니다.

---

## State의 다른 타입 지원

Compose는 상태를 관리하기 위해 반드시 `MutableState<T>`를 사용해야 하는 것은 아니며, 다른 관찰 가능한 타입들을 지원합니다. 
다른 관찰 가능한 타입을 Compose에서 사용하기 전에는, 상태가 변경될 때 Composable이 자동으로 재구성되도록 그것을 `State<T>`로 변환해야 합니다.

Compose는 Android 앱에서 자주 사용되는 일반적인 관찰 가능한 타입들로부터 `State<T>`를 생성하는 함수를 제공합니다.

#### Flow 지원

`collectAsStateWithLifeCycle()`는 생명주기를 고려하여 Flow에서 값을 수집하며, 이를 통해 앱이 필요 이상의 리소스를 사용하는 것을 방지할 수 있습니다. 
이 API는 Android 앱에서 Flow를 수집하는 권장되는 방법입니다.

`collectAsState`는 `collectAsStateWithLifecycle`와 비슷하게, Flow에서 값을 수집하고 Compose의 `State`로 변환합니다. 
`collectAsState`는 Android가 아닌 플랫폼에서 `collectAsStateWithLifecycle`(Android 전용) 대신 사용됩니다.

#### Stateful Composable vs Stateless Composable

`remember`를 사용하여 객체를 저장하는 Composable은 내부 상태를 생성하므로, 그 Composable은 상태를 가지게 됩니다.   
이는 상태를 제어할 필요가 없는 상황에서 유용할 수 있지만, 내부 상태를 가진 Composable은 재사용성이 낮고 테스트하기 어렵습니다.

`Stateless Composable`은 상태를 직접 가지고 있지 않는 Composable를 의미합니다. 
`Stateless Composable`은 모든 상태를 외부에서 받아들이고, 변경을 외부로 보내는 역할만 합니다. 
이를 구현하는 방법 중 하나가 `state hoisting`입니다.

> stateful composable : 상태에 대해 신경 쓸 필요가 없는 호출자에게 편리합니다.  
> stateless composable : 상태를 제어하거나 hoisting해야 하는 호출자에게 필요합니다.

---

## rememberSaveable의 저장 방식들

Compose에서는 `rememberSaveable` API를 사용하여 ReComposition뿐만 아니라 `Activity`이나 `Process` 재생성에서의 상태를 유지할 수 있습니다.

### 상태 저장 방법
`Bundle`에 추가된 모든 데이터 유형은 자동으로 저장됩니다. 
`Bundle`에 추가할 수 없는 것을 저장하려면 아래와 같이 몇 가지 옵션이 있습니다.

### Parcelize
가장 간단한 솔루션은 객체에 `@Parcelize` 어노테이션을 추가하는 것입니다. 
이렇게 하면 객체가 `Parcelable`로 변환되어 `Bundle`에 추가될 수 있게 됩니다. 

예를 들어, 다음 코드는 Parcelable `City` 데이터 타입을 만들고 이를 상태에 저장합니다.

```kotlin
@Parcelize
data class City(val name: String, val country: String) : Parcelable

@Composable
fun CityScreen() {
    var selectedCity = rememberSaveable {
        mutableStateOf(City("Madrid", "Spain"))
    }
}
```

### MapSaver
어떤 이유로 `@Parcelize`가 적합하지 않다면, `mapSaver`를 사용하여 객체를 시스템이 `Bundle`에 저장할 수 있는 값의 집합으로 변환하는 규칙을 직접 정의할 수 있습니다.

```kotlin
data class City(val name: String, val country: String)

val CitySaver = run {
    val nameKey = "Name"
    val countryKey = "Country"
    mapSaver(
        save = { mapOf(nameKey to it.name, countryKey to it.country) },
        restore = { City(it[nameKey] as String, it[countryKey] as String) }
    )
}

@Composable
fun CityScreen() {
    var selectedCity = rememberSaveable(stateSaver = CitySaver) {
        mutableStateOf(City("Madrid", "Spain"))
    }
}
```

### ListSaver
맵의 키를 정의하는 것을 피하려면, `listSaver`를 사용하고 인덱스를 키로 사용할 수 있습니다:

```kotlin
data class City(val name: String, val country: String)

val CitySaver = listSaver<City, Any>(
    save = { listOf(it.name, it.country) },
    restore = { City(it[0] as String, it[1] as String) }
)

@Composable
fun CityScreen() {
    var selectedCity = rememberSaveable(stateSaver = CitySaver) {
        mutableStateOf(City("Madrid", "Spain"))
    }
}
```

---

## remember 함수 블록 재실행
`remember` API는 `MutableState`와 함께 사용되는 경우가 많습니다.

```kotlin
var name by remember { mutableStateOf("") } 
```
여기서 `remember` 함수를 사용하면 `MutableState` 값이 ReComposition에서도 유지됩니다.

### remember calculation
일반적으로 `remember`는 `calculation` 람다 매개변수를 받습니다. 
`remember`가 처음 실행될 때, 이 람다를 호출하고 그 결과를 저장합니다.
ReComposition 도중에 `remember`는 마지막으로 저장된 값을 반환합니다.

상태 캐싱 외에도 `remember`를 사용하여 초기화 하거나 계산하는 데 비용이 많이 드는 Composition 내의 모든 객체나 연산 결과를 저장할 수 있습니다.
이러한 계산을 모든 ReComposition에서 반복하고 싶지 않을 수 있습니다. 

아래의 비싼 연산인 `ShaderBrush` 객체를 생성하는 예제를 보시죠.

```kotlin
val brush = remember {
    ShaderBrush(
        BitmapShader(
            ImageBitmap.imageResource(res, avatarRes).asAndroidBitmap(),
            Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT
        )
    )
}
```

`remember`는 값이 Composition을 떠날 때까지 값을 저장합니다. 하지만, 캐시된 즉, Composable 내부에 저장된 값을 무효화하는 방법이 있습니다.   
`remember`는 `key` 또는 `keys` 매개변수를 받습니다. 이들 중 어떤 것이 변경되면, 
다음 ReComposition에서 `remember`는 캐시를 무효화하고 람다 블록 계산을 다시 실행합니다.

아래 예제는 이 메커니즘이 어떻게 작동하는지 보여줍니다.

이 코드에서 `ShaderBrush`가 생성되어 `Box` composable의 `background`로 사용됩니다.   
앞서 설명한 것처럼 `remember`는 `ShaderBrush` 인스턴스를 저장합니다. 
이는 `avatarRes`가 선택된 배경 이미지로서 `key1` 매개변수로 `remember`에 전달되기 때문입니다. 
만약 `avatarRes`가 변경되면, 브러시는 새 이미지로 recompose되고 `Box`에 다시 적용됩니다. 
이는 사용자가 피커에서 다른 이미지를 배경으로 선택했을 때 발생할 수 있습니다.

```kotlin
@Composable
private fun BackgroundBanner(
    @DrawableRes avatarRes: Int,
    modifier: Modifier = Modifier,
    res: Resources = LocalContext.current.resources
) {
    val brush = remember(key1 = avatarRes) {
        ShaderBrush(
            BitmapShader(
               

 ImageBitmap.imageResource(res, avatarRes).asAndroidBitmap(),
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT
            )
        )
    }

    Box(
        modifier = modifier.background(brush)
    ) {
        /* ... */
    }
}
```

아래 코드에서는 상태가 일반 `state holder class`인 `MyAppState`로 호이스팅됩니다. 
이는 `rememberMyAppState` 함수를 통해 클래스 인스턴스를 초기화하는 데 사용됩니다. 
이런 함수를 공개하여 ReComposition에서도 유지되는 인스턴스를 생성하는 것은 Compose에서 일반적인 패턴입니다. 

`rememberMyAppState`는 `windowSizeClass`를 받아 `remember`의 `key` 매개변수로 사용합니다. 
이 매개변수가 변경되면, 앱은 최신 값을 사용하여 `MyAppState`를 다시 생성해야 합니다. 
이는 사용자가 스마트폰을 회전하는 등의 경우에 발생할 수 있습니다.

```kotlin
@Composable
private fun rememberMyAppState(
    windowSizeClass: WindowSizeClass
): MyAppState {
    return remember(windowSizeClass) {
        MyAppState(windowSizeClass)
    }
}

@Stable
class MyAppState(
    private val windowSizeClass: WindowSizeClass
) { /* ... */ }
```