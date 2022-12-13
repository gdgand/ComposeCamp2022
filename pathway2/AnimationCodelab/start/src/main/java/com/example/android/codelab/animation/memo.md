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
여러 Animatioun API 의 대부분의 공통 매개변수인 animationSpec 에 스펙을 전달도 가능


3. Transition API
Transition API 을 사용하면 모든 애니메이션이 완료된 시점을 추적할 수 있다.
여러 상태 간에 전환할 때 다양한 transitionSpec을 정의할 수도 있다. 
여러 값에 동시에 애니메이션을 적용하려면 Transition을 사용
Transition은 updateTransition 함수를 사용하여 만들 수 있다.
각 애니메이션 값은 Transition의 animate* 확장 함수를 사용하여 선언
그럼 Transition 의 target value 에 따라 함께 일어나는 애니메이션을 만들 수 있는 것이다!
animate* 에는 transitionSpec 을 주어 FiniteAnimationSpec 을 사용할 수 있고
isTransitioningTo 중위함수를 사용해서 분기를 쳐서 FiniteAnimationSpec 을 바꿔 설정할 수 있다.

4. InfiniteTransition
여러 값에 애니메이션을 적용한다는 점에서 Transition 과 유사하다.
Transition 은 상태변경에 따라 애니메이션을 적용하는 반면 InfiniteTransition 은 이름처럼 무기한 애니메이션을 적용한다.
rememberInfiniteTransition 을 사용하여 생성한다.
그리고 생성한 InfiniteTransition 객체로 animate* 확장함수를 사용하여 애니메이션 값 변경을 선언한다.
initialValue, targetValue, animationSpec 등의 인자를 전달하여 애니메이션 값을 설정한다.
이 animate* 확장함수의 animationSpec 에는 InfiniteRepeatable 만 사용한다. AnimationSpec 을 래핑하여 반복 가능하게 만들었다.
animationSpec 을 사용해서 애니메이션 값 변화 속도를 조절할 수 있다.





animationSpec 정리
tween (지속시간, 시작 지연시간, 이징)
durationMillis: Int = DefaultDurationMillis,
delayMillis: Int = 0,
easing: Easing = FastOutSlowInEasing
TweenSpec 은 DurationBasedAnimationSpec 상속
DurationBasedAnimationSpec 은 FiniteAnimationSpec 상속

spring (바운싱정도, 지속시간과부드러움정도, -)
dampingRatio: Float = Spring.DampingRatioNoBouncy,
stiffness: Float = Spring.StiffnessMedium,
visibilityThreshold: T? = null
SpringSpec 은 FiniteAnimationSpec 상속


keyframes (KeyframesSpecConfig 에서 지속시간이나 시간에 따른 값 '값 at ms' 설정)
init: KeyframesSpec.KeyframesSpecConfig<T>.() -> Unit
KeyframesSpec 은 DurationBasedAnimationSpec 상속
DurationBasedAnimationSpec 은 FiniteAnimationSpec 상속

https://proandroiddev.com/animate-with-jetpack-compose-animate-as-state-and-animation-specs-ffc708bb45f8