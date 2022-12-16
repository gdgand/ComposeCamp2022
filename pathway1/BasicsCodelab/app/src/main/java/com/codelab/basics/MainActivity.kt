package com.codelab.basics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.IconButton
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) } //OnboardingScreen 의 변수를 호이스팅
    //remember : 컴포저블이 컴포지션에 유지되는 동안 작동
    //rememberSaveable : 구성 변경, 프로세스 중단 시에도 값 저장

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false})
        } else {
            Greetings()
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"   //Preview 이름
)
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
        OnboardingScreen(onContinueClicked = { })
    }
}

@Composable
fun Greeting(name: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        backgroundColor = Color.LightGray   //코드처럼 테마 색깔을 가져올 수 없어 아무 색이나..
    ) {
        Row(
            modifier = Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
                .padding(12.dp)
        ) {
            Column(
                Modifier.weight(1f)
            ) {
                Text(text = "Hello, ")
                Text(
                    text = name, style = MaterialTheme.typography.h5
//                    .copy(fontWeight =  FontWeight.ExtraBold)
                    , fontWeight = FontWeight.ExtraBold
                )
                if (expanded) {
                    Text(stringResource(id = R.string.lorem_ipsum))
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) stringResource(id = R.string.show_less) else stringResource(
                        id = R.string.show_more
                    )
                )
            }
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }  //$it : 목록의 index
) {
    //RecyclerView 와 유사하나 하위 요소 재활용을 하지 않음.
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        //Android studio 가 기본적으로 다른 items 함수를 선택하므로 별도로 선언 필요 (androidx.compose.foundation.lazy.items)
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }   //Hoisting 변수
    //Q. by 로 변수 생성 시 .value 가 필요 없는 이유?

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