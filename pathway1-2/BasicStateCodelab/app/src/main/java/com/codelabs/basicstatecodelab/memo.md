
Compose : 선언형 UI 프레임워크
Composition : 컴포저블을 실행할 때 Jetpack Compose에서 빌드한 UI에 관한 설명입니다.
Initial Composition : 처음 컴포저블을 실행하여 컴포지션을 만듭니다.
Recomposition : 데이터가 변경될 때 컴포지션을 업데이트하기 위해 컴포저블을 다시 실행하는 것을 말합니다.

어떻게 업데이트 타이밍을 아는가?
-> Compose 가 추적할 상태를 알아야한다. 그래야 업데이트 받을 때 리컴포지션 가능
추적할 상태는 State 와 MutableState 유형을 사용
value 속성을 읽는 각 Composable 을 추적하고 그 value 가 변경 되면 리컴포지션 트리거하는 방식

어떻게 리컴포지션 간에 데이터를 유지하는가?
-> remember 를 사용
remember는 컴포지션에 객체를 저장하고, remember가 호출되는 소스 위치가 리컴포지션 중에 다시 호출되지 않으면 객체를 삭제
remember를 사용하면 리컴포지션 간에 상태를 유지하는 데 도움이 되지만 구성 변경 간에는 유지안됨. 이를 위해서는 remember 대신 rememberSaveable을 사용