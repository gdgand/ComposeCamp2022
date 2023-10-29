# Compose - Phases

다른 UI 툴킷과 마찬가지로, Compose는 여러 단계를 거쳐 프레임을 렌더링합니다.

Android View 시스템을 보면 아래 주요 단계가 있습니다.

```mermaid
graph LR
    subgraph Android View
        direction LR
        Measure --> Layout --> drawing
    end
```

위 Android View 시스템과 유사하지만, 컴포즈는 시작 부분에 `Composition` 단계가 있습니다.

---

## Frame의 세 가지 단계

> - `Composition`은 컴포저블을 실행하여 UI 요소에 속성을 설정하고 UI 트리에 요소를 추가하여 UI 트리를 출력
> - `Layout`은 UI 트리를 순회하고 각 UI 요소의 크기를 결정하고 배치
> - `Drawing`은 각 UI 요소에 `onDraw()`를 호출하여 UI 요소를 렌더링

Compose는 다음 세 가지 주요 단계를 가집니다.

![img.png](../../resource/frame-phases.png)

위 단계처럼 `Data 입력 → Composition → Layout → Drawing → UI` 형태로 이어져 하나의 `프레임(Frame)`을 생성합니다.  
이를 [UDF](../용어.md#단방향-데이터-흐름) 라고도 합니다.

> `BoxWithConstraints`, `LazyColumn`, `LazyRow` 등의 경우  
> 자식 요소의 Composition은 부모 요소의 Layout 단계에 따라 달라질 수 있습니다.

`Composition -> Layout -> Drawing`의 단계는 대부분의 모든 프레임에 대해서 가상으로 이루어진다고 볼 수 있지만,  
**컴포즈는 성능 최적화를 위해 동일한 `Data 입력`을 통한 동일한 결과를 도출하는 작업은 하지 않습니다.** 

컴포즈는 이전 결과를 재사용할 수 있으면 컴포저블을 실행을 건너뛰고, 전체 UI 트리를 다시 `Layout` 또는 `Drawing` 할 필요가 없는 경우 생략할 수 있습니다.
이런 최적화는 컴포즈가 각 단계에서 상태 변화를 추적할 수 있기 때문에 UI 업데이트 시 최소한의 작업만 할 수 있는 것 입니다.

예를 들어 컴포즈는 각 단계에서 상태를 추적하여 UI를 업데이트할 때 필요한 작업을 최소화 할 수 있습니다.

- `Composition` :  사용자가 입력한 텍스트를 읽을 수 있음
- `Layout` : 뷰의 크기를 읽을 수 있음
- `Drawing` : 뷰의 색상을 읽을 수 있음

사용자가 텍스트를 입력하면 컴포즈는 `Composition` 단계에서 상태를 확인하며, `Layout` 단계와 `Drawing` 단계에서는 이전에 확인한 상태를 재사용할 수 있습니다.
따라서 컴포즈는 `Composition` 단계에서만 상태를 확인하면 됩니다. 

---

## 상태(State) 읽기

`Composition`, `Layout`, `Drawing` 단계 중 하나에서 `Snapshot state`의 값을 확인하면, 컴포즈는 상태를 확인할 때 무엇을 하고 있었는지 자동으로 추적합니다.  
이러한 상태 추적은 상태 값이 변경될 때 컴포저블 코드를 다시 실행할 수 있게 하며, 이는 컴포즈에서 상태 관찰 기능의 기반을 형성합니다.

`Snapshot State`는 일반적으로 `mutableStateOf()`를 통해 생성되고, 다음 2가지 방법 중 하나를 통해 접근할 수 있습니다.

- `value` 프로파티를 통한 직접 접근
- `by` Property Delegate를 사용

```kotlin
val paddingState: MutableState<Dp> = remember { mutableStateOf(8.dp) }
val padding: Dp by remember { mutableStateOf(8.dp) }

println(paddingState.value)
println(padding)
```


`Property Delegate`의 내부에서는 `getter`와 `setter` 함수가 State의 `value`에 접근하고 업데이트하는 데 사용됩니다.   
이 `getter`와 `setter` 는 속성을 값으로 **참조할 때만 호출**되며, **생성될 때는 호출되지 않습니다**. 그래서 위 두 가지 방법은 동등합니다.

---

## Phases state reads

> - 컴포즈는 3가지 단계에서 어떤 상태를 확인하는지 자동으로 추적할 수 있으며, 이를 통해 특정 단계만을 실행하도록 알릴 수 있음
> - 중요한 점은 `SnapshotState`가 생성되는 지점이 아닌, 어디서 확인(읽는)하는지 이며, 이를 통해 컴포즈가 상태를 추적하도록 함
> - Composable or Composable Block에서 상태를 확인하면 `Composition - Layout - Drawing` 3 단계에 영향을 미침
>   - 상태 값이 변경된 경우 리컴포저는 해당 상태 값을 읽은 모든 컴포저블 함수를 다시 실행하도록 예약 
>   - 만약 상태 값이 변경되지 않았다면 런타임은 일부 또는 모든 Composable 함수를 건너뛸 수 있음
>   - `Composition` 결과에 따라 `Layout - Drawing`을 실행하지만, UI 요소의 크기나 레이아웃이 변경되지 않았다면 건너뛸 수 있음
> - '측정'과 '배치' 단계에서 상태를 확인하면 `Layout - Drawing` 단계에 영향을 미칠 수 있음
>   - '측정'에서 상태를 Layout 컴포저블에 전달된 `measurePolicy`, `LayoutModifier` 인터페이스의 `MeasureScope.measure` 메서드 등에서 사용한 경우
>   - '배치'에서 상태를 Layout 컴포저블의 배치 블록, `Modifier.offset { … }`의 람다 블록 등에서 사용하는 경우
> - '측정'과 '배치' 단계는 별도의 재시작 범위를 가지지만, [서로 얽혀있기에 서로의 재시작 범위에 영향을 줄 수 있음](#측정과-배치-서로의-영향)
> - UI를 그리는 중 상태를 확인하면 `Drawing` 단계만 영향을 줌
>   - `Canvas`, `Modifier.drawBehind`, `Modifier.drawWithContent` 등의 그리기 코드에서 상태를 사용하는 경우

컴포즈의 3가지 주요 단계가 있고, 각 단계에서 어떤 상태를 확인했는지 컴포즈가 자동으로 추적합니다.  
이를 통해 컴포즈는 특정 UI 요소에 영향을 주는 작업을 수행해야 하는 특정 단계만을 알릴 수 있습니다.

> 중요한 점은 `snapshot state`가 어디에서 생성되고 저장되는지가 아닌, 언제 어디서 읽혔는지입니다.

<img src="../../resource/phases-state-read-draw.svg" width="60%" height="60%">

---

### 단계 1: Composition (구성)

'컴포저블'이나 '컴포저블 람다 블록'에서 상태를 확인하면 `Composition-Layout-Drawing` 세 가지 단계에 영향을 미칠 수 있습니다. 

상태 값이 변경될 경우 리컴포저(Recomposer)는 해당 상태 값을 읽은 모든 컴포저블 함수를 다시 실행하도록 예약합니다.  
만약 상태 값이 변경되지 않았다면 런타임은 일부 또는 모든 컴포저블 함수를 건너뛸 수도 있습니다.

컴포즈 UI는 `Composition` 결과에 따라 `Layout`과 `Drawing` 단계를 실행하지만,
UI 트리의 UI 요소가 동일하고 크기 및 레이아웃이 변경되지 않았다면 `Layout`과 `Drawing` 단계를 건너뛸 수 있습니다.

```kotlin
var padding by remember { mutableStateOf(8.dp) }
Text(
    text = "Hello",
    // modifier가 생성될 때 Composition 단계에서 padding 상태를 읽음
    // padding의 변경은 Re-Composition 호출
    modifier = Modifier.padding(padding)
)
```

### 단계 2: Layout (레이아웃)

`Layout` 단계는 2가지 단계로 구성됩니다.

- 측정 단계 : Layout 컴포저블에 전달된 `measurePolicy`를 실행하거나, `LayoutModifier` 인터페이스의 `MeasureScope.measure` 메서드 등이 실행됩니다.  
- 배치 단계 : Layout 컴포저블의 배치 블록, `Modifier.offset { … }`의 람다 블록 등이 실행됩니다.

위처럼 '측정'과 '배치' 단계에서의 상태 확인은 `Layout-Drawing` 단계에 영향을 미칠 수 있습니다.  
상태 값이 변경되면, 컴포즈 UI는 `Layout` 단계를 예약하며, 크기나 위치가 변경된 경우 `Drawing` 단계도 실행합니다.

추가로 '측정' 단계와 '배치' 단계는 별도의 [재시작 범위](#재시작-범위-re-start-scope)를 가집니다.   
이는 '배치' 단계에서의 상태 확인을 하여도 '측정' 단계를 다시 실행하지 않음을 의미합니다.  

그러나, 실제 동작 시 서로 긴밀하게 연결되어 있을 수 있어서 '배치' 단계에서 상태를 읽으면, 이것이 '측정' 단계에 속한 다른 재시작 범위에 영향을 미칠 수 있습니다.

#### '측정'과 '배치' 서로의 영향

예를 들어 사용자가 버튼을 누르는 경우 텍스트의 내용이 변경되는 이벤트가 있다고 하면, 이 상태 변경은 '배치' 단계에서 읽혀집니다.  
이 상태 변경으로 인해 '배치' 단계는 재실행되며, 이로인해 텍스트의 길이가 변경되는 경우 '측정' 단계의 결과에도 영향을 미칠 수 있습니다.  
즉, 텍스트가 길어져 `Text` 컴포넌트의 크기가 커져야 하는 상황이 발생되는 경우 입니다.

이처럼 '배치' 단계에서 읽힌 상태 변경이 '측정' 단계의 다른 재시작 범위에 영향을 주는 것입니다.  
이렇게 볼 때, 두 단계는 별도의 재시작 범위를 가지고 있지만, 실제로는 서로에게 영향을 미칠 수 있다는 것을 알 수 있습니다.

```kotlin
var offsetX by remember { mutableStateOf(8.dp) }
Text(
    text = "Hello",
    modifier = Modifier.offset {
        // offset이 계산될 때 Layout 단계의 '배치' 단계에서 offsetX 상태를 읽음
        // offsetX 변경은 Layout 단계 다시 시작
        IntOffset(offsetX.roundToPx(), 0)
    }
)
```

### 단계 3: Drawing (그리기)

UI를 그리는 중에 상태를 읽으면 `Drawing` 단계에 영향을 미칩니다.  
상태 값이 변경되면 해당 상태 값을 읽는 그리기 코드가 다시 실행되며, 그 결과 UI 요소의 시각적 표현이 업데이트 됩니다.  
(그리기 코드의 예로는 `Canvas()`, `Modifier.drawBehind`, `Modifier.drawWithContent` 등이 있습니다.)

즉, 상태가 변경되었을 떄 `Drawing` 단계만 재실행하는 것은 `Composition` 단계를 거치지 않아도 되므로 자원을 절약할 수 있어 효율적이며,
`Drawing` 단계에서 애니메이션과 다른 시각적 전환을 부드럽게 처리할 수 있어 사용자에게 더 좋은 UX를 제공할 수 있게 됩니다.

```kotlin
var color by remember { mutableStateOf(Color.Red) }
Canvas(modifier = modifier) {
    // 캔버스가 렌더링될 때 Drawing 단계에서 `color` 상태를 읽음
    // color 변경은 그리기를 다시 시작
    drawRect(color)
}
```

---

### 재시작 범위 (re-start scope)
재시작 범위는 컴포즈에서 **특정 상태를 읽은** `Code Block`을 가리키며 상태 값이 변경될 때 재실행 될 수 있음을 말합니다.

각각의 `Composition`, `Layout`, `Drawing` 단계에서 상태를 읽는 모든 코드 블록을 추적하고, 해당 상태가 변경될 때 해당 코드 블록을 재실행합니다.
