# 1.

## 리컴포지션 이란?

- Compose 앱은 구성 가능한 함수를 호출하여 데이터를 UI로 변환합니다. 데이터가 변경되면 Compose는 새 데이터로 이러한 함수를 다시 실행하여 업데이트된 UI를 만듭니다. 이를 리컴포지션이라고 합니다.

### 컴포즈가 변경되지 않은 UI 부분을 처리하는 방식

- 또한, Compose는 데이터가 변경된 구성요소만 다시 구성하고 영향을 받지 않는 구성요소는 다시 구성하지 않고 건너뛰도록 개별 컴포저블에서 필요한 데이터를 확인합니다.

### 주의 할 점

- 구성 가능한 함수는 자주 실행될 수 있고 순서와 관계없이 실행될 수 있으므로 코드가 실행되는 순서 또는 이 함수가 다시 구성되는 횟수에 의존해서는 안 됩니다.

## State 와 MutableState 란?

- State 및 MutableState는 어떤 값을 보유하고 그 값이 변경될 때마다 UI 업데이트(리컴포지션)를 트리거하는 인터페이스입니다.

## remember 로 상태 기억 하기

- 여러 리컴포지션 간에 상태를 유지하려면 remember를 사용하여 변경 가능한 상태를 기억해야 합니다.
- remember는 리컴포지션을 방지하는 데 사용되므로 상태가 재설정되지 않습니다.
- ex) 상위 컴포저블 에서 커스텀 컴포저블이 불필요하게 리컴포지션 될 수 있는 것을 막아준다.

### 상태 버전을 가진 UI 요소

- 화면의 서로 다른 부분에서 동일한 컴포저블을 호출하는 경우 자체 상태 버전을 가진 UI 요소를 만듭니다. 내부 상태는 클래스의 비공개 변수로 보면 됩니다.

### 상태 구독

- 구성 가능한 함수는 상태를 자동으로 '구독'합니다. 상태가 변경되면 이러한 필드를 읽는 컴포저블이 재구성되어 업데이트를 표시합니다.


--------------

# more?

- https://developer.android.com/jetpack/compose/kotlin#higher-order
- https://kotlinlang.org/docs/lambdas.html#function-types


--------------

# Jetpack Compose Basic Codelab
Week1 Jetpack Compose Basic 코드랩은 새 프로젝트를 만드는 것으로 시작합니다.

## 작업 전 필수 확인
Basics에 올려둔 프로젝트는 new project 입니다. 바로 이 프로젝트 위에서 코드랩을 시작하시면 됩니다. 

## 작업 후
Git 명령어 또는 [SourceTree](https://www.sourcetreeapp.com/), [GitKraken](https://www.gitkraken.com/) 등을 이용해 작업 결과를 push 해주세요.
