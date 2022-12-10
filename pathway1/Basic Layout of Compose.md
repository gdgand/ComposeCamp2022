# Compose의 기본 레이아웃
### 목표
<img src="https://user-images.githubusercontent.com/101886039/203074388-58c39928-94b1-4637-886d-109b9e76d36f.png"  width="200" height="400"/>

<br>

### 검색창 - 수정자
```kotlin
@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    TextField(
        value = "",
        onValueChange = {},
        modifier = modifier
    )
}
```
- 수정자
  - 컴포저블 크기, 레이아웃, 동작, 모양 변경
  - 접근성 라벨과 같은 정보 추가
  - 사용자 입력 처리
  - 요소를 클릭 가능, 스크롤 가능, 드래그 가능 또는 확대/축소 가능하게 만드는 것과 같은 높은 수준의 상호작용 추가
  - 즉, 컴포저블의 modifier 매개변수를 설정해 디자인, 느낌, 동작 조정 가능
- 수정자 - [크기 섹션](https://developer.android.com/jetpack/compose/modifiers-list#Size)
  - 목표 화면의 검색창은 높이가 최소 56dp, 너비는 상위 컴포넌트에 맞게 설정해야 하기 때문에 아래 코드와 같이 설정
    ```kotlin
    TextField(
        value = "",
        onValueChange = {},
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
    ```
- 아이콘 추가
  - `TextField`의 매개변수엔 `leadingIcon`이 있음
    - 내부에 아이콘 설정 가능
- Text
  <br>
  <img src = "https://user-images.githubusercontent.com/101886039/203082927-6c575cd3-222c-4f2f-ac50-7ee182633d47.png" width = "300" height = "56">
    ```kotlin
    @Composable
    fun SearchBar(
        modifier: Modifier = Modifier
    ) {
        TextField(
            value = "",
            onValueChange = {},
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            placeholder = {
                Text(stringResource(id = R.string.placeholder_search))
            }
        )
    }
    ```
    ![image](https://user-images.githubusercontent.com/101886039/203083733-8a43bf25-242b-4a8e-a352-a6586dcd0ed8.png)

<br>

### 신체의 조화 - 정렬
- 수정자 - `clip`
  - `shpae`로 컴포저블 모양 조정 가능
  ![image](https://user-images.githubusercontent.com/101886039/203085095-d4993973-b91e-4a2f-9d91-385c32076e95.png)
- 이미지 - 크기
  - `Image`의 `contentScale` 매개변수
```kotlin
Image(
    painter = painterResource(id = R.drawable.ab1_inversions),
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = Modifier
        .size(88.dp)
        .clip(CircleShape)
)
```
![image](https://user-images.githubusercontent.com/101886039/203085730-b8471b3b-8cc5-43a3-be07-ef7a8f7ce6d7.png)

<br>

- 정렬 방법
  - `Column`
    - Start
    - CenterHorizontally
    - End
  - `Row`
    - Top
    - CenterVertically
    - Bottom
  - `Box`
    - TopStart
    - TopCenter
    - TopEnd
    - CenterStart
    - Center
    - CenterEnd
    - BottomStart
    - BottomCenter
    - BottomEnd
```kotlin
@Composable
fun AlignYourBodyElement(
   @DrawableRes drawable: Int,
   @StringRes text: Int,
   modifier: Modifier = Modifier
) {
   Column(
       modifier = modifier,
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       Image(
           painter = painterResource(drawable),
           contentDescription = null,
           contentScale = ContentScale.Crop,
           modifier = Modifier
               .size(88.dp)
               .clip(CircleShape)
       )
       Text(
           text = stringResource(text),
           style = MaterialTheme.typography.h3,
           modifier = Modifier.paddingFromBaseline(
               top = 24.dp, bottom = 8.dp
           )
       )
   }
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
```
![image](https://user-images.githubusercontent.com/101886039/203087491-5466cab4-e3d6-45c4-b386-61df26ee2266.png)

<br>

### 즐겨찾는 컬렉션 카드 - Material Surface
```kotlin
@Composable
fun FavoriteCollectionCard(
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(192.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.fc2_nature_meditations),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(56.dp)
            )
            Text(
                text = stringResource(id = R.string.fc2_nature_meditations)
            )
        }
    }
}
```
![image](https://user-images.githubusercontent.com/101886039/203089445-77fa08da-238d-4430-bad9-8baf1bff2459.png)

```kotlin
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
           verticalAlignment = Alignment.CenterVertically,
           modifier = Modifier.width(192.dp)
       ) {
           Image(
               painter = painterResource(drawable),
               contentDescription = null,
               contentScale = ContentScale.Crop,
               modifier = Modifier.size(56.dp)
           )
           Text(
               text = stringResource(text),
               style = MaterialTheme.typography.h3,
               modifier = Modifier.padding(horizontal = 16.dp)
           )
       }
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
```
![image](https://user-images.githubusercontent.com/101886039/203090020-47bf1859-dea4-4567-ad0b-39e8c1cb31c8.png)

<br>

### 본문 행 정렬 - 배치
![gif](https://developer.android.com/static/codelabs/jetpack-compose-layouts/img/25089e1f3e5eab4e.gif)
`LazyRow` 컴포저블 사용
```kotlin
@Composable
fun AlignYourBodyRow(
   modifier: Modifier = Modifier
) {
   LazyRow(
       modifier = modifier
   ) {
       items(alignYourBodyData) { item ->
           AlignYourBodyElement(item.drawable, item.text)
       }
   }
}
```

<br>

- 컨테이너의 기본 축 위에 하위 컴포저블 배치 방식
  - `Row`
    ![gif](https://developer.android.com/static/codelabs/jetpack-compose-layouts/img/c1e6c40e30136af2.gif)
  - `Column`
    ![gif](https://developer.android.com/static/codelabs/jetpack-compose-layouts/img/df69881d07b064d0.gif)
  - `Arrangement.spaceBy()` 메소드 사용해 각 하위 컴포저블 사이에 고정된 공간 추가 가능
    ```kotlin
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        ...
    ```
- padding 추가
  - `LazyRow`에 `padding` 수정자를 추가하면 양 끝의 item이 잘리게 된다
  - `contentPadding` 매개변수 사용
    ```kotlin 
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        ...
    ```

<br>

### 즐겨찾는 컬렉션 그리드 - Lazy 그리드
![gif](https://developer.android.com/static/codelabs/jetpack-compose-layouts/img/4378867d758590ae.gif)
- `LazyHorizontalGrid` 사용
```kotlin
@Composable
fun FavoriteCollectionsGrid(
    modifier: Modifier = Modifier
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.height(120.dp)
    ){
        items(favoriteCollectionsData){ item ->
            FavoriteCollectionCard(
                drawable = item.drawable,
                text = item.text,
                modifier = Modifier.height(56.dp)
            )
        }
    }
}
```

<br>

### 홈 섹션 - 슬롯 API
- 유연한 섹션 컨테이너 구현을 위해 슬롯 API 사용
- [슬롯 기반 레이아웃](https://developer.android.com/jetpack/compose/layouts/basics#slot-based-layouts)
```kotlin
@Composable
fun HomeSection(
   @StringRes title: Int,
   modifier: Modifier = Modifier,
   content: @Composable () -> Unit
) {
   Column(modifier) {
       Text(stringResource(title))
       content()
   }
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
```
- 컴포저블이 채울 수 있는 슬롯을 여러개 제공한다면 이름만 따로 지정해서 여러개 넣을 수 있음

<br>

### 홈 화면 - 스크롤
- `Spacer` 사용해서 공간 설정
  - `padding`을 사용해 설정하면 잘림 현상 일어날 수 있음
```kotlin
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Spacer(Modifier.height(16.dp))
        SearchBar(modifier = Modifier.padding(horizontal = 16.dp))
        HomeSection(title = R.string.align_your_body) {
            AlignYourBodyRow()
        }
        HomeSection(title = R.string.favorite_collections) {
            FavoriteCollectionsGrid()
        }
        Spacer(Modifier.height(16.dp))
    }
}
```
- 기기의 높이가 작을 경우를 대비해 스크롤 동작 넣기
  - `verticalScroll`, `horizontalScroll` 수정자 사용
  - 스크롤의 현재 상태 포함 및 외부에서 스크롤 상태 수정하는 데 사용되는 `ScrollState` 필요
  - 여기서는 스크롤 상태 수정할 필요가 없어서 그냥 `rememberScrollState` 사용
    ```kotlin
    @Composable
    fun HomeScreen(modifier: Modifier = Modifier) {
        Column(
            modifier
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
    ```

<br>

### 하단 탐색 - Material
- `BottomNavigation` 컴포저블 사용
```kotlin
@Composable
private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Spa,
                    contentDescription = null
                )
            },
            label = {
                    Text(stringResource(id = R.string.bottom_navigation_home))
            },
            selected = true,
            onClick = { /*TODO*/ }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(id = R.string.bottom_navigation_profile))
            },
            selected = false,
            onClick = { /*TODO*/ }
        )
    }
}
```
![image](https://user-images.githubusercontent.com/101886039/203103320-ff4c47c3-1274-4f62-b70f-c89b3180df40.png)

<br>

### MySoothe 앱 - Scaffold
- Material의 `Scaffold` 컴포저블을 사용
- `Scaffold`
  - 구성 가능한 최상위 수준 컴포저블 제공
  - 다양한 Material 개념의 슬롯 포함
  - 그 중 하나가 하단 메뉴
```kotlin
@Composable
fun MySootheApp() {
    MySootheTheme {
        Scaffold(
            bottomBar = { SootheBottomNavigation() }
        ) { padding ->
            HomeScreen(Modifier.padding(padding))
        }
    }
}
```
![image](https://user-images.githubusercontent.com/101886039/203104221-00491377-3710-4b67-ae4a-0646bd86bd17.png)
