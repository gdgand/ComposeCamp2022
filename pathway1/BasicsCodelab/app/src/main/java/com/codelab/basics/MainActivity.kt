package com.codelab.basics

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MyApp()
                }
            }
        }
    }
}

private val names = List(1000) { "$it" }

enum class SelectedComposable {
    ONLY_REMEMBER,
    ONLY_STATE,
    REMEMBER_SAVABLE_STATE
}

var num = 0

@SuppressLint("UnrememberedMutableState")
@Composable
fun MyApp() {
    var compose: Boolean by remember { mutableStateOf(true) }
    fun toggleComposition() {
        compose = !compose
    }

    var selected: SelectedComposable by rememberSaveable {
        mutableStateOf(SelectedComposable.ONLY_REMEMBER)
    }

    Surface {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioItem(
                    selected = selected == SelectedComposable.ONLY_REMEMBER,
                    onClick = { selected = SelectedComposable.ONLY_REMEMBER },
                    text = "only remember"
                )
                RadioItem(
                    selected = selected == SelectedComposable.ONLY_STATE,
                    onClick = { selected = SelectedComposable.ONLY_STATE },
                    text = "only state"
                )
                RadioItem(
                    selected = selected == SelectedComposable.REMEMBER_SAVABLE_STATE,
                    onClick = { selected = SelectedComposable.REMEMBER_SAVABLE_STATE },
                    text = ""
                )
            }

            when (selected) {
                SelectedComposable.ONLY_REMEMBER -> {
                    OnlyRemember()
                }
                SelectedComposable.ONLY_STATE -> {
                    if (compose) {
                        OnlyState()
                    } else {
                        Button(onClick = {
                            toggleComposition()
                        }) {
                            Text("toggle")
                        }
                    }
                }
                SelectedComposable.REMEMBER_SAVABLE_STATE -> {
                    if (compose) {
                        RememberSavableStateTest(::toggleComposition)
                    } else {
                        Button(onClick = {
                            toggleComposition()
                        }) {
                            Text("toggle")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RadioItem(selected: Boolean, text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onClick.invoke()
        }
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(text = text)
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun OnlyRemember() {

    val context = LocalContext.current
    val mutableState = mutableStateOf(false)
    fun invokeRecomposition() {
        mutableState.value = !(mutableState.value)
    }

    Toast.makeText(
        context,
        "RememberStateTest()",
        Toast.LENGTH_SHORT
    ).show()

    var count: Int = remember { 0 }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column {
            Text("$count, ${mutableState.value}")
            Button(onClick = {
                count += 1
                Toast.makeText(
                    context,
                    "current count is $count",
                    Toast.LENGTH_SHORT
                ).show()
            }
            ) {
                Text(text = "click to increase count")
            }
            Button(onClick = { invokeRecomposition() }) {
                Text(text = "invoke recomposition")
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun OnlyState() {

    val context = LocalContext.current

    val count = mutableStateOf(0)

    Toast.makeText(
        context,
        "composition emitted, count: ${count.value}",
        Toast.LENGTH_SHORT
    ).show()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column {
            Text("${count.value}")
            Button(onClick = { count.value += 1 }
            ) {
                Text(text = "click to increase count")
            }
        }
    }
}

@Composable
fun RememberSavableStateTest(removeComposition: () -> Unit) {

    var count: Int by rememberSaveable { mutableStateOf(0) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column {
            Text("$count")
            Button(onClick = { count += 1 }
            ) {
                Text(text = "click to increase count")
            }
        }
    }
}

@Composable
fun Greetings(names: List<String> = listOf("Compose", "World")) {
    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(vertical = 4.dp)
    ) {
        items(names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(
                vertical = 4.dp,
                horizontal = 8.dp
            )
    ) {
        CardContens(name)
    }
}

@Composable
fun CardContens(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1F)
                .padding(12.dp)
        ) {
            Text(text = "Hello,")
            Text(
                text = "$name!",
                style = MaterialTheme
                    .typography
                    .h4
                    .copy(fontWeight = FontWeight.ExtraBold)
            )
            if (expanded) {
                Text(
                    ("Composem ipsum color sit lazy, " + "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(
            onClick = { expanded = !expanded }
        ) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) "Show less" else "Show more"
            )
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
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
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen {}
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@Preview(showBackground = true)
@Composable
fun Test() {
    Text(
        text = "hello",
        modifier = Modifier
            .padding(4.dp)
            .background(color = colorResource(id = R.color.black))
            .padding(4.dp)
            .background(color = colorResource(id = R.color.teal_200).copy(alpha = 0.5f)
            )
    )
}
