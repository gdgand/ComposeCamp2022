package com.codelab.basics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContent를 사용하여 레이아웃 정의 후 구성 가능한 함수(@Compose 함수)를 호출
        setContent {
            // 구성 가능한 함수 스타일을 지정
            // 스타일을 지정할 경우 content 내부에 있는 구성요소로 하향 적용.
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
        // rememberSaveable을 사용하여 현재 상태를 유지 시켜줌.
        var shouldShowOnbording by rememberSaveable { mutableStateOf(true) }

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

@Composable
private fun Greetings(
        modifier: Modifier = Modifier,
        names: List<String> = List(1000) {"$it"}            // 1000개의 목록을 생성.
    ) {
        // 리사이클러뷰와 동일한 역할
        LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
           items(items = names) { name ->
               Greeting(name = name)
           }
        }
    }
// Composable 주석을 통해 구성 가능한 함수를 만들어줌.
// Greeting을 Card Composable로 재구성 해줌.
// Greeting 내부의 column에 새로운 Text 추가, CardDefaults.cardColors를 호출하고 변경하려는 색상을 재정의하여
// Card의 색상을 변경.
@Composable
private fun Greeting(name: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
private fun CardContent(name: String) {
    // 기본 상태 정의
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                // 열고 닫을 애니메이션을 정의함.
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ){
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                // 헤더 스타일 지정
                text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy +" +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = {expanded = !expanded}) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    // strings.xml에 정의해둔 문자열 가져오기
                    stringResource(R.string.show_less)
                }else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

// 다크모드 미리보기 설정
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    widthDp = 320,
    name = "Dark"
)

// @Preview => 미리보기 사용을 위한 어노테이션 지정, 동일한 파일에 미리보기를 여러 개 만들고 이름을 지정 가능.
// 미리보기 가로 크기 추가(320dp)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})        // 빈 람다 표현식에 할당하는 것은 아무 작업도 하지 않음을 의미.
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