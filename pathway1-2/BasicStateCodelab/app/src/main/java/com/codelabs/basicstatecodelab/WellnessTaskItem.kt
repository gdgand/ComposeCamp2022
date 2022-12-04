@Composable
fun WellnessTaskItem(taskName: String, onClose: () -> Unit, modifier: Modifier = Modifier) {
    //비공개 변수처럼 각 WellnessTaskItem 컴포저블에 독립적으로 속합니다.
    //WellnessTaskItem의 그 인스턴스만 재구성되며
    //LazyColumn의 모든 WellnessTaskItem 인스턴스가 재구성되는 것은 아닙니다.
    //var checkedState by remember { mutableStateOf(false) }
    var checkedState by rememberSaveable { mutableStateOf(false) }

    WellnessTaskItem(
        taskName = taskName,
        checked = checkedState,
        onCheckedChange = { newValue -> checkedState = newValue },
        onClose = onClose,
        modifier = modifier,
    )
}