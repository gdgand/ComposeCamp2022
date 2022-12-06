package com.codelab.theming.ui.start

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.young.metro.theme.MetroTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    var searchTextState by rememberSaveable { mutableStateOf("") }
    val focus = LocalFocusManager.current

    Surface(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeScreenSearchBar(
                searchTextState = searchTextState,
                onSearchText = {
                    focus.clearFocus()
                },
                onSearchTextChange = {},
                onSearchClose = { searchTextState = "" }
            )

            HomeSearchStationList()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenSearchBar(
    modifier: Modifier = Modifier,
    searchTextState: String,
    onSearchTextChange: (String) -> Unit,
    onSearchText: (String) -> Unit,
    onSearchClose: () -> Unit
) {
    TextField(
        value = searchTextState,
        textStyle = MaterialTheme.typography.bodyLarge,
        onValueChange = { searchText: String -> onSearchTextChange(searchText) },
        leadingIcon = {
            IconButton(
                onClick = { onSearchText(searchTextState) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            if (searchTextState.isNotEmpty()) {
                IconButton(
                    onClick = { onSearchClose() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null
                    )
                }
            }
        },
        label = {
            Text(
                text = "입력해주세요.",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .padding(top = 50.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSearchText(searchTextState) }
        )
    )
}

@Preview(showSystemUi = true, name = "SearchBar PreView")
@Composable
fun HomeSearchBarPreView() {
    MetroTheme {
        var test by remember { mutableStateOf("") }

        HomeScreenSearchBar(
            searchTextState = test,
            onSearchTextChange = { test = it},
            onSearchText = {},
            onSearchClose = { test = "" }
        )
    }
}