package com.codelab.basics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

@Composable
fun OnboardingScreen(
    onNavigateToHome: () -> Unit,
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = UiText.DynamicString("Welcome to the Basics Codelab!").value,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                ),
                onClick = {
                    onNavigateToHome.invoke()
                },
            ) {
                Text(
                    text = UiText.DynamicString("Continue").value,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(
            onNavigateToHome = { }
        )
    }
}