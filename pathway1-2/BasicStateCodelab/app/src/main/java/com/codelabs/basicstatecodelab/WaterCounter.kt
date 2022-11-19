package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
        var count = 0
        Text(text = "You've had $count glasses.")

        /**
         * count 변수에 다른 값을 설정해도 Compose에서 이 값을 상태 변경으로 감지하지 않으므로 아무 일도 일어나지 않습니다.
         * 이는 상태가 변경될 때 Compose에 화면을 다시 그려야 한다고(즉, Composable 함수를 'recompose') 알리지 않았기 때문입니다.
         */
        Button(
            onClick = { count++ },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Add one")
        }
    }
}