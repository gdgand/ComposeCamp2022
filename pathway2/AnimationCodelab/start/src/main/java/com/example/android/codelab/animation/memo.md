1. animate*AsState API
간단한 값 변경

2. AnimatedVisibility
visible 파라미터로 전달된 Boolean 값이 변경될 때마다 애니메이션을 실행

enter, exit 파라미터에 원하는 트랜지션 넣어 커스텀하게 사용가능
enter: EnterTransition = fadeIn() + expandIn(),
exit: ExitTransition = shrinkOut() + fadeOut(),
ex. 
slideInVertically, slideOutVertically 는  기본 동작이 항목 높이의 절반을 사용
initialOffsetY는 초기 위치를 반환하는 람다
람다는 인수 하나(요소 높이)를 수신
여러 Animatioun API 의 공통 매개변수인 animationSpec 에 스펙을 전달도 가능


3. Transition API
Transition API 을 사용하면 모든 애니메이션이 완료된 시점을 추적할 수 있다.
여러 상태 간에 전환할 때 다양한 transitionSpec을 정의할 수도 있다. 
여러 값에 동시에 애니메이션을 적용하려면 Transition을 사용
Transition은 updateTransition 함수를 사용하여 만들 수 있다.
   각 애니메이션 값은 Transition의 animate* 확장 함수를 사용하여 선언