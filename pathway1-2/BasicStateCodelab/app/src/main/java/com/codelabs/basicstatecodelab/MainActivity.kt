package com.codelabs.basicstatecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme
import com.codelabs.basicstatecodelab.ui.theme.WellnessScreen
import com.codelabs.basicstatecodelab.ui.theme.WellnessTasksList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicStateCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // setContent 블록 내에서 WellnessScreen Composable을 호출.
                    WellnessScreen()
                }
            }
        }
    }
}


/*
- 하루 동안 마신 물잔 개수를 계산하는 워터 카운터
- 현재 상태는 정적 상태로 count 변수로 상태 판단
- but, 수정이 불가하기에 Button을 추가하여 상태를 변화시켜주어야함 -> 이벤트

하지만, 이대로 실행하면 버튼을 클릭해도 아무 일도 일어나지 않음.
count 변수에 다른 값을 설정해도 Compose에서 이 값을 상태 변경으로 감지 하지 않으므로,
아무일도 일어나지 않음.
이는 상태가 변경될 때, Compose에 화면을 다시 그려야 한다(함수를 재구성)고 알리지 않았기 때문이다.


@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        // 상태 추척을 위해 Compose의 State 사용
        // count의 value 값이 변경되면 변경을 인식하고 Recomposition이 생김.
        // count가 변경되면 count의 value를 자동으로 읽는 함수의 Recomposition이 예약된다.
        // by 키워드를 사용하여 count를 var로 정의
        // getter, setter 가져오기를 추가해 MutableState의 value 속성을 명시적으로 참조하지 않고도
        // count를 간접적으로 읽고 변경이 가능하다.
        var count by rememberSaveable {mutableStateOf(0)}
        // Android 스튜디오의 Layout Inspector를 사용하여 Compose에서 생성된 앱 레이아웃을 검사
        // 이를 보여주기 위해 상태에 따라 UI를 표시하도록 수정
        if (count > 0) {
            Text("You've had $count glasses")
        }
        // 람다 함수 형식으로 버튼 이벤트 추가
        // Column 안에 배치하고 버튼 composable에 맞게 Text를 세로로 정렬, Button 상단에 추가 패딩을 더해
        // Text에서 분리
        // 버튼에 enabled 속성을 주어 count가 10이되는 순간 버튼을 사용여부를 false로 변경
            Button(onClick = { count ++}, Modifier.padding(top = 8.dp), enabled = count < 10) {
                Text("Add one")
            }
    }
}
Stateless의 이점을 활요하기 위해 기존의 WaterCounter Composable을 리팩토링
 */
/*
- StatelessCounter의 역할 : count 표시, count를 늘릴 때 함수를 호출
- onIncrement -> 상태가 증가해야 할 때 호출
 */
@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier){
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

// 상태를 소유, count 상태를 보유하고 StatelessCounter 함수를 호출할 때 이 상태를 수정
@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    // count의 상태 지정
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count, {count++}, modifier)

    /*
    Stateless 재사용 테스트를 위한 임시 코드
    물과 주스의 잔 개수 , 독립 상태를 표시

    var waterCount by remember { mutableStateOf(0) }
    var juiceCount by remember { mutableStateOf(0) }
    StatelessCounter(waterCount, {waterCount++})
    StatelessCounter(juiceCount, {juiceCount++})
     */
}




@Composable
fun WellnessTaskItem(
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = taskName
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}




