package com.codelab.basics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.onboarding.OnBoarding
import com.codelab.basics.ui.theme.BasicsCodelabTheme

/***
 * Material 은 설정한 색상에 맞게 주변 child Compose 들이 자동으로 변한다
 * Modifier -> padding 값 등 수정할 수 있는 수정자를 의미
 ***/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    // var isExpanded = false // 절대 이렇게 해서는 안된다. -> 그 이유 해당 컴포즈가 추적을 하지 못하기 때문
    val isExpanded = remember { mutableStateOf(false) }

    val extraPending by animateDpAsState(
        targetValue = if (isExpanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPending.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello, ", color = Color.White)
                Text(text = name, color = Color.White, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ))
            }

            ElevatedButton(
                onClick = { isExpanded.value = !isExpanded.value }
            ) {
                Text(text = if (isExpanded.value) "Show Less" else "Show More")
            }
        }
    }
}

// 기본적으로 함수는 빈 수정자 ( Modifier ) 를 가지고 있는것이 좋다.
@Composable
private fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnBoarding) {
        OnBoarding { shouldShowOnBoarding = false }
    } else {
        Greetings()
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(
        content = {
            items(items = names) {
                Greeting(name = it)
            }
        }
    )
}

// Surface 란 무엇일까 ? -> https://stackoverflow.com/questions/65918835/when-should-i-use-android-jetpack-compose-surface-composable

@Preview(showBackground = true, name = "Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
private fun OnBoardingPreview() {
    BasicsCodelabTheme {
        OnBoarding(onClickListener = {})
    }
}
