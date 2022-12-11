package com.codelab.basics

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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.android.LayoutCompat
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
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
                    color = MaterialTheme.colors.background
                ) {
                     // MyApp(modifier = Modifier.fillMaxSize())
                     NewMyApp(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Card(
         contentColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting(name = "Hello")
}

// region Surface 내부에 중첩된(여기서는 Text) 요소들은 surface color 위에 그려진다.
@Composable
fun SurfaceGreeting(name: String) {
    Surface(color = MaterialTheme.colors.primary) {
        /*텍스트 color를 설정하지 않았는데 흰색으로 지정된 이유는
        Surface에서 기본 색상으로 설정되어 있으면 그 위에 있는 모든 텍스트가 테마에 정의된
        onPrimary 색상을 사용하게 된다.
        Theme.kt에 lightColors 함수에 onPrimary: Color = Color.White 정의되어 있음*/
        Text(text = "Hello $name!")
    }
}

@Preview(showBackground = true)
@Composable
fun SurfaceGreetingPreview() {
    SurfaceGreeting(name = "Kotlin")
}
// endregion

// region modifier는 UI 요소에서 상위 레이아웃 내에서 레이아웃, 표시 또는 동작하는 방법을 알려준다.
@Composable
fun GreetingModifier(name: String) {
    Surface(color = MaterialTheme.colors.primary) {
        Text(
            text = "Hello $name",
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingModifierPreview() {
    GreetingModifier(name = "Kotlin")
}
// endregion

// region composable function에 modifier를 empty modifier로 할당하는 이유
/*UI에 더 많은 구성요소를 추가할 수록 많은 중첩이 생긴다.
함수가 정말 커지면 가독성에 영향을 줄 수 있기 때문에 재사용 가능한 작은 구성 요소를 만들어서
UI 요소를 쉽게 구축할 수 있고 각각 화면의 작은 부분을 담당하며 독립적으로 수정할 수도 있다.
제일 좋은 방법으로는 컴포저블 함수 파라미터에 modifier(수정자, 한정자)를 빈 값으로 할당하는 것이다. (가능하면 함수내에서 호출하는 첫번째로)
이런 식으로 호출하면 함수 외부에서 레이아웃 동작을 조정할 수 있다.*/
@Composable
private fun MyApp(
    modifier: Modifier = Modifier,
     names: List<String> = listOf("World", "Compose")
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        names.forEach {
            // Greeting(name = it)
            // WeightGreeting(name = it)
             StateGreeting(name = it)
        }
    }
}
// endregion

// region weight modifier는 사용 가능한 모든 공간을 채우도록 유연하게 만들고 가중치(weight)가 없는 다른 요소를 효과적으로 밀어낸다
@Composable
private fun WeightGreeting(name: String) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            ElevatedButton(
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Show more")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeightGreetingPreview() {
    WeightGreeting(name = "GOOD")
}
// endregion

// region state 사용하는 이유
/*StateGreeting이 호출될 때마다 expanded가 false로 설정되므로 recomposition이 일어나지 않는다.
컴포저블 내부 상태를 추가하려면 state 및 mutableState를 사용해야한다.
(state, mutableState는 일부 값을 보유하고 해당 값이 변경될 때마다 UI update를 트리거하는 인터페이스이다.)*/
@Composable
private fun StateGreeting(name: String) {
    // var expanded = false // Don't do this!
    // val expanded = mutableStateOf(false) // Don't do this!
    val expanded = remember { mutableStateOf(false) }
    val extradPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extradPadding)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            ElevatedButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(text = if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}
// endregion

// region state hoisting 사용 이유
/*예를 들어 어떤 화면에 진입 전 새로운 화면을 추가하고 해당 화면에서 클릭 액션이 이뤄졌을 때
다음 화면으로 넘어간다고 하자. 이럴 때 컴포저블 함수에서 여러 함수에서 읽거나 수정한 공통 조상?이 있어야 한다.
이 프로세스를 state hoisting 이라한다. state를 hoisting 가능하게 만들면 상태 복제 및 버그를 방지하고
테스트를 쉽게 할 수 있으며 컴포저블을 재사용하는데 도움이 된다. 반대로 컴포저블의 부모에 의해 제어될
필요가 없는 상태는 hoisting 되어서는 안된다.*/
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
        Text(text = "Welcom to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text(text = "Continue")
        }
    }
}

@Composable
fun NewMyApp(modifier: Modifier = Modifier) {
    /*remember는 오직 composable 이 컴포지션에 유지되는 동안에만 동작한다.
    즉, 화면전환 또는 시스템 재구성, recomposition 또는 프로세스가 종료되면 상태를 잃는다.
    상태를 유지하고 싶다면 rememberSaveable을 사용하면 된다.*/
    // var shouldShowOnboarding by remember { mutableStateOf(true) }
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier = modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            // Greetings()
            LazyColumnGreetings(modifier = modifier)
        }
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        names.forEach {
            StateGreeting(name = it)
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingsPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@Preview
@Composable
fun NewMyAppPreview() {
    BasicsCodelabTheme {
        NewMyApp(modifier = Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
private fun OnboardingScreenPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = { })
    }
}
// endregion

// region LazyColumn == RecyclerView
/*기존에 Android View RecyclerView는 recycle()를 통해 뷰를 재활용 하지만
LayColumn 뷰를 재사용하지 않는다. 스크롤 시 새로운 composable를 내보낸다.
Android View를 인스턴스화 해서 보여주는 방식(RecyclerView)보다 새로운 컴포저블을 보내는게
성능적으로 저렴하기 떄문이다.*/
@Composable
private fun LazyColumnGreetings(
    modifier: Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) {
            // Greeting(name = it)
            AnimateGreeting(name = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LazyColumnGreetingPreview() {
    LazyColumnGreetings(modifier = Modifier)
}
// endregion

// region animate
@Composable
private fun AnimateGreeting(name: String) {
    var expanded by remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            ElevatedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }
    }
}
// endregion

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
                text = name, style = MaterialTheme.typography.subtitle1.copy(
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
                    "Show less"
                } else {
                    "Show more"
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
