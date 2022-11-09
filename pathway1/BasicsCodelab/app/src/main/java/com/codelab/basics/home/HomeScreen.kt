package com.codelab.basics

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.home.HomeData
import com.codelab.basics.home.HomeViewModel
import com.codelab.basics.ui.theme.BasicsCodelabTheme

@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen(
    onNavigateToMain: () -> Unit,
    viewModel: HomeViewModel = HomeViewModel(),
    isPreview: Boolean = false,
) {
    val homeDatas = viewModel.homeDatas.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        LazyColumn {
            items(homeDatas) { homeData ->
                HomeDataCardView(homeData = homeData)
            }
        }

        if(isPreview) {
            HomeDataCardView(homeData = HomeViewModel.previewData)
            HomeDataCardView(
                homeData = HomeViewModel.previewData,
                state = mutableStateOf(true)
            )
        }
    }

    BackHandler(enabled = true) {
        onNavigateToMain.invoke()
    }
}

@Composable
private fun HomeDataCardView(
    homeData: HomeData,
    state: MutableState<Boolean> = mutableStateOf(false)
) {
    val isExpanded = remember { state }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 8.dp
            ).animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp,
    ) {
        Box(
            modifier = Modifier.padding(12.dp),
        ) {
            Column(
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Text(
                    text = homeData.title,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = homeData.index.toString(),
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colors.onPrimary,
                )
                if(isExpanded.value) {
                    Text(
                        text = homeData.content,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                onClick = {
                    isExpanded.value = !isExpanded.value
                }
            ) {
                Icon(
                    imageVector = if(isExpanded.value) {
                        Icons.Filled.ExpandLess
                    } else {
                        Icons.Filled.ExpandMore
                    },
                    contentDescription = if(isExpanded.value) {
                        "Show less"
                    } else {
                        "Show more"
                    },
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    BasicsCodelabTheme {
        HomeScreen(
            onNavigateToMain = { /*TODO*/ },
            isPreview = true
        )
    }
}