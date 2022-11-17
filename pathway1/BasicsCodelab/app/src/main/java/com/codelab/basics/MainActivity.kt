package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContent를 사용하여 레이아웃 정의 후 구성 가능한 함수(@Compose 함수)를 호출
        setContent {
            // 구성 가능한 함수 스타일을 지정
            BasicsCodelabTheme {
                MyApp(modifier = Modifier.fillMaxSize())     // 너비, 높이 모두 전체 채우기
            }
        }
    }
}
    // Composable 재사용: 가독성과 독립적 수정을 위해 따로 함수를 만들어줌.
    @Composable
    fun MyApp(modifier: Modifier = Modifier) {
        // 타 화면을 표시하는 로직
        var shouldShowOnbording by remember { mutableStateOf(true) }

        // OnboardingScreen이 상태를 변경하도록 하는 대신 사용자가 Continue 버튼을 클릭했을 때
        // 앱에 알리도록 한다. 이벤트 전달은 콜백으로 전달.
        Surface(modifier) {
            if (shouldShowOnbording) {
                OnboardingScreen(onContinueClicked = { shouldShowOnbording = false })
            }else {
                Greetings()
            }
        }
    }

    @Composable
    private fun Greetings(
        modifier: Modifier = Modifier,
        names: List<String> = listOf("World", "Compose")
    ) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            for (name in names) {
                Greeting(name = name)
            }
        }
    }

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun GreetingsPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}


// Composable 주석을 통해 구성 가능한 함수를 만들어줌.
@Composable
private fun Greeting(name: String) {
    /*
    UI를 지정하기 위해 Text() 를 Surface() {} 로 감싸줌
    Surface()로 배경색 지정이 가능함.
     */
    // Composable에 내부 상태를 추가하려면 mutableStateOf 함수를 사용.
    // 리컴포지션 간에 상태를 유지하기 위해 remember를 사용하여 변경 가능한 상태를 기억해야 함.
    val expanded = remember { mutableStateOf(false) }

    // 상태에 따라 달라지는 추가 변수 , 간단한 계산을 실행하는 변수
    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            // weight를 사용해 가중치를 주어 가중치가 적용되지 않은 요소를 밀어내고 해당 버튼을 배치함.
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
            Text(text = "Hello, ")
            Text(text = name)
        }
        // 코너 커스텀이 들어간 버튼 생성 후 텍스트 변경 설정
        ElevatedButton(
            onClick = { expanded.value = !expanded.value} ,
        ) {
            Text(if (expanded.value) "Show less" else "Show more")
        }

        }

    }
}

// 상태가 아닌 함수를 OnboardingScreen에 전달하는 방식으로 Composable의 재사용성 가능성을 높이고
// 다른 Composable이 상태를 변경하지 않도록 보호함.
@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier
                .padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})        // 빈 람다 표현식에 할당하는 것은 아무 작업도 하지 않음을 의미.
    }
}

// @Preview => 미리보기 사용을 위한 어노테이션 지정, 동일한 파일에 미리보기를 여러 개 만들고 이름을 지정 가능.
// 미리보기 가로 크기 추가(320dp)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        MyApp()
    }
}

// MyApp의 Composable의 Preview
@Preview
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}