# BasicStateCodeLab

## Compose State, Event
- 상태에 따라 특정 시점에 UI에 표시되는 항목이 결정됩니다.
- Android 앱에서는 이벤트에 대한 응답으로 상태가 업데이트됩니다.
- 즉 앱 상태로 UI에 표시할 항목에 관한 설명을 제공하고, 이벤트라는 메커니즘을 통해 상태가 변경되고 UI도 변경됩니다.

<img width="646" alt="image" src="https://user-images.githubusercontent.com/34837583/204146220-492c9b83-d2d0-4314-a072-a16ce08f9685.png">

<br>

## Composition
Compose 앱은 Composable 함수를 호출하여 데이터를 UI로 변환합니다. 

- Composition : Jetpack Compose에서 Composable 함수를 호출하여 빌드된 UI에 대한 설명, UI를 기술하는 컴포저블의 트리 구조
- Initial Composition : 처음 Composable 실행 시에 컴포지션 생성
- ReComposition : 데이터 변경 시 Composition을 업데이트하기 위해 Composable 함수를 다시 실행하는 것

이렇게 하기 위해서는 Compose가 ``추적할 상태``를 알아야 합니다. 그래야 업데이트 시 리컴포지션을 예약할 수 있습니다.

<br>

### State, MutableState, remember
- Compose에서는 State, MutableState 유형을 사용하여 Compose에서 상태 관찰할 수 있도록 함.
- 하지만 State, MutableState만 다시 사용해서는 Recomposition 할 뿐 이전 상태를 유지하기에 똑같은 UI가 나타나게 됩니다. 따라서 리컴포지션 간에 상태를 유지할 방법이 필요합니다.
- 이를 위해 Composable 한 remember 를 사용할 수 있습니다. remeber로 계산된 값을 초기 컴포지션 중에 컴포지션에 저장되고 저장된 값을 리컴포지션 간에 유지됩니다.

<br>

## State 기반 UI
<img width="696" alt="image" src="https://user-images.githubusercontent.com/34837583/204148424-e107eb0e-296b-4a42-b0f8-c980bd87c98d.png">

- 컴포지션 내 컴포저블의 수명 주기를 나타내는 그림으로 컴포저블은 컴포지션을 시작하고 0회 이상 Recomposition이 되고 추후에 컴포지션을 종료합니다.
- 상태가 변경될 때 UI 구성요소를 업데이트하게 되고, 컴포저블이 결국 컴포지션을 시작하거나 종료할 수 있습니다. 이런 선언형 UI 프레임워크를 통해 직접 업데이트 하는 것보다 오류 발생 가능성을 현저히 낮출 수 있게 됩니다.

<br>

## Composition Remember
- remember는 컴포지션에 객체를 저장하고, remember가 호출되는 소스 위치가 리컴포지션 중에 다시 호출되지 않으면 객체를 삭제합니다.
- remeber는 Configuration Change 발생 시에는 상태가 유지되지 않습니다. 이를 위해서는 rememberSaveable을 사용해야 합니다.
- rememberSaveable은 Bundle에 저장할 수 있는 모든 값을 자동으로 저장합니다. 다른 값의 경우에는 맞춤 Saver 객체를 전달해야 합니다.

<br>

## State hoisting
- remeber를 사용하여 객체 저장하는 컴포저블을 Stateful 컴포저블로 내부 상태로 인해 재사용성이 적고 테스트하기가 어려운 경향이 있습니다.
- 상태를 보유하지 않는 컴포저블을 Stateless 컴포저블이라고 합니다.
- Compose에서는 State hoisting은 컴포저블을 Stateless로 만들기 위해 상태를 호출자로 옮기는 패턴입니다. State hoisting을 위한 일반적인 패턴은 상태 변수를 두 개의 매개변수로 바꾸는 것입니다.
    - value : T - 값
    - onValueChanage: (T) -> Unit - 값을 변경하도록 요청하는 이벤트

<img width="280" alt="image" src="https://user-images.githubusercontent.com/34837583/204148980-7ce6dbc1-3cf2-490f-b81b-c40bdd3d6216.png">

- 위  그림처럼 상태는 내려가고 이벤트는 올라가는 패턴을 Undirectional Data Flow(UDF) 라고 합니다.

<br>

### State hoisting의 장점
- 단일 소스 저장소 : 소스 저장소가 하나여서 버그 방지에 도움
- 공유 가능함 : 여러 컴포저블과 공유 가능
- 가로채기 가능 : 상태를 통해 컴포저블 재구성
- 분리됨 : Stateless함수의 상태는 어디에든 저장할 수 있음.

<br>

## 관찰 가능한 MutableList
- LazyColumn, LazyRow 사용 시 리스트 자체가 변경될 때 단순히 ArrayList, mutableListOf를 사용한다고 Recomposition이 발생하지 않습니다.
- Compose에서 관찰할 수 있는 MutableList 인스턴스를 만들어야 하고 toMutableStateList()와 mutableStateOf 함수를 사용하는 것입니다.

```kotlin
// Don't do this!
val list = remember { mutableStateListOf<WellnessTask>() }
list.addAll(getWellnessTasks())

// Do this instead
val list = remember{
    mutableStateListOf<WellnessTask>().apply { addAll(getWellnessTasks()) }
}
```
- 위 코드에서처럼 기존 리스트에 toMutableStateList()를 사용하는 것이 좋고 mutableStateOf 사용 시에 초깃값을 사용하는것이 성능 최적화가 됩니다.

<br>

## ViewModel 사용
- 이전에 remember은 Bundle 객체에 저장할 수 있는 데이터만 가능하다고 하였습니다. 다른 데이터 형식을 저장하기 위해서는 맞춤 Saver를 제공해야 합니다.
- 하지만 직렬화, 역직렬화가 필요한 데이터 구조를 저장하는데서는 rememberX를 사용해서는 안됩니다. Activity의 onSavedInstanceState를 사용할 때도 유사한 규칙으로 정말 필요한 간단한 형식의 데이터만 저장해야 합니다.
- ViewModel은 UI 상태와 앱의 다른 레이어에 있는 비즈니스 로직에 대한 액세스 권한을 제공합니다. 또한 ViewModel은 구성 변경 후에도 유지되므로 컴포지션보다 전체 기간이 더 깁니다.
