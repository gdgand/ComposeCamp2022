package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        // remember은 리컴포지션 간에만 상태를 유지하는데 도움이 되지만
        // remeberSaveable은 화면회전, 다크모드 전환 등 다른 구성 변경에도 도움이 된다.
        var count by rememberSaveable { mutableStateOf(0) }

        if (count > 0) {
            Text(text = "You've had $count glasses.")
        }

        /**
         * 버튼 설정
         * @param enabled 활성화 조건
         * enabled = count < 10 은
         * count가 10 미만일 때만 버튼을 활성화한다는 의미
         */
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}