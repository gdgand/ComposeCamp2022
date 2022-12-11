package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//Compose에는 특정 상태를 읽는 컴포저블의 리컴포지션을 예약하는 특별한 상태 추적 시스템이 있습니다
//state, mutablestate 유형을 사용
//리컴포지션

//스테이트풀(Stateful)과 스테이트리스(Stateless) 비교
/*스테이트리스(Stateless) 컴포저블은 상태를 소유하지 않는 컴포저블입니다. 즉, 새 상태를 보유하거나 정의하거나 수정하지 않습니다.

스테이트풀(Stateful) 컴포저블은 시간이 지남에 따라 변할 수 있는 상태를 소유하는 컴포저블입니다.*/

@Composable
fun WaterCounter(modifier: Modifier = Modifier) { //재사용성 modifier
    /*val count = 0
    Text(
        text = "You've had $count glasses.",
        modifier = modifier.padding(16.dp)
    )*/
    Column(modifier = modifier.padding(16.dp)) {
        //var count = 0

        //remember를 사용하여 리컴포지션 간에 값을 유지할 방법이 필요
        /*val count : MutableState<Int> = remember {
            mutableStateOf(0)
        }*/
        //rememberSaveable - bundle에 저장할 수 있는 모든 값을 자동 저장
        var count by rememberSaveable{
            mutableStateOf(0)
        }

        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }

        // Layout Inspector 도구를 사용해서 compose에서
        //생성된 앱 레이아웃을 검사가능
        //Tools -> Layout Inspector
        /*if (count > 0) {
            var showTask by remember {
                mutableStateOf(true)
            }
            if (showTask) {
                WellnessTaskItem(
                    taskName = "Have you taken your 15 minute walk today?",
                    onClose = { showTask = false }
                )
            }
            Text("You've had $count glasses.")
        }

        Row(Modifier.padding(top = 8.dp)) {
            //count증가(리컴포지션발생)


            //showtask = true
            //wellnesstaskitem 및 카운터 text가 모두 표시 시작
            //wellnesstaskitem의 구성요소 x를 누를때 또 다른 리컴포지션 발생 후
            //showtask가 false로 wellnesstaskitem은 발생하지 않음
            //addone은 또다른 리컴 - showtask 잔 추가 - wellnesstaskitem은 닫았음 기억
            //clear를 통해 count 0이 되면 리컴포지션, count표시 text,wellnesstaskitem과 관련된 모든 코드 호출x 컴포지션 종료
            //...
            Button(onClick = { count++ }, enabled = count < 10) {
                Text("Add one")
            }
            Button(onClick = { count = 0 }, Modifier.padding(start = 8.dp)) {
                Text("Clear water count")
            }*/
    }
}

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }

    /*
    var count by remember { mutableStateOf(0) }

   StatelessCounter(count, { count++ })
   AnotherStatelessMethod(count, { count *= 2 })
   */
}

//상태 끌어올리기 -> count를 statelesscounter에서 statefulcounter로 올ㄹ고
//앱에 연결 후 wellnessscreen을 업뎃함

/*
핵심 사항: 상태를 끌어올릴 때 상태의 이동 위치를 쉽게 파악할 수 있는 세 가지 규칙이 있습니다.

상태는 적어도 그 상태를 사용하는 모든 컴포저블의 가장 낮은 공통 상위 요소로 끌어올려야 합니다(읽기).
상태는 최소한 변경될 수 있는 가장 높은 수준으로 끌어올려야 합니다(쓰기).
두 상태가 동일한 이벤트에 대한 응답으로 변경되는 경우 두 상태는 동일한 수준으로 끌어올려야 합니다.
이러한 규칙에서 요구하는 것보다 더 높은 수준으로 상태를 끌어올릴 수 있습니다. 하지만 상태를 충분히 높은 수준으로 끌어올리지 않으면 단방향 데이터 흐름을 따르기가 어렵거나 불가능할 수 있습니다.
*/
@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count, { count++ }, modifier)
    /*
    var waterCount by remember { mutableStateOf(0) }

    var juiceCount by remember { mutableStateOf(0) }

    StatelessCounter(waterCount, { waterCount++ })
    StatelessCounter(juiceCount, { juiceCount++ })
    */
}
