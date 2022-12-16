https://developer.android.com/codelabs/jetpack-compose-accessibility?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fcompose%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-accessibility#0

## 터치 영역 크기
사용자가 클릭, 터치 등의 방법으로 상호작용할 수 있는 화면상의 요소는 안정적으로 상호작용할 수 있도록 충분히 커야 합니다. 
이러한 요소의 너비와 높이가 최소 48dp인지 확인합니다.

이러한 컨트롤의 크기가 동적으로 설정된 경우, 즉 콘텐츠 크기에 따라 조절되는 경우 sizeIn 수정자를 사용하여 크기의 하한선을 설정해 보세요.
수정자 함수의 순서는 중요합니다. 
각 함수는 이전 함수에서 반환한 Modifier를 변경하므로 순서는 최종 결과에 영향을 줍니다. 이 경우 크기를 설정하기 전에, 하지만 clickable 수정자를 적용한 후에 padding을 적용합니다. 
그렇게 하면 패딩이 크기에 추가되고 전체 요소를 클릭할 수 있게 됩니다.
터치 영역을 최소 48dp로 만드는 더 쉬운 방법이 있습니다. 이를 자동으로 처리해주는 머티리얼 구성요소 IconButton을 사용하 면 됩니다.

## 라벨클릭
Surface, Card, clickable 수정자 같은 컴포저블에는 이 클릭 라벨을 직접 설정할 수 있는 매개변수가 포함되어 있습니다.
onClickLabel

## 맞춤작업
기본적으로 Row와 IconButton 컴포저블은 모두 클릭 가능하고, 결과적으로 음성 안내 지원을 통해 포커스를 받습니다.
이러한 상황은 목록의 각 항목에 발생합니다. 즉, 목록을 탐색하는 동안 많은 스와이프가 발생한다는 의미입니다. 
차라리 IconButton과 관련된 작업을 목록 항목의 맞춤 작업으로 포함하고자 합니다.
clearAndSetSemantics 수정자를 사용하여, 접근성 서비스에 이 Icon과 상호작용하지 않도록 지시할 수 있습니다.

## 시각적 요소 설명
앱의 모든 사용자가 아이콘, 삽화 같이 앱에 표시되는 시각적 요소를 보거나 해석할 수 있는 것은 아닙니다. 
접근성 서비스가 관련 픽셀만으로 시각적 요소를 이해하는 방법도 없습니다. 
따라서 개발자가 앱의 시각적 요소에 관한 자세한 정보를 접근성 서비스에 전달해야 합니다.
Image 및 Icon 같은 시각적 컴포저블에는 contentDescription 매개변수가 포함됩니다.
여기서 이 시각적 요소의 현지화된 설명 또는 null(요소가 완전히 장식용인 경우)을 전달할 수 있습니다.

## 제목
Modifier.semantics { heading() }

## 맞춤병합
최상위 행에 하위 요소를 병합하도록 지시
Modifier.semantics(mergeDescendants = true) {}

## toggleable 사용하여 CheckBox 토글 콜백을 상위요소로 상향하기