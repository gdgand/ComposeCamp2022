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

// StatelessCounter는 count를 표시하고 count를 늘릴 때 함수를 호출한다.
@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier){
    Column(modifier = modifier.padding(16.dp)){
        if(count > 0){
            Text(text = "You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10){
            Text(text = "Add one")
        }
    }
}

// StatefulCounter는 상태를 소유한다.
// 즉 count 상태를 보유하고 StatelessCounter 함수를 호출할 때 이 상태를 수정한다.
@Composable
fun StatefulCounter(modifier: Modifier = Modifier){
    var count by rememberSaveable{
        mutableStateOf(0)
    }
    StatelessCounter(count = count, onIncrement = { count++ }, modifier)
}