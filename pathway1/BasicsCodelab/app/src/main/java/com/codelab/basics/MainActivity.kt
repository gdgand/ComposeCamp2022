package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

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
private fun MyApp(
  modifier: Modifier = Modifier,
) {
  var shouldShowOnBoarding by remember {
    mutableStateOf(true)
  }
  Surface(modifier) {
    if (shouldShowOnBoarding) {
      OnBoardingScreen(onContinuedClicked = { shouldShowOnBoarding = false })
    } else {
      Greetings()
    }
  }
}


@Preview(showBackground = true, widthDp = 320)
@Composable
private fun MyAppPreview() {
  BasicsCodelabTheme {
    MyApp(Modifier.fillMaxSize())
  }
}

@Composable
private fun Greetings(
  modifier: Modifier = Modifier,
  names: List<String> = listOf("World", "Compose"),
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
fun Greeting(name: String) {
  val expaned = remember { mutableStateOf(false) }
  val extraPadding = if (expaned.value) 48.dp else 0.dp
  Surface(
    color = MaterialTheme.colorScheme.primary,
    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
  ) {
    Row(modifier = Modifier.padding(24.dp)) {
      Column(modifier = Modifier
        .weight(1f)
        .padding(bottom = extraPadding)
      ) {
        Text(text = "Hello, ")
        Text(text = name)
      }
      ElevatedButton(onClick = { expaned.value = !expaned.value }) {

        Text(if (expaned.value) "Show less" else "Show more")
      }
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

@Composable
fun OnBoardingScreen(
  onContinuedClicked: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text("Welcome to the Basic Coldlab!")
    Button(
      modifier = Modifier.padding(vertical = 24.dp),
      onClick = onContinuedClicked
    ) {
      Text(text = "Continue")
    }
  }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnBoarindgPreview() {
  BasicsCodelabTheme {
    OnBoardingScreen(onContinuedClicked = {})
  }
}
