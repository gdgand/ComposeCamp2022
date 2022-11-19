package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    /**
     * WaterCounter 함수의 상태는 count 변수입니다.
     * 그러나 정적 상태가 있는 것은 그다지 유용하지 않습니다. 정적 상태는 수정할 수 없기 때문입니다.
     * 이 문제를 해결하려면 Button을 추가하여 개수를 늘리고 하루 동안 마신 물잔 개수를 추적합니다.
     * 상태 수정을 야기하는 작업을 '이벤트'라고 합니다.
     */
    Column(modifier = modifier.padding(16.dp)) {

        /**
         * remember - 컴포지션간 상태를 유지
         */
//        var count by remember { mutableStateOf(0) }

        /**
         * rememberSaveable - 구성 변경 간에도 유지
         * rememberSaveable은 Bundle에 저장할 수 있는 모든 값을 자동으로 저장합니다.
         * 다른 값의 경우에는 맞춤 Saver 객체를 전달할 수 있습니다.
         */
        var count by rememberSaveable { mutableStateOf(0) }

        /**
         * 상태 기반 UI
         * count가 0보다 크면 Text를 표시합니다.
         */
        if (count > 0) {

            var showTask by remember { mutableStateOf(true) }
            if (showTask) {
                WellnessTaskItem(
                    taskName = "Have you taken your 15 minute walk today?",
                    onClose = { showTask = false }
                )
            }

            Text(text = "You've had $count glasses.")
        }

        /**
         * count 변수에 다른 값을 설정해도 Compose에서 이 값을 상태 변경으로 감지하지 않으므로 아무 일도 일어나지 않습니다.
         * 이는 상태가 변경될 때 Compose에 화면을 다시 그려야 한다고(즉, Composable 함수를 'recompose') 알리지 않았기 때문입니다.
         *
         * count가 초깃값이 0인 mutableStateOf API를 사용하도록 WaterCounter 컴포저블을 업데이트합니다.
         * mutableStateOf가 MutableState 유형을 반환하므로 value를 업데이트하여 상태를 업데이트할 수 있고 Compose는 value를 읽는 이러한 함수에 리컴포지션을 트리거합니다.
         *
         * count가 변경되면 count의 value를 자동으로 읽는 Composable 함수의 리컴포지션이 예약됩니다.
         * 이 경우 WaterCounter는 버튼을 클릭할 때마다 재구성됩니다.
         *
         * 앱에서 Add one 버튼을 누르면 다음 작업이 실행됩니다.
         * - 개수가 1로 증가하고 상태가 변경됩니다.
         * - 리컴포지션이 호출됩니다.
         * - 화면이 새 요소로 재구성됩니다.
         */
        Row(Modifier.padding(top = 8.dp)) {
            Button(
                onClick = { count++ },
                enabled = count < 10
            ) {
                Text(text = "Add one")
            }

            Button(
                onClick = { count = 0 },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Clear water count")
            }
        }
    }
}

/**
 * StatelessCounter의 역할은 count를 표시하고 count를 늘릴 때 함수를 호출하는 것입니다.
 * 이렇게 하려면 위에 설명된 패턴을 따르고 count 상태(구성 가능한 함수에 매개변수로)와
 * onIncrement 람다(상태가 증가해야 할 때 호출됨)를 전달합니다.
 */
@Composable
fun StatelessCounter(
    count: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

/**
 * StatefulCounter는 상태를 소유합니다.
 * 즉, count 상태를 보유하고 StatelessCounter 함수를 호출할 때 이 상태를 수정합니다.
 */
@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count, { count++ }, modifier)
}