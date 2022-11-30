package com.codelabs.basicstatecodelab

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.concurrent.CountedCompleter

/**
 * BasicStateCodelab
 * @author jaesung
 * @created 2022/11/19
 */

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Log.e("WC", "Recomposition!")
//
//    Column(modifier = modifier.padding(16.dp)) {
////        val count = mutableStateOf(0)  // Recomposition이 발생할 때마다 0으로 초기화됨
//
//        // remember를 통해 recomposition이 발생하더라도 이전 상태를 기억할 수 있음
//        var count by remember {
//            mutableStateOf(0)
//        }
//
//        // 여기서 count와 showTask는 기억된 값으로 Recomposition이 발생해도 상태를 잃지 않음
//        if (count > 0) {
//            var showTask by remember {
//                mutableStateOf(true)
//            }
//            if (showTask) {
//                WellnessTaskItem(
//                    taskName = "Have you taken your 15 minute walk today",
//                    onClose = { showTask = false }
//                )
//            }
//            Text("You've had $count glasses.")
//        }
//        Row(Modifier.padding(top = 8.dp)) {
//            Button(
//                onClick = { count++ },
//                enabled = count < 10
//            ) {
//                Text("Add one")
//            }
//
//            Button(
//                onClick = { count = 0 },
//                modifier = Modifier.padding(start = 8.dp)
//            ) {
//                Text("Clear water count")
//            }
//        }
//    }
//}

/**
 * 9. 상태 호이스팅
 * remember 또는 rememberSaveable를 통해 상태를 스테이트풀하게 만들 수 있다.
 * 스테이풀한 컴포저블은 재사용 가능성이 적고 테스트 복잡성의 문제가 있다.
 *
 * 상태 호이스팅을 통해 스테이트리스하게 만들어야 한다.
 * 컴포저블 내부에 상태관련 변수를 만드는 것이 아닌 컴포저블의 매개변수로 넣는다.
 * 상태 호이스팅은 UDF를 따른다. (상태는 아래로 이벤트는 위로)
 *
 * 호이스팅한 상태는 공유할 수 있기 때문에 불필요한 리컴포지션을 방지하고 재사용성을 높이려면 컴포저블에 필요한 상태만 전달해야 한다.
 */

/**
 * StatelessCounter
 * 상태에 해당하는 count를 컴포저블 내부에 보유하고 있지 않음
 * 단순히 count를 표시하며 count를 늘리는 이벤트에 해당하는 onIncrement를 전달하여 UDF 패턴을 따름
 *
 * ```kotlin
 * @Composable
 * fun StatefulCounter() {
 *     var waterCount by remember { mutableStateOf(0) }
 *     var juiceCount by remember { mutableStateOf(0) }
 *
 *     StatelessCounter(waterCount, { waterCount++ })
 *     StatelessCounter(juiceCount, { juiceCount++ })
 * }
 * ```
 *
 * 이렇게 쓰면 사용자 이벤트에 의한 리컴포지션을 독립적으로 실행할 수 있다.
 * waterCount가 증가하는 리컴포지션이 있어도 juiceCount는 증가하지 않는다.
 *
 */
@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {

        if (count > 0) {
            Text("You've had $count glasses")
        }
        Button(onClick = onIncrement, modifier = Modifier.padding(top = 8.dp)) {
            Text("Add One")
        }
    }
}

/**
 * StatefulCounter
 * 상태에 해당하는 count를 컴포저블 내부에서 보유함
 */
@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable {
        mutableStateOf(0)
    }

    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glasses")
        }
        Button(onClick = { count++ }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Add One")
        }
    }
}


@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        // rememberSaveable로 Config Change가 발생해도 상태 유지 가능
        var count by rememberSaveable { mutableStateOf(0) }
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WaterCounterPreview() {
    WaterCounter()
}