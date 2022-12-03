package com.codelab.basics

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
private fun MyApp(
    modifier: Modifier = Modifier,

    ) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    Surface(
        modifier = modifier,
    ) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1_000) { "$it" }
) {
    Surface(
        modifier = modifier.then(Modifier.padding(vertical = 4.dp)),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn {
            items(names) { name ->
                Greeting(name)
            }
        }
    }
}

@Composable
private fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
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

@Composable
private fun Greeting(name: String, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.then(Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
    ) {
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
            ) {
                Text(text = "Hello, ")
                Text(
                    text = name, style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                if(expanded){
                    Text(text = "composem ipsum".repeat(4))
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) stringResource(R.string.cta_show_less) else stringResource(R.string.cta_show_more)
                )
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    widthDp = 320,
    heightDp = 320,
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    widthDp = 320,
    heightDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun DefaultPreview() {
    BasicsCodelabTheme {
        Surface {
            MyApp()
        }
    }
}

@Preview(
    name = "Greetings Light Mode",
    showBackground = true,
    widthDp = 320,
    heightDp = 320,
)
@Preview(
    name = "Greetings Dark Mode",
    showBackground = true,
    widthDp = 320,
    heightDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun GreetingsPreview() {
    BasicsCodelabTheme {
        Surface {
            Greetings()
        }
    }
}

@Preview(
    name = "Onboarding Light Mode",
    showBackground = true,
    widthDp = 320,
    heightDp = 320,
)
@Preview(
    name = "Onboarding Dark Mode",
    showBackground = true,
    widthDp = 320,
    heightDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun OnboardingPreview() {
    BasicsCodelabTheme {
        Surface {
            OnboardingScreen(onContinueClicked = {})
        }
    }
}