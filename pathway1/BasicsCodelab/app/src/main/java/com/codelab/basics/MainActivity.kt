package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

/**
 * 컴포즈를 사용해도 Activity가 앱의 진입점
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        기존에 View 방식에서 setContentView(XML Layout file) 호출과 다르게,
        setContent 블럭에서 컴포저블을 호출
         */
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                GreetingApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun GreetingApp(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Android", "Compose"),
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Hello, ")
                Text(text = "${name}!")
            }

            ElevatedButton(
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Show more")
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        GreetingApp()
    }
}