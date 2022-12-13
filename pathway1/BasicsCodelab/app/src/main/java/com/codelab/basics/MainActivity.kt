package com.codelab.basics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

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
private fun MyApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen (onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings()
    }
}

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" } ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name)
        }
    }
}

//@Composable
//private fun Greeting(name: String) {
//    var expanded by remember { mutableStateOf(false) }
//
//    /**
//     * animateDpAsState 컴포저블을 사용합니다.
//     * 이 컴포저블은 애니메이션이 완료될 때까지 애니메이션에 의해 객체의 value가 계속 업데이트되는 상태 객체를 반환합니다.
//     * 유형이 Dp인 목표 값을 사용합니다.
//     */
//    val extraPadding by animateDpAsState(
//        targetValue = if (expanded) 48.dp else 0.dp,
//        /**
//         * animateDpAsState는 애니메이션을 맞춤설정할 수 있는 animationSpec 매개변수를 선택적으로 사용합니다.
//         * 스프링 기반의 애니메이션 추가와 같이 더 재미있는 작업을 해보겠습니다.
//         *
//         * animate*AsState를 사용하여 만든 애니메이션은 중단될 수 있습니다.
//         * 즉, 애니메이션 중간에 목표 값이 변경되면 animate*AsState는 애니메이션을 다시 시작하고 새 값을 가리킵니다.
//         * 특히 스프링 기반 애니메이션에서는 중단이 자연스럽게 보입니다.
//         *
//         * 다양한 유형의 애니메이션을 살펴보려면 다양한 spring용 매개변수, 다양한 사양(tween, repeatable),
//         * 다양한 함수(animateColorAsState 또는 다양한 유형의 Animation API)를 사용해 보세요.
//         */
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )
//
//    Surface(
//        color = MaterialTheme.colors.primary,
//        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
//    ) {
//        Row(modifier = Modifier.padding(24.dp)) {
//            Column(modifier = Modifier
//                .weight(1f)
//                .padding(bottom = extraPadding.coerceAtLeast(0.dp)) // coerceAtLeast(minimumValue) minimumValue 이하로 내려가지 않는것을 보장
//            ) {
//                Text(text = "Hello, ")
//                /**
//                 * 일반적으로 MaterialTheme 내부의 색상, 모양, 글꼴 스타일을 유지하는 것이 훨씬 좋습니다. 예를 들어, 어두운 모드는 색상을 하드 코딩하는 경우 구현하기 어렵고 오류가 발생하기 쉬운 작업이 많이 요구됩니다.
//                 * 그러나 가끔 색상과 글꼴 스타일의 선택에서 약간 벗어나야 할 때도 있습니다. 이러한 상황에서는 기존에 사용하고 있는 색상이나 스타일을 기반으로 하는 것이 좋습니다.
//                 * 이를 위해 copy 함수를 사용하여 미리 정의된 스타일을 수정할 수 있습니다. 다음과 같이 숫자를 더 굵게 만들어 보세요.
//                 */
//                Text(
//                    text = name,
//                    style = MaterialTheme.typography.h4.copy(
//                        fontWeight = FontWeight.Bold
//                    )
//                )
//            }
//
//            OutlinedButton(
//                onClick = { expanded = !expanded }
//            ) {
//                Text(text = if (expanded) "Show less" else "Show more")
//            }
//        }
//    }
//}

@Composable
private fun Greeting(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
private fun CardContent(name: String) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }

            )
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        MyApp()
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "PreviewGreetingsDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun PreviewGreetings() {
    BasicsCodelabTheme {
        Greetings()
    }
}
