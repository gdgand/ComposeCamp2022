# Compose 기본 사항
[자료 다운로드](https://speakerdeck.com/skydoves/2022-compose-camp-pathway-1-2-state-and-theming-in-jetpack-compose?slide=8)

### Compose Phases
![image](https://user-images.githubusercontent.com/101886039/203547748-47c8d71b-f6ff-451c-bb9e-f07dedde1b23.png)
- Composition
  - 어떤 UI를 그릴지 결정
- Layout
  - UI를 측정하고 어디에 배치할지 판단
- Drawing
  - UI 렌더링
- recomposition
  - 이미 그려진 컴포저블 함수를 업데이트(다시 컴포지션) 하고자 하는 경우에 사용
  - 변경된 부분만 새로 컴포지션하고 변경되지 않은 부분은 새롭게 컴포지션 하지 않음(smart recomposition)
    - 성능 향상
    - 퍼포먼스 관련
      - [google IO 발표 영상](https://www.youtube.com/watch?v=EOQB8PTLkpY&feature=youtu.be)
      - [Compose performance 최적화 가이드라인](https://getstream.io/blog/jetpack-compose-guidelines/)

<br>

### Compose State
![image](https://user-images.githubusercontent.com/101886039/203549088-e1601193-b2f0-4cbf-bf35-d5f01eb78b53.png)
- State
  - 데이터를 읽기만 할 수 있음
- MutableState
  - 데이터 읽고 쓰기 전부 가능
- State and Recomposition
  - initial Composition 후 UI를 화면에 그림
  - 컴포저블에서 내부적으로 state 관찰하고 있다가 클릭 이벤트와 같이 User Event 발생해 state 변경이 일어남
  - state가 변경되면 UI를 업데이트 하기 위해 Recomposition이 일어남
- State란?
  ![image](https://user-images.githubusercontent.com/101886039/203549856-69127276-3972-4d44-9e36-248763e59c3d.png)
  - Value Holder 역할   
  - Observable 
    - state 변화를 항상 관찰
  - Recomposition Trigger
    - UI 업데이트 위해 Recomposition 실행
- Composable 함수에서 상태 정의
  - recomposition 시 상태도 같이 초기화 되기 때문에 `remember` api 같이 사용
- Google에선 Stateless 권장
  ![image](https://user-images.githubusercontent.com/101886039/203550892-1cfd2649-4bf2-4fb3-8cc4-47be147d3eb9.png)
  - state를 제공하는 호출자 쪽에서 소스를 단일화 시키고 소스의 원천이 유일하다는 것을 보장할 수 있기 때문
    - 여러개의 공급자가 발생하고 이로 인해 발생되는 이슈를 최소화시킬 수 있음
  - 특정한 stateful한 컴포저블 함수에 대해서만 state를 관리할 수 있도록 하고, 외부에선 state에 대한 정보를 알 수 없도록 캡슐화
  - state로부터 독립적이고 자유롭기 때문에 재사용성을 높일 수 있음
- State Hoisting
  - stateful한 함수를 stateless하게 만드는 것을 말함
  - stateless 하게 변경하는 방법
    - state를 함수의 파라미터로 옮겨줌
      - mutableState의 경우 상태 변경이 가능하기 때문에 람다식을 사용해서 작성
  - 컴포저블 함수의 state를 호출자 쪽에서 관리하도록 역할을 위임해 재사용성을 높임

<br>

### CompositionLocal
![image](https://user-images.githubusercontent.com/101886039/203554679-df69e18a-9bfb-4622-ad36-fa10a65ec856.png)
- composable A에 있는 값을 B를 거쳐서 C에 전달해야 하는 경우
  - 파라미터를 통해 전달하면 A에 의존성을 갖게 됨
    - 모든 하위 컴포저블에서 파라미터를 추가해야하기 때문에 불필요한 파라미터 전달이 반복됨
- `CompositionLocal` 사용
  - 데이터를 암시적으로 전달 가능
  - composable 함수의 트리 구조가 복잡해질수록 유리하게 처리 가능
  - [CompositionLocal 공식 문서](https://developer.android.com/jetpack/compose/compositionlocal)

<br>

### Compose Theming
- Material Design
  - 구글에서 개발한 디자인 시스템으로 
  - 각종 컴포넌트에 대한 스펙을 정의해주고 있기 때문에 디자인 시스템을 개발하기 위한 리소스를 많이 절약 가능
  ![image](https://user-images.githubusercontent.com/101886039/203555483-419e8856-9342-46fe-9986-d9b0408c8eff.png)
  - 디테일한 값들은 프로젝트 성격에 따라 커스텀 가능
  - Color, Shape, Typography를 커스텀 가능 
  - MaterialTheme 컴포저블 함수의 하위 스코프에 속한 모든 컴포저블 함수에 대해 동일한 스타일링 적용 가능
