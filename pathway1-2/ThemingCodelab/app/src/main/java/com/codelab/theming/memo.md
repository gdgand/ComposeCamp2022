Material Theming
- 색상 (color)
- 서체 (typography)
- 도형 (shape)

의미론적으로 이름이 지정된 여러가지의 스타일 존재

@Composable
fun MaterialTheme(
colors: Colors,
typography: Typography,
shapes: Shapes,
content: @Composable () -> Unit
) { ...


MaterialTheme 컴포저블에 매개변수를 주어 맞춤설정을 지정할 수 있다.

요소의 색상을 설정할 때는 Surface를 사용하는 것이 좋습니다. 적절한 콘텐츠 색상 CompositionLocal 값을 설정하기 때문입니다.
적절한 콘텐츠 색상을 설정하지 않는 Modifier.background를 직접 호출할 때는 주의해야 합니다.