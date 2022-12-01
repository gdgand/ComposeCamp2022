## Memo
동일한 결과를 얻는 방법에는 여러가지 방법이 있을 수 있다.
가장 중요한 것은 디자인 및 Compose 가이드라인을 준수하여 구현하는 것


- 텍스트의 기준선
  - 텍스트의 기준선이란 문자가 '놓여' 있는 선을 가리킵니다. 디자이너들은 주로 상단이나 하단이 아닌 기준선을 기준으로 텍스트 요소를 정렬합니다.
  - paddingFromBaseline 으로 기준선 기준으로 패딩을 줄 수 있음

- Content Description
  - 순전히 장식용이므로 contentDescription을 null로 설정

- 수직방향의 정렬방식
  - 일반적으로, 상위 컨테이너 내부에서 컴포저블을 정렬하려면 상위 컨테이너의 alignment를 설정합니다. 
  - 즉, 하위 요소에 상위 요소 배부에 배치되도록 지시하는 대신 상위 요소에 하위 요소를 정렬하는 방법을 지시합니다. 
  - Column 
    - Start 
    - CenterHorizontally 
    - End
  - Row 
    - Top 
    - CenterVertically 
    - Bottom
  - Box 
    - TopStart 
    - TopCenter 
    - TopEnd 
    - CenterStart 
    - Center 
    - CenterEnd 
    - BottomStart 
    - BottomCenter 
    - BottomEnd
  - 컨테이너의 모든 하위 요소가 동일한 정렬 패턴을 따릅니다. 
  - 하지만 align 수정자를 추가하여 단일 하위 요소의 동작을 재정의할 수 있습니다.
  - Alignment 는 해당 레이아웃 방향의 수직방향 대한 정렬
- 수평방향의 놓기방식
  - Arrangement 는 해당 레이아웃의 방향의 놓기 방식
  - 특히 LazyLayout 에서 spacedBy() 함수로 기존 RecyclerView 에서의   

- Lazy
  - 화면에 표시되는 요소만 렌더링하여 앱의 성능 유지
  - 하위 요소는 컴포저블이 아님을 인지
  - item, items 와 같은 메서드를 제공하는 Lazy 목록 DSL을 사
  - Arrangement.spacedBy() 메서드를 사용하여 각 하위 컴포저블 사이에 고정된 공간을 추가

---
컴포저블에 modifier 아규먼트를 줄 때 레이아웃은 위로 하위 컴포저블은 아래로 주는 것 같다.

- preferred 사이즈
  - 자식 컴포저블에 Modifier.size 값을 주고 그보다 부모 컴포저블에 그보다 작은 height 값을 줘보았는데 잘리지 않고 size 값보다 작게 만들어주는 것을 알게되었다. 
  - 문서에서는 다음과 같이 설명하는 것의 의미를 알겠다.
  - Declare the preferred - of the content to be exactly - dp. The incoming measurement Constraints may override this value, forcing the content to be either smaller or larger.
