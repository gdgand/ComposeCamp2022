# Jetpack Compose의 상태

### Compose에서의 상태
- '상태'란?
  - 시간이 지남에 따라 변할 수 있는 값
  - Room DB 부터 클래스 변수까지 모든 항목이 포함됨
  - `상태에 따라 특정 시점에 UI에 표시되는 항목이 결정됨`

<br>

### Compose의 이벤트
- 이벤트?
  - 애플리케이션 내부 또는 외부에서 생성되는 입력
    - 예) 버튼 누르기 등으로 UI와 상호작용하는 사용자
    - 새 값을 전송하는 센서 또는 네트워크 응답
<br>

![image](https://user-images.githubusercontent.com/101886039/203690726-728feaff-1fb2-46a8-a3eb-9c2e805c121a.png)
- 상태가 변경될 때 compose에 recomposition 행 한다고 알려줘야함

<br>

### 구성 가능한 함수의 메모리 
- Composition
  - 컴포저블을 실행할 때 Jetpack Compose에서 빌드한 UI에 관한 설명
- 초기 Composition
  - 처음 컴포저블을 실행해 컴포지션을 만듦
- Recomposition
  - 데이터가 변경될 때 컴포지션을 업데이트하기 위해 컴포저블
<br>

- `state`, `MutableState` 유형을 사용해 Compose에서 상태 관찰할 수 있도록 함
  - `MutableStateOf` 함수를 사용해 `MutableState` 만들기 가능
    ```kotlin
    val count: MutableState<Int> = mutableStateOf(0)
    ```
  - 위 상태로 작성하면 recomposition 할 때 다시 0으로 초기화되기 때문에 `remember` 사용
  - 일반적으로 `remember`와 `mutableStateOf`는 같이 사용
    ```kotlin
      val count: MutableState<Int> = remember { mutableStateOf(0) } // 초기값이 0
    ```
  - by 사용
    ```kotlin
    var count by remember { mutableStateOf(0) }
    ```

<br>

### 상태 기반 UI
![image](https://user-images.githubusercontent.com/101886039/203694647-d0702c7e-24d4-4c56-b8e2-b46711cdb50c.png)
- 뷰를 수동으로 업데이트하는 복잡성을 방지 가능
- 새 상태에 따라 뷰를 업데이트 하는 일이 자동으로 발생하기 때문에 오류도 적게 발생
<br>

[Composable의 수명 주기](https://developer.android.com/jetpack/compose/lifecycle#lifecycle-overview)

[Layout Inspector 도구](https://developer.android.com/studio/debug/layout-inspector)

- Layout Inspector 사용
  ![image](https://user-images.githubusercontent.com/101886039/203695741-080a362f-1473-4e46-a0ce-25d75fc8a522.png)

<br>

### 컴포지션의 Remember
- `remember`
  - 컴포지션에 객체를 저장
  - `remember`가 호출되는 소스 위치가 리컴포지션 중에 다시 호출되지 않으면 객체 삭제
- 이런 시나리오 해결 방법으로 상태 복원 방법 존재

<br>

### Compose에서 상태 복원
- `rememberSaveable`
  - `remember`는 리컴포지션간 상태를 유지하는데 도움
  - 구성 변경 간에 유지를 위해 `rememberSaveable` 사용
- `Bundle`에 저장할 수 있는 모든 값 자동으로 저장
- [Compose에서 상태 복원](https://developer.android.com/jetpack/compose/state#restore-ui-state) 
- 앱의 상태에 따라 remember 사용할지 rememberSavable 사용할지 고려

<br>

### 상태 호이스팅
- `remember`를 사용해서 객체를 저장하는 컴포저블에는 내부 상태가 포함되어 있어 컴포저블을 stateful로 만듦
  - 호출자가 상태를 제어할 피룡가 없음
  - 상태를 직접 관리하지 않아도 상태를 사용할 수 있음
  - 하지만 재사용 가능성이 적고 테스트하기 더 어려운 경향이 있다
- 상태를 보유하지 않는 컴포저블을 stateless 컴포저블
  - 상태 호이스팅을 사용하면 stateless 컴포저블을 쉽게 만들 수 있음

<br>

- 상태를 컴포저블의 호출자로 옮겨서 상태 호이스팅 구현
- 일반적 패턴은 상태 변수를 아래 두 개의 매개변수로 바꾸는 것
  - `value`: 표시할 현재 값
  - `onValueChange: (T) -> Unit`: 값을 변경하도록 요청하는 이벤트, T는 제안된 새 값
- 중요한 속성
  - 단일 소스 저장소: 상태를 복제하는 대신 위로 옮겼기 때문에 하나의 소스 저장소로 만들어 버그 방지에 도움
  - 공유 가능함: 호이스팅 된 상태를 여러 컴포저블과 공유 가능
  - 가로채기 가능함: stateless 컴포저블의 호출자는 상태를 변경하기 전에 이벤트를 무시할지, 수정할지 결정할 수 있음
  - 분리됨: stateless 컴포저블 함수의 상태는 어디에든 저장할 수 있음

<br>

- 상태를 호이스팅 할 때 규칙

  1. 상태는 적어도 그 상태를 사용하는 모든 컴포저블의 가장 낮은 공통 상위 요소로 끌어올려야 함
  2. 상태는 최소한 변경될 수 있는 가장 높은 수준으로 끌어올려야 함
  3. 두 상태가 동일한 이벤트에 대한 응답으로 변경되는 경우, 두 상태는 동일한 수준으로 끌어올려야 함
<br>

```kotlin
@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier){
    Column(modifier = modifier.padding(16.dp)){
        if(count>0){
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count<10){
            Text("Add one")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier){
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count, { count++ }, modifier)
}
```
- 위 코드처럼 작성하면 stateless 컴포저블을 재사용 가능
```kotlin
@Composable
fun StatefulCounter(modifier: Modifier = Modifier){
    var waterCount by rememberSaveable { mutableStateOf(0) }
    var juiceCount by rememberSaveable { mutableStateOf(0) }

    StatelessCounter(waterCount, { waterCount++ })
    StatelessCounter(juiceCount, { juiceCount++ })
}
```

- stateful 함수는 여러 컴포저블 함수에 동일한 상태를 제공할 수 있음
```kotlin
@Composable
fun StatefulCounter(modifier: Modifier = Modifier){
    var count by remember { mutableStateOf(0) }

    StatelessCounter(count, { count++ })
    AnotherStatelessMethod(count, { count *= 2})
}
```
- 컴포저블 디자인 권장사항은 필요한 매개변수만 전달하는 것임

<br>

### 관찰 가능한 MutableList
- `toMutableStateList()`
- `rememberSaveable`은 긴 직렬화, 역직렬화가 필요한 복잡한 데이터 구조나 대량의 데이터를 저장하는데 사용해서는 안됨

<br>

### ViewModel의 상태
- 로직 유형
  - UI동작 또는 UI 로직
    - 화면에 상태 변경을 표시하는 방법(탐색 로직, 스낵바 표시)과 관련
  - 비즈니스 로직
    - 상태 변경 시(결제, 사용자 환경설정 저장) 실행할 작업으로 이 로직은 대개 비즈니스 레이어나 데이터 영역에 배치되고 UI 레이어에는 배치되지 않음
- ViewModel은 컴포지션의 일부가 아니기 때문에 메모리 누수 방지를 위해 컴포저블에서 만든 상태(예를 들어 기억된 값 등)를 보유해서는 안됨
- [ViewModel 부분 Codelab](https://developer.android.com/codelabs/jetpack-compose-state?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fjetpack-compose-for-android-developers-1%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-state#11)
