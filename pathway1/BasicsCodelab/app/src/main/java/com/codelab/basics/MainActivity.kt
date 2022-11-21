package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //레이아웃 정의
        setContent {
            //구성 가능한 함수의 스타일을 지정하는 방법
            BasicsCodelabTheme {
                /*
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }*/
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

//호이스팅 - 구성 가능한 함수에서 여러 함수가 읽거나 수정하는 상태는 공통의 상위 항목에 위치해야 합니다. 이 프로세스
//장점 - 상태가 중복되지 않고 버그가 발생하는 것을 방지할 수 있으며 컴포저블을 재사용할 수 있고 훨씬 쉽게 테스트할 수 있습니다
//여기서 shouldShowOnboarding에 엑세스할 수 없는데,
//OnboardingScreen에서 만든 상태를 MyApp컴포저블과 공유해야함
//상태 값을 상위 요소와 공유하는 대신 상태를 호이스팅한다.
//즉, 상태 값에 액세스해야 하는 공통 상위 요소로 상태 값을 이동하기만 하면 됩니다.
@Composable
private fun MyApp(
    modifier: Modifier = Modifier
) {
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
    /*Surface(
        modifier = modifier,
        //color = MaterialTheme.colors.background
    ) {
        Greeting(name = "Android")
    }*/
    /*Column(modifier = modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }*/
}

@Preview //UI 프리뷰
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
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

@Composable
private fun Greeting(name: String) {
    //이 변수를 compose에서 추적하고 있지 않음
    //내부 상태를 추가하기 위해서는 mutableStateOf함수를 사용
    //var expanded = false

    //상태를 유지하기 위해 remember사용
    val expanded = remember {
        mutableStateOf(false)
    }
    //간단한 계산
    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        //가로배치
        Row(modifier = Modifier.padding(24.dp)) {
            //세로배치, weight를 통해 가중치가 없는 다른 요소를 효과적으로 밀어내어 모든 공간을 채워줌
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding) //바텀 크기 계산
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                //true일 때(펴져 있을 때, 클릭 했을 때)
                Text(if (expanded.value) " Show less" else "Show more")
            }
        }

        //Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    //by - 매번 value를 입력할 필요가 없도록 해주는 속성 위임
    /*var shouldShowOnboarding by remember {
         mutableStateOf(true)
    }*/
    //화면 중앙에 콘텐츠 표시
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
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
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun DefaultPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}
