# Compose 아키텍처 레이어

아래는 Compose의 아키텍처 레이어와 핵심 설계 원칙입니다.

Compose는 여러 모듈로 구성되며, 이 모듈들을 이해하면 다음과 같은 점에 대해 이해하고 구현할 수 있습니다.

- 앱 또는 라이브러리 구축에 적절한 추상화 수준을 선택
- 더 많은 제어를 위해 더 낮은 수준으로 내려갈 시기
- 의존성 최소화

## Layer
Compose의 주요 레이어는 다음과 같습니다.

<img src=../../resource/layering-major-layers.svg width="50%" height="auto">

### Runtime
`remember`, `mutableStateOf`, `@Composable` 어노테이션 및 `SideEffect` 등의 Compose RunTime의 기본 요소를 제공합니다.  
UI가 아닌 Compose의 트리 관리 기능만 필요하다면 이 레이어를 직접 사용할 수 있습니다.

### UI
`ui-text`, `ui-graphics`, `ui-tooling` 등의 여러 모듈로 구성되어 있습니다.  
`LayoutNode`, `Modifier`, input handler, custom layer, drawing 등의 UI Toolkit의 기본 개념을 구현합니다.  
이 레이어는 UI Tookit의 기본 개념이 필요할 때 직접 사용할 수 있습니다.리

### Foundation
`Row`와 `Column`, `LazyColumn`, 특정 제스처 인식 등과 같이 디자인 시스템에 독립적인 기본 구성 요소를 제공합니다.   
자신만의 디자인 시스템을 만드는 데 Foundation 레이어를 사용할 수 있습니다.

### Material
Material Design 시스템을 Compose UI에 적용하고, Theme 시스템, 스타일 컴포넌트, 리플 표시, 아이콘 등을 제공합니다.   
앱에서 MaterialDesign을 사용할 때 이 레이어를 기반으로 구축하면 됩니다.