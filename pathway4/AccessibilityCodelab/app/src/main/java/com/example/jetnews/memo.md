https://developer.android.com/codelabs/jetpack-compose-accessibility?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fcompose%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-accessibility#0

사용자가 클릭, 터치 등의 방법으로 상호작용할 수 있는 화면상의 요소는 안정적으로 상호작용할 수 있도록 충분히 커야 합니다. 
이러한 요소의 너비와 높이가 최소 48dp인지 확인합니다.

이러한 컨트롤의 크기가 동적으로 설정된 경우, 즉 콘텐츠 크기에 따라 조절되는 경우 sizeIn 수정자를 사용하여 크기의 하한선을 설정해 보세요.
수정자 함수의 순서는 중요합니다. 
각 함수는 이전 함수에서 반환한 Modifier를 변경하므로 순서는 최종 결과에 영향을 줍니다. 이 경우 크기를 설정하기 전에, 하지만 clickable 수정자를 적용한 후에 padding을 적용합니다. 
그렇게 하면 패딩이 크기에 추가되고 전체 요소를 클릭할 수 있게 됩니다.
터치 영역을 최소 48dp로 만드는 더 쉬운 방법이 있습니다. 이를 자동으로 처리해주는 머티리얼 구성요소 IconButton을 사용하면 됩니다.