package com.codelab.basics

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.home.HomeData
import com.codelab.basics.home.HomeViewModel
import com.codelab.basics.ui.theme.BasicsCodelabTheme

@Composable
fun HomeScreen(
    onNavigateToMain: () -> Unit,
    viewModel: HomeViewModel = HomeViewModel(),
    isPreview: Boolean = false,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        val homeDatas = viewModel.homeDatas.value
        
        LazyColumn {
            items(homeDatas) { homeData ->
                HomeDataCardView(homeData = homeData)
            }
        }

        if(isPreview) {
            HomeDataCardView(homeData = HomeViewModel.previewData)
        }
    }
}

@Composable
private fun HomeDataCardView(
    homeData: HomeData
) {
    val isExpanded = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 18.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ).clickable {
                isExpanded.value = !isExpanded.value
            },
        backgroundColor = MaterialTheme.colors.secondary,
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
                    color = MaterialTheme.colors.onSecondary
                )
                Text(
                    text = homeData.index.toString(),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSecondary,
                    fontWeight = FontWeight.Bold,
                )
                if(isExpanded.value) {
                    Text(
                        text = homeData.content,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSecondary
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
                        Icons.Default.KeyboardArrowDown
                    } else {
                        Icons.Default.KeyboardArrowUp
                    },
                    tint = MaterialTheme.colors.onSecondary,
                    contentDescription = "Expand or Collapse button"
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