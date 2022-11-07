package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                MyApp()
            }
        }
    }
}

@Composable
private fun MyApp(names:List<String> = listOf("World","Compose")) {
    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            for (name in names){
                Greeting(name = name)
            }
        }

    }
}

@Composable
fun Greeting(name: String) {
    // 테마만 적용했을 뿐인데 글자가 흰색인 이유?
    /** 머티리얼 구성요소는 앱에 넣고자하는 공통기능(텍스트 색등)을 처리하여 더 나은 환경을 만들도록 제공 */
    Surface(color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)){
        Row(modifier = Modifier.padding(24.dp)){
            Column(modifier = Modifier
                .weight(1f)) {
                Text(text = "Hello,")
                Text(text = name)
            }
            OutlinedButton(onClick = {}) {
                Text(text = "Show more")
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