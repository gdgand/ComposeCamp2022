package com.codelab.basics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose", "Kotlin", "Android")
) {
    Column(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
    ) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}

@Composable
fun MyOnBoarding(modifier: Modifier = Modifier) {

    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    //remember의 경우 컴포저블이 유지되는 동안에만 작동, 기기 회전등의 활동이 다시 시작되게 될경우 상태가 손실됨,

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
            //click event
        } else {
            Greetings()
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose", "Kotlin", "Android"),
    nameList: List<String> = List(1000) { "$it" }
) {
    //LazyColumn과 LazyRow는 Android 뷰의 RecyclerView와 동일하다.
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = nameList)
        //androidx.compose.foundation.lazy.items import필요함
        { nameList ->
            Greeting(name = nameList)
        }
    }
}

@Composable
private fun Greeting(name: String) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    //상태를 저장하고 이를 기억하여 유지함
    val extraPadding by animateDpAsState(
        if (isExpanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    //니메이션이 완료될 때까지 애니메이션에 의해 객체의 value가 계속 업데이트되는 상태 객체를 반환

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            //패딩 값이 음수가 되지 않도록 조정
            ) {
                Text(text = "Hello, ")
                Text(text = name,style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold
                )
            }
            ElevatedButton(
                onClick = { isExpanded = !isExpanded }
            ) {
                Text(color = Color.Black, text = if (isExpanded) "Less" else "More")
            }
        }
    }
}

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

//다크모드 프리뷰 추가
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}


@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        MyApp()
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun GreetingsPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}