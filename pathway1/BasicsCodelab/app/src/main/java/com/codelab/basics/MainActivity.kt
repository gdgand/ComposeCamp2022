package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme
import com.codelab.basics.ui.theme.Shapes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp(Modifier.fillMaxSize())
            }
        }
    }
}

//컴포저블 재사용
@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        //내가 짠 코드
//        Box(modifier = Modifier.fillMaxWidth()) {
//            Column(modifier = Modifier
//                .padding(24.dp)
//            ) {
//                Text(text = "Hello,")
//                Text(text = name)
//            }
//
//            OutlinedButton(
//                modifier = Modifier
//                .align(Alignment.CenterEnd)
//                .padding(16.dp),
//                shape = RoundedCornerShape(20.dp),
//                onClick = { /*TODO*/ }
//            ) {
//                Text(text = "Show more")
//            }
//        }
        val expanded = remember { mutableStateOf(false) }
        val extraPadding = if(expanded.value) 48.dp else 0.dp
        //코드랩 솔루션 코드
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f).padding(bottom = extraPadding)
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }

            //ElevatedButton은 material3
            OutlinedButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(text = if(expanded.value) "show less" else "Show more")
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