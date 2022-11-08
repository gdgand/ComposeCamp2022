## Jetpack Compose starter tutorial

- Kotlin API로 Android에서의 UI 개발을 간소화하고 가속화
- 선언형 함수를 사용하여 간단한 UI 구성요소를 빌드
- XML 레이아웃, Layout Editor를 사용하지 않음

### 1. **Composable functions**

1. Add **@Composable** annotation
    1. Defind app’s UI programmatically by describing how it should look and providing data dependencies
    2. rather than focusing on the process of the UI's construction (initializing an element, attaching it to a parent, etc.).
2. Add a text element
    1. `setContent` block defines the Activity’s layour where composable functions are called.
    2. Composable functions can only be called from other composable functions.
3. Define a composable function
4. Preview your function in Android Studio
    1. **@Preview** annotation before **@Composable**
    2. click the refresh button at the top of the preview window

### 2. Layouts

1. Add multiple texts
2. Using a Column

    ```kotlin
    @Composable
    fun MessageCard(msg: Message) {
        Column {
            Text(text = msg.author)
            Text(text = msg.body)
        }
    }
    ```

3. Add an image element
4. Configure your layout - modifier

### 3. Material Design

1. Use Material Design
2. Color
3. Typography
4. Shape
5. Enable Dark Theme
    1. Color choices for the light and dark themes are defined in the IDE-generated `Theme.kt` file.

### 4. Lists and Animations

1. Create a list of messages
    1. LazyColumn, LazyRow
    2. These composables render only the elements that are visible on screen, so they are designed to be very efficient for long lists.
2. Animate messages while expanding
    1. `remember` and `mutableStateOf`
    2. Store local state in memory by using `remember`
    3. Track changes to the value passed to `mutableStateOf`
    4. Recomposition
        - Composables and their children using this state will get redrawn automatically when the value is updated
    5. `animateColorAsState`, `animateContentSize`

    ```kotlin
    // We keep track if the message is expanded or not in this variable
    var isExpanded by remember { mutableStateOf(false) }
    
    // surfaceColor will be updated gradually from one color to the other
    val surfaceColor by animateColorAsState(
        if (isExpanded) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surface,
    )
    
    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 1.dp,
        // surfaceColor color will be changing gradually from primary to surface
        color = surfaceColor,
        // animateContentSize will change the Surface size gradually
        modifier = Modifier.animateContentSize().padding(1.dp)
    ) {
    		Text()
    }
    ```