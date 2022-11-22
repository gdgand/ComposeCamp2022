package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(
    modifier: Modifier = Modifier   //기본적으로 Modifier 를 제공하는 것이 재사용성에 좋음
) {
    Column(modifier = modifier.padding(16.dp)) {
        //State 및 MutableState 사용하여 Compose 에서 상태 관찰. value 가 변경되면 리컴포지션을 트리거
        //remember 를 사용해 리컴포지션 시 값이 0으로 초기화되지 않고 유지할 수 있도록 함
        //by 키워드 사용하여 getter/setter 를 추가하면 value 속성 사용 없이 count 를 간접적으로 읽고 변경할 수 있음.
//        var count by remember { mutableStateOf(0) }

        //remember 는 구성 변경 간에 유지되지 않기 때문에
        //Bundle 에 저장할 수 있는 모든 값을 자동으로 저장하는 rememberSaveable 사용
        var count by rememberSaveable { mutableStateOf(0) }
        if (count > 0 ) {
            Text(text = "You've had $count glasses.")
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if(count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count = count, onIncrement = { count++ }, modifier = modifier)
}