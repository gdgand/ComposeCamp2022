/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelab.basiclayouts

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basiclayouts.ui.theme.MySootheTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MySootheApp() }
    }
}

// Step: Search bar - Modifiers
// 검색창을 구현 하기 위해 Compose Material에는 TextField라는 Composable이 있음.
// 텍스트 필드 값은 하드코딩, onValueChanger 콜백은 아무런 작업도 하지 않음.
/*
- modifier(수정자):
역할
1. Composable의 크기, 레이아웃, 동작, 모양을 변경
2. 접근성 라벨과 같은 정보 추가
3. 사용자 입력 처리
4. 요소 클릭, 스크롤, 드래그 가능 or 확대/축소를 가능하게 만드는것과 같은 높은 수준의 상호 작용 추가

 */
@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    TextField(
        value = "" ,
        onValueChange = {},
        /*
        leadingIcon = 다른 Composable을 받는 매개변수 leadingIcon, 내부에는 Icon 설정 가능.
        Search 아이콘 설정, textField의 배경색을 surface로 설정.
         */
        leadingIcon = {
            Icon(
               imageVector = Icons.Default.Search,
               contentDescription = null
            )
        },
        // textField의 배경색을 MaterialTheme의 surface 색으로 설정.
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        // string.xml에 정의된 텍스트를 가져옴.
        placeholder = {
            Text(stringResource(id = R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp))     // textField의 높이 최소 56dp 설정
}

// Step: Align your body - Alignment
// 신체의 조화 - 정렬 구현,
/*
- 이미지의 높이 88dp, 텍스트 기준선과 이미지 기준선의 간격은 24dp, 기준선과 요소 하단 사이의 간격은 8dp
- 서체 스타일은 H3, 텍스트의 기준선 -> 문자가 놓여 있는 선을 가리킴.
해당 Composable 구현을 위해서는 Image와 text Composable이 필요한데 두 Composable을 세로 방향으로 배치.

- size와 clip, contentScale을 통해 Image Composable을 수정.
1. size : fillMaxWidth와 heightIn과 마찬가지로 특정 크기에 맞게 Composable을 조정.
2. clip : Composable의 모양을 조정.
3. 이미지의 크기를 올바르게 조절하기 위해서 Image의 contentScale을 사용.
ex) Fit, FillBounds, Crop 등이 있음.
 */
@Composable
fun AlignYourBodyElement(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    // 세로 방향으로 배치하기 위해 Column Scope로 작성.
    // 설명이 필요없는 장식용 이미지일경우 contentDescription 옵션을 null로 처리해줌.
    // 추가 설명이 필요할 경우에만 사용
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally     // 글자 정렬
    ) {
        Image(
              painter = painterResource(drawable),
              contentDescription = null,
              contentScale = ContentScale.Crop,
              modifier = Modifier
                  .size(88.dp)          // 사이즈 88dp
                  .clip(CircleShape)    // 원 모양으로
        )
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.h3,        // 서체를 h3로
            modifier = Modifier.paddingFromBaseline(    // 텍스트 요소의 기준선 간격을 업데이트
                top = 24.dp, bottom = 8.dp          // top : 24dp, bottom: 8dp
            )

        )
    }
}

// Step: Favorite collection card - Material Surface
@Composable
fun FavoriteCollectionCard(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,      // 수직 정렬
            modifier = Modifier.width(192.dp)
        ) {
            Image(
                painter = painterResource(drawable) ,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(56.dp)
            )
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}

// Step: Align your body row - Arrangements
/*
LazyRow의 하위 요소는 Composable이 아님.
대신 Composable을 목록 항목으로 내보내는 item 및 items와 같은 메서드를 제공하는 Lazy 목록 DSL을 사용.
alignment
 */
@Composable
fun AlignYourBodyRow(
    modifier: Modifier = Modifier
) {
   LazyRow(
       horizontalArrangement = Arrangement.spacedBy(8.dp),
       contentPadding = PaddingValues(horizontal = 16.dp) ,
       modifier = modifier
   ) {
       items(alignYourBodyData) { item ->
           AlignYourBodyElement(item.drawable, item.text)
       }
   }
}

// Step: Favorite collections grid - LazyGrid
/*
- 항목-그리드 요소 매핑을 더 효과적으로 지원하는 LazyHorizontalGrid를 사용.
 */
@Composable
fun FavoriteCollectionsGrid(
    modifier: Modifier = Modifier
) {
    // 두 개의 고정 행이 있는 그리드 구현
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2) ,
        contentPadding = PaddingValues(horizontal = 16.dp),      // 가로 16dp padding을 줌
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.height(120.dp)
    ) {
        items(favoriteCollectionsData) { item ->
            FavoriteCollectionCard(
                drawable = item.drawable,
                text = item.text,
                modifier = Modifier.height(56.dp)
            )

        }
    }

}

// Step: Home section - Slot APIs
/*
- 제목과 슬롯 콘텐츠를 받도록 HomeSection Composable을 조정.
- 이 HomeSection을 '신체의 조화' 제목 및 콘텐츠와 함께 호출하도록 연결된 미리보기도 조정.
- container 함수 생성을 위해 content을 Unit으로 반환하는 고차함수를 선언하고
- 해당 파라미터를 선언했기때문에 HomeSection 호출해서 원하는 형태로 view를 그릴수 있게 함.
-> 정리하면 내부적으로 Container 내에 내가 원하는 컴포넌트를 넣어주기 위함.
 */
@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    /*
    디자인 적용 -> 모두 대문자로 표시, H2 서체, 레드라인 디자인과 같은 Padding값을 줄 것
     */
    Column(modifier) {
        Text(
            // uppercase -> deprecated,
            text = stringResource(title).uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.h2,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 8.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

// Step: Home screen - Scrolling
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            // 수동으로 스크롤 효과를 반영
            // 수직간의 Padding을 16dp만큼 주었기 때문에 Spacer 옵션을 삭제.
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        SearchBar(Modifier.padding(horizontal = 16.dp))
        HomeSection(title = R.string.align_your_body) {
            AlignYourBodyRow()
        }
        HomeSection(title = R.string.favorite_collections) {
            FavoriteCollectionsGrid()
        }
    }
}

// Step: Bottom navigation - Material
/*
- 기존의 BottomNavigation을 통해 화면을 전환 할수 있었는데 해당 BottomNavigation을 구현하기 위해
Compose Material 라이브러리의 일부인 BottomNavigation Composable을 사용.
해당 Composable 내부에서 BottomNavigationItem 을 추가해준다.
 */
@Composable
private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
    BottomNavigation(
        // 배경 색상을 추가해주면 아이콘과 텍스트의 색상이 테마의 onBackGround 색상으로 자동 조정 된다.
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        BottomNavigationItem(
            // 아이콘 추가
            icon = {
                Icon(imageVector = Icons.Default.Spa ,
                     contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_home))
            },
            // 기본 클릭되어 있는지 여부(true)
            selected = true ,
            onClick = {}
        )

        BottomNavigationItem(
            // 아이콘 추가
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_profile))
            },
            // 기본 클릭되어 있는지 여부(false)
            selected = false,
            onClick = {}
        )
    }
}

// Step: MySoothe App - Scaffold
@Composable
fun MySootheApp() {
    MySootheTheme {
        Scaffold(
            bottomBar = { SootheBottomNavigation()}
        ) { padding ->
            HomeScreen(Modifier.padding(padding))

        }
    }
}

private val alignYourBodyData = listOf(
    R.drawable.ab1_inversions to R.string.ab1_inversions,
    R.drawable.ab2_quick_yoga to R.string.ab2_quick_yoga,
    R.drawable.ab3_stretching to R.string.ab3_stretching,
    R.drawable.ab4_tabata to R.string.ab4_tabata,
    R.drawable.ab5_hiit to R.string.ab5_hiit,
    R.drawable.ab6_pre_natal_yoga to R.string.ab6_pre_natal_yoga
).map { DrawableStringPair(it.first, it.second) }

private val favoriteCollectionsData = listOf(
    R.drawable.fc1_short_mantras to R.string.fc1_short_mantras,
    R.drawable.fc2_nature_meditations to R.string.fc2_nature_meditations,
    R.drawable.fc3_stress_and_anxiety to R.string.fc3_stress_and_anxiety,
    R.drawable.fc4_self_massage to R.string.fc4_self_massage,
    R.drawable.fc5_overwhelmed to R.string.fc5_overwhelmed,
    R.drawable.fc6_nightly_wind_down to R.string.fc6_nightly_wind_down
).map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun SearchBarPreview() {
    MySootheTheme { SearchBar(Modifier.padding(8.dp)) }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AlignYourBodyElementPreview() {
    MySootheTheme {
        AlignYourBodyElement(
            text = R.string.ab1_inversions,
            drawable = R.drawable.ab1_inversions,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FavoriteCollectionCardPreview() {
    MySootheTheme {
        FavoriteCollectionCard(
            text = R.string.fc2_nature_meditations,
            drawable = R.drawable.fc2_nature_meditations,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FavoriteCollectionsGridPreview() {
    MySootheTheme { FavoriteCollectionsGrid() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AlignYourBodyRowPreview() {
    MySootheTheme { AlignYourBodyRow() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun HomeSectionPreview() {
    MySootheTheme {
        HomeSection(R.string.align_your_body) {
            AlignYourBodyRow()
        }
    }
}

// 높이 제한을 주고 대화형 모드로 실행하면 스크롤 동작 확인이 가능.
@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2, heightDp = 180)
@Composable
fun ScreenContentPreview() {
    MySootheTheme { HomeScreen() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun BottomNavigationPreview() {
    MySootheTheme { SootheBottomNavigation(Modifier.padding(top = 24.dp)) }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MySoothePreview() {
    MySootheApp()
}
