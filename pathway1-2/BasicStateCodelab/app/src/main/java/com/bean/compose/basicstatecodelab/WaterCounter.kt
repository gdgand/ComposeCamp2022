package com.bean.compose.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// composition : 데이터를 UI로 바꿔주는 함수
// state가 변경되면 recomposition이 발생함.
// compose가 반드시 무슨 state를 관리해야 하는지 알아야함. ***
// remember => recomposition 사이에 살아남기 위함.

// internal state -> stateful composable
// stateless composiable 테스트 하기 쉬움
// state hoisting -> sateless 만듬
// SSOT 만들 수 있음

// UDF state는 아래로, event는 위로

// state에 의해서 화면에 최종적으로 그려질 걸 정한다.
@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
//    var count: MutableState<Int> = mutableStateOf(0) // recomposition은 발생하지만 다시 0으로 초기화된다.
//    val count: MutableState<Int> =
//        remember { mutableStateOf(0) } // recomposition에도 살아남도록 remember한다.
//    var count by remember { mutableStateOf(0) } // 더 편리하게 사용하기 위함.
    var count by rememberSaveable { mutableStateOf(0) } // state를 bundle에 저장하고 불러온다.
    StatelessCounter(count = count, onIncrement = { count++ }, modifier = modifier)
}

@Composable
private fun StatelessCounter(
    count: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text(text = "You've had $count glasses.")
        }
        Button(
            onClick = onIncrement,
            enabled = count < 10,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add one")
        }
    }
}

