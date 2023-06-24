# State Hoist 범위

상태가 UI를 어떻게 표현할지 결정하는 로직에 필요한지, 아니면 앱이 어떻게 동작해야 하는지 결정하는 로직에 필요한지에 따라 Hoist의 위치가 달라집니다.

| State   | 설명                                                        |
|---------|-----------------------------------------------------------|
| UI 로직   | 상태를 가지고 있는 컴포넌트는 UI를 렌더링하거나 사용자 입력을 처리하는 데 사용되는 컴포넌트   |
| 비즈니스 로직 | 상태를 가지고 있는 컴포넌트는 앱의 핵심 기능을 수행하거나 앱의 전반적인 동작을 관리하는 컴포넌트 |

## 모범 사례

UI 상태를 읽고 쓰는 모든 Composable들의 가장 가까운 공통되는 조상에 UI 상태를 위치시키는것이 좋으며,
이는 읽고 쓰는 모든 Composable들에서 가장 가까운 곳에 위치해야 합니다.

상태 소유자(즉, Composable 함수 or ViewModel에서 Hoist하는 State 등)는
`immutable State`와 `상태를 변경하는 이벤트`를 소비자(즉, 구독을 하고있는 Composable)에게 제공해야 합니다.

---

## UI 상태와 UI 로직의 종류

### UI State

UI 상태는 UI를 묘사하는 `Property`입니다. UI 상태에는 두 가지 종류가 있습니다:

#### Screen UI State

기기 Display에 표현해야 할 UI 상태를 의미합니다.

`NewsUiState` 클래스를 가정해보면 UI를 렌더링하는 데 필요한 '뉴스 기사'와 '기타 정보'를 포함할 수 있습니다.  
위 상태는 앱의 `Data`를 포함하고 있기에 일반적으로 다른 계층의 레이어와 연결됩니다.

#### UI Element State

UI Element(요소)의 고유한 속성을 의미 합니다. 이 상태는 그들이 **어떻게 렌더링 되는지**에 영향을 미칩니다.

UI 요소는 '보이거나 숨겨질 수 있고', '특정 글꼴', '글꼴 크기', '글꼴 색상'을 가질 수 있습니다.   
Android에서는 `View`가 그 자체로 상태를 관리하며, 상태를 수정하거나 쿼리하는 메서드를 제공합니다.  
이의 예시로는 `TextView`의 `get` 및 `set` 메서드가 있습니다.

Compose에서는 상태는 Composable 외부에 있으며, 상태를 Composable이나 State Holder로 호이스팅할 수도 있습니다.   
이의 예시로는 `Scaffold` composable에 대한 `ScaffoldState`가 있습니다.

### 로직

Application 로직은 비즈니스 로직 또는 UI 로직일 수 있습니다.

#### 비지니스 로직

비즈니스 로직은 앱 데이터에 대한 제품 요구 사항의 구현입니다.  
예를 들어, 사용자가 버튼을 누르면 뉴스 앱에서 기사를 북마크하는 것입니다.  
북마크를 파일이나 데이터베이스에 저장하는 이 로직은 일반적으로 DomainLayer 또는 DataLayer에 위치합니다.   
상태 소유자는 위 Domain, Data 레이어가 제공하는 메서드를 호출하여 이 로직을 위임합니다.

#### UI 로직

UI 로직은 화면에 UI 상태를 어떻게 표시하는지와 관련이 있으며 다음과 같은 예가 있습니다.
- 사용자가 카테고리를 선택했을 때 올바른 검색 창 힌트를 얻는 것
- 목록에서 특정 아이템으로 스크롤
- 사용자가 버튼을 클릭했을 때 특정 화면으로의 탐색 로직

---

## UI 로직

UI 로직이 상태를 사용하고 싶을 때는 UI의 생명 주기를 따라야 하며, 이를 위해서 상태를 적절한 레벨의 Composable에 호이스팅해야 합니다.
이에 대한 대안으로 UI 생명 주기에 Scope가 지정된 [Plain State Holder Class](https://developer.android.com/topic/architecture/ui-layer/stateholders#ui-logic)에서 이를 수행할 수 있습니다.

### Composable이 상태를 관리

상태와 로직이 단순한 경우 Composable 내부에 UI 로직과 UI 요소 상태를 두는것도 좋은 방식입니다. 
필요에 따라 Composable 내부에 상태를 두거나 호이스팅 할 수 있습니다.

### 필요하지 않은 State Hoisting 

상태 호이스팅을 항상 할 필요는 없습니다. 
다른 Composable이 그 상태를 제어할 필요가 없는 경우 상태는 Composable 내부에 두어도 괜찮습니다. 

다음 클릭 시 확장되고 축소되는 Composable을 보시죠

```kotlin
@Composable
fun ChatBubble(
    message: Message
) {
    var showDetails by rememberSaveable { mutableStateOf(false) } // UI 요소 확장 상태 정의

    ClickableText(
        text = AnnotatedString(message.content),
        onClick = { showDetails = !showDetails } // 간단한 UI 로직 적용
    )

    if (showDetails) {
        Text(message.timestamp)
    }
}
```

`showDetails` 변수는 UI 요소의 내부 상태입니다. 이는 이 Composable 내부에서만 읽히고 수정되며, 이에 적용되는 로직은 단순합니다.  
따라서 위와 같은 경우에는 호이스팅의 큰 이점이 없어 내부에서 처리할 수 있습니다.

> UI Element State를 Composable 내부에 유지 할 수 있습니다.   
> 이는 상태와 그에 적용하는 로직이 단순하고 UI Layer의 다른 Composable들이 상태를 필요로 하지 않는 경우 괜찮습니다.   
> 일반적으로 애니메이션 상태의 경우 사용될 수 있습니다.
