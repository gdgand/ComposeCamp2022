package com.codelab.basics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    GreetingThemeColumn("Android")
                }
            }
        }
    }
}

/*** Practice 1. Greeting ***/
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    BasicsCodelabTheme {
//        Greeting("Android")
//    }
//}

/*** Practice 2. Greeting 2 ***/
@Composable
fun GreetingUi(name: String) {
    Surface(
        color = MaterialTheme.colors.primary // Surface 의 배경 색 설정
    ) {
        Text(text = "Hello $name!", modifier = Modifier.padding(24.dp)) // Text padding 추가
    }
}
//@Preview(showBackground = true)
//@Composable
//fun GreetingUiPreview() {
//    BasicsCodelabTheme {
//        GreetingUi("Android") // Composable 재사용
//    }
//}

/*** Practice 3. Greeting Column ***/
@Composable
fun GreetingColumn(name: String) {
    Surface(color = MaterialTheme.colors.primary) {
        // fillMaxWidth() = width:match_parent
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)) {
            Text(text = "Hello, ")
            Text(text = name)
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun GreetingColumnPreview() {
//    BasicsCodelabTheme {
//        GreetingColumn("Android") // Composable 재사용
//    }
//}

/*** Practice 4. Greeting Column with name list ***/
@Composable
fun GreetingListColumn(names: List<String>) {
    Surface(color = MaterialTheme.colors.primary) {
        Column(modifier = Modifier.padding(24.dp)) {
            // Composable 함수는 Kotlin 의 다른 함수처럼 사용 가능하다
            names.forEach { name ->
                Greeting(name = name)
            }
        }
    }
}
//@Preview(showBackground = true, widthDp = 320) // Preview width 설정 가능
//@Composable
//fun GreetingListColumnPreview() {
//    BasicsCodelabTheme {
//        GreetingListColumn(listOf("Android", "Kotlin")) // Composable 재사용
//    }
//}

/*** Practice 5. Greeting Column and Row ***/
@Composable
fun GreetingColumnRow(name: String) {
    Surface(color = MaterialTheme.colors.primary) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            Button(onClick = { /*TODO*/ }) {
                Text("Show more")
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun GreetingColumnRowPreview() {
//    BasicsCodelabTheme {
//        GreetingColumnRow("Android") // Composable 재사용
//    }
//}

/*** Practice 6. Greeting with State ***/
@Composable
fun GreetingWithMoreState(name: String) {
    val expanded = remember { mutableStateOf(false) }
    val extraPadding = if (expanded.value) 40.dp else 0.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello, ", color = Color.Black)
                Text(text = name, color = Color.Black)
            }
            OutlinedButton(
                shape = RoundedCornerShape(30.dp),
                border = BorderStroke(3.dp, Color.DarkGray),
                colors = ButtonDefaults.outlinedButtonColors(Color.LightGray),
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun GreetingWithMoreStatePreview() {
//    BasicsCodelabTheme {
//        GreetingWithMoreState("Android") // Composable 재사용
//    }
//}

/*** Practice 7. State Hoisting ***/
@Composable
fun MyAppHoisting(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            GreetingUi("Android")
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
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}
//@Preview(showBackground = true, widthDp = 320, heightDp = 320)
//@Composable
//fun MyAppHoistingPreview() {
//    BasicsCodelabTheme {
//        OnboardingScreen(onContinueClicked = {})
//    }
//}


/*** Practice 8. Lazy Column / Row ***/
@Composable
fun GreetingLazyColumn(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            GreetingWithMoreState(name = name)
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun GreetingLazyColumnPreview() {
//    BasicsCodelabTheme {
//        GreetingLazyColumn()
//    }
//}


/*** Practice 9. Savable Lazy + Animation ***/
@Composable
fun GreetingLazySavableColumn(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            GreetingWithMoreSavable(name = name)
        }
    }
}
@Composable
fun GreetingWithMoreSavable(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) } // 화면 회전에도 상태가 유지 되도록 rememberSaveable 사용
    val extraPadding by animateDpAsState(  // 애니메이션 적용
        if (expanded) 48.dp else 8.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello, ", color = Color.Black)
                Text(text = name, color = Color.Black)
            }
            OutlinedButton(
                shape = RoundedCornerShape(30.dp),
                border = BorderStroke(3.dp, Color.DarkGray),
                colors = ButtonDefaults.outlinedButtonColors(Color.LightGray),
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun GreetingLazySavableColumnPreview() {
//    BasicsCodelabTheme {
//        GreetingLazySavableColumn()
//    }
//}

/*** Practice 10. App Theme + Style + Resource ***/
@Composable
fun GreetingThemeColumn(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) } // 화면 회전에도 상태가 유지 되도록 rememberSaveable 사용
    val extraPadding by animateDpAsState(  // 애니메이션 적용
        if (expanded) 48.dp else 8.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.primary)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello, ", color = Color.Black, style = MaterialTheme.typography.h6)
                Text(text = name, color = Color.Black, style = MaterialTheme.typography.subtitle1)
            }
            IconButton(
                onClick = { expanded = !expanded }
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ArrowForward else Icons.Filled.ArrowDropDown,
                    contentDescription = stringResource(
                        id = if (expanded) R.string.show_less else R.string.show_more
                    )
                )
            }
        }
    }
}
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingThemeColumnPreview() {
    BasicsCodelabTheme {
        GreetingThemeColumn("Android")
    }
}
@Preview(showBackground = true, widthDp = 320, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun GreetingThemeColumnDarkPreview() {
    BasicsCodelabTheme {
        GreetingThemeColumn("Android")
    }
}