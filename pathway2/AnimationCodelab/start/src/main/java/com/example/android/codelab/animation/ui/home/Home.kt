/*
 * Copyright 2021 The Android Open Source Project
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

package com.example.android.codelab.animation.ui.home

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Surface
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android.codelab.animation.R
import com.example.android.codelab.animation.ui.Amber600
import com.example.android.codelab.animation.ui.AnimationCodelabTheme
import com.example.android.codelab.animation.ui.Green300
import com.example.android.codelab.animation.ui.Green800
import com.example.android.codelab.animation.ui.Purple100
import com.example.android.codelab.animation.ui.Purple700
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private enum class TabPage {
    Home, Work
}

/**
 * Shows the entire screen.
 */
@Composable
fun Home() {
    // String resources.
    val allTasks = stringArrayResource(R.array.tasks)
    val allTopics = stringArrayResource(R.array.topics).toList()

    // The currently selected tab.
    var tabPage by remember { mutableStateOf(TabPage.Home) }

    // True if the whether data is currently loading.
    var weatherLoading by remember { mutableStateOf(false) }

    // Holds all the tasks currently shown on the task list.
    val tasks = remember { mutableStateListOf(*allTasks) }

    // Holds the topic that is currently expanded to show its body.
    var expandedTopic by remember { mutableStateOf<String?>(null) }

    // True if the message about the edit feature is shown.
    var editMessageShown by remember { mutableStateOf(false) }

    // Simulates loading weather data. This takes 3 seconds.
    suspend fun loadWeather() {
        if (!weatherLoading) {
            weatherLoading = true
            delay(3000L)
            weatherLoading = false
        }
    }

    // Shows the message about edit feature.
    suspend fun showEditMessage() {
        if (!editMessageShown) {
            editMessageShown = true
            delay(3000L)
            editMessageShown = false
        }
    }

    // Load the weather at the initial composition.
    LaunchedEffect(Unit) {
        loadWeather()
    }

    val lazyListState = rememberLazyListState()

    // The background color. The value is changed by the current tab.
    // TODO 1: Animate this color change.
    //tabpage = state객체로 지원되는 Int
    //이러한 간단한 값 변경을 애니메이션으로 처리하려면 animate*AsState API를 사용
    //val backgroundColor = if (tabPage == TabPage.Home) Purple100 else Green300
    val backgroundColor by animateColorAsState(if (tabPage == TabPage.Home) Purple100 else Green300)

    // The coroutine scope for event handlers calling suspend functions.
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            HomeTabBar(
                backgroundColor = backgroundColor,
                tabPage = tabPage,
                onTabSelected = { tabPage = it }
            )
        },
        backgroundColor = backgroundColor,
        floatingActionButton = {
            HomeFloatingActionButton(
                extended = lazyListState.isScrollingUp(),
                onClick = {
                    coroutineScope.launch {
                        showEditMessage()
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
            state = lazyListState,
            modifier = Modifier.padding(padding)
        ) {
            // Weather
            item { Header(title = stringResource(R.string.weather)) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 2.dp
                ) {
                    if (weatherLoading) {
                        LoadingRow()
                    } else {
                        WeatherRow(onRefresh = {
                            coroutineScope.launch {
                                loadWeather()
                            }
                        })
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }

            // Topics
            item { Header(title = stringResource(R.string.topics)) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            items(allTopics) { topic ->
                TopicRow(
                    topic = topic,
                    expanded = expandedTopic == topic,
                    onClick = {
                        expandedTopic = if (expandedTopic == topic) null else topic
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }

            // Tasks
            item { Header(title = stringResource(R.string.tasks)) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            if (tasks.isEmpty()) {
                item {
                    TextButton(onClick = { tasks.clear(); tasks.addAll(allTasks) }) {
                        Text(stringResource(R.string.add_tasks))
                    }
                }
            }
            items(count = tasks.size) { i ->
                val task = tasks.getOrNull(i)
                if (task != null) {
                    key(task) {
                        TaskRow(
                            task = task,
                            onRemove = { tasks.remove(task) }
                        )
                    }
                }
            }
        }
        EditMessage(editMessageShown)
    }
}

/**
 * Shows the floating action button.
 *
 * @param extended Whether the tab should be shown in its expanded state.
 */
// AnimatedVisibility is currently an experimental API in Compose Animation.
@Composable
private fun HomeFloatingActionButton(
    extended: Boolean,
    onClick: () -> Unit
) {
    // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
    // how it should animate.
    FloatingActionButton(onClick = onClick) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )
            // Toggle the visibility of the content with animation.
            // TODO 2-1: Animate this visibility change.
            //이 가시성 변경에 애니메이션 효과를 적용하는 것
            //AnimatedVisibility = 지정된 Boolean 값이 변경될 때마다 애니메이션을 실행
            //기본적으로 페이드 인 및 확장하여 표시하고 페이드 아웃 및 축소를 통해 요소를 숨김

            AnimatedVisibility (extended) {
                Text(
                    text = stringResource(R.string.edit),
                    modifier = Modifier
                        .padding(start = 8.dp, top = 3.dp)
                )
            }
        }
    }
}

/**
 * Shows a message that the edit feature is not available.
 */
@Composable
private fun EditMessage(shown: Boolean) {
    // TODO 2-2: The message should slide down from the top on appearance and slide up on
    //           disappearance.
    AnimatedVisibility(
        visible = shown,
        //enter => enterTransition의 인스턴스
        //in, out 모두 기본동작이 항목 높이의 절반을 사용하기에 이상해보임
        //따라서 initialOffsetY 매개변수 설정을 통해
        // 항목의 전체 높이를 사용하도록
        // 기본 동작을 조정하여 올바르게 애니메이션이 적용되도록 가능능
        enter = slideInVertically(
            // Enters by sliding down from offset -fullHeight to 0.
            initialOffsetY = { fullHeight ->  -fullHeight },
            //animationSpec은 매개변수를 사용하여 추가 맞춤설정 가능,
            // 여러 Animation API의 공통 매개변수로 사용됨
            //시간이 지남에 따라 애니메이션의 값을 변경할지 지정가능
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            // Exits by sliding up from offset 0 to -fullHeight.
            targetOffsetY = { fullHeight ->  -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.secondary,
            elevation = 4.dp
        ) {
            Text(
                text = stringResource(R.string.edit_message),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

/**
 * Returns whether the lazy list is currently scrolling up.
 */
@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

/**
 * Shows the header label.
 *
 * @param title The title to be shown.
 */
@Composable
private fun Header(
    title: String
) {
    Text(
        text = title,
        modifier = Modifier.semantics { heading() },
        style = MaterialTheme.typography.h5
    )
}

/**
 * Shows a row for one topic.
 *
 * @param topic The topic title.
 * @param expanded Whether the row should be shown expanded with the topic body.
 * @param onClick Called when the row is clicked.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TopicRow(topic: String, expanded: Boolean, onClick: () -> Unit) {
    TopicRowSpacer(visible = expanded)
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 2.dp,
        onClick = onClick
    ) {
        // TODO 3: Animate the size change of the content.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                //수정자 추가로 크기 변경에 애니메이션 적용
                .animateContentSize()

            //animateContentSize도
            // 맞춤 animationSpec으로 맞춤설정할 수 있습니다.
            // 스프링에서 트윈 등으로 애니메이션 유형을 변경하는 옵션을 제공할 수 있습니다.
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = topic,
                    style = MaterialTheme.typography.body1
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.lorem_ipsum),
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
    TopicRowSpacer(visible = expanded)
}

/**
 * Shows a separator for topics.
 */
@Composable
fun TopicRowSpacer(visible: Boolean) {
    AnimatedVisibility(visible = visible) {
        Spacer(modifier = Modifier.height(8.dp))
    }
}

/**
 * Shows the bar that holds 2 tabs.
 *
 * @param backgroundColor The background color for the bar.
 * @param tabPage The [TabPage] that is currently selected.
 * @param onTabSelected Called when the tab is switched.
 */
@Composable
private fun HomeTabBar(
    backgroundColor: Color,
    tabPage: TabPage,
    onTabSelected: (tabPage: TabPage) -> Unit
) {
    TabRow(
        selectedTabIndex = tabPage.ordinal,
        backgroundColor = backgroundColor,
        indicator = { tabPositions ->
            HomeTabIndicator(tabPositions, tabPage)
        }
    ) {
        HomeTab(
            icon = Icons.Default.Home,
            title = stringResource(R.string.home),
            onClick = { onTabSelected(TabPage.Home) }
        )
        HomeTab(
            icon = Icons.Default.AccountBox,
            title = stringResource(R.string.work),
            onClick = { onTabSelected(TabPage.Work) }
        )
    }
}

/**
 * Shows an indicator for the tab.
 *
 * @param tabPositions The list of [TabPosition]s from a [TabRow].
 * @param tabPage The [TabPage] that is currently selected.
 */
@Composable
private fun HomeTabIndicator(
    tabPositions: List<TabPosition>,
    tabPage: TabPage
) {
    // TODO 4: Animate these value changes.


    //이러한 여러 값에 동시에 애니메이션을 적용하려면 Transition을 사용
    //탭행에 있는 표시기  왼쪽 가장자리의 가로 위치
    //val indicatorLeft = tabPositions[tabPage.ordinal].left
    //오른쪽
    //val indicatorRight = tabPositions[tabPage.ordinal].right
    //val color = if (tabPage == TabPage.Home) Purple700 else Green800

    //tabPage -> 선택된 탭의 색인을 targetState매개변수로 전달
    //animate*함수는 State객체를 반환함으로
    //여기서 다시 by선언을 사용하여 로컬위임속성으로 만들 수 있음
    val transition = updateTransition(
        tabPage,
        label = "Tab indicator"
    )
    /*val indicatorLeft by transition.animateDp(label = "Indicator left") { page ->
        tabPositions[page.ordinal].left
    }
    val indicatorRight by transition.animateDp(label = "Indicator right") { page ->
        tabPositions[page.ordinal].right
    }
    val color by transition.animateColor(label = "Border color") { page ->
        if (page == TabPage.Home) Purple700 else Green800
    }*/

    //isTransitioningTo -> 상태변경방향을 결정 가능
    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            if (TabPage.Home isTransitioningTo TabPage.Work) {
                // Indicator moves to the right.
                // The left edge moves slower than the right edge.
                spring(stiffness = Spring.StiffnessVeryLow)
            } else {
                // Indicator moves to the left.
                // The left edge moves faster than the right edge.
                spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = "Indicator left"
    ) { page ->
        tabPositions[page.ordinal].left
    }
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            if (TabPage.Home isTransitioningTo TabPage.Work) {
                // Indicator moves to the right
                // The right edge moves faster than the left edge.
                spring(stiffness = Spring.StiffnessMedium)
            } else {
                // Indicator moves to the left.
                // The right edge moves slower than the left edge.
                spring(stiffness = Spring.StiffnessVeryLow)
            }
        },
        label = "Indicator right"
    ) { page ->
        tabPositions[page.ordinal].right
    }
    val color by transition.animateColor(
        label = "Border color"
    ) { page ->
        if (page == TabPage.Home) Purple700 else Green800
    }


    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, color),
                RoundedCornerShape(4.dp)
            )
    )
}

/**
 * Shows a tab.
 *
 * @param icon The icon to be shown on this tab.
 * @param title The title to be shown on this tab.
 * @param onClick Called when this tab is clicked.
 * @param modifier The [Modifier].
 */
@Composable
private fun HomeTab(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title)
    }
}

/**
 * Shows the weather.
 *
 * @param onRefresh Called when the refresh icon button is clicked.
 */
@Composable
private fun WeatherRow(
    onRefresh: () -> Unit
) {
    Row(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Amber600)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = stringResource(R.string.temperature), fontSize = 24.sp)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onRefresh) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(R.string.refresh)
            )
        }
    }
}

/**
 * Shows the loading state of the weather.
 */
@Composable
private fun LoadingRow() {
    // TODO 5: Animate this value between 0f and 1f, then back to 0f repeatedly.
    //val alpha = 1f
    //Transition->상태변경에 따라 애니메이션 적용
    //infiniteTransition->무기한으로 애니메이션 적용

    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        //animationSpec은 지속시간기반animationSpec을 래핑하여 반복가능
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                //keyframes애니메이션은 다른 밀리초 단위에서
                //진행중인 값을 변경할 수 있는 또 다른 유형의 animationSpec임
                durationMillis = 1000
                0.7f at 500
                //두개이상의 키프레임
                0.9f at 800
            },
            //기본 repeatMode는 RepeatMode.Restart임
            //0~1진행 후 1~0으로 다시 진행
            repeatMode = RepeatMode.Reverse
        )
    )
    Row(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = alpha))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(Color.LightGray.copy(alpha = alpha))
        )
    }
}

/**
 * Shows a row for one task.
 *
 * @param task The task description.
 * @param onRemove Called when the task is swiped away and removed.
 */
@Composable
private fun TaskRow(task: String, onRemove: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .swipeToDismiss(onRemove),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = task,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

/**
 * The modified element can be horizontally swiped away.
 *
 * @param onDismissed Called when the element is swiped to the edge of the screen.
 */
private fun Modifier.swipeToDismiss(
    //터치로 요소를 스와이프 할 수 있도록 하는 수정자
    // 요소가 화면 가장자리로 플링되면 요소가 삭제될 수 있도록 onDismissed 콜백을 호출
    onDismissed: () -> Unit
): Modifier = composed {
    // TODO 6-1: Create an Animatable instance for the offset of the swiped element.
    val offsetX = remember { Animatable(0f) }

    pointerInput(Unit) {
        // Used to calculate a settling position of a fling animation.
        val decay = splineBasedDecay<Float>(this)
        // Wrap in a coroutine scope to use suspend functions for touch events and animation.
        coroutineScope {
            while (true) {
                // Wait for a touch down event.
                val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                offsetX.stop()
                // TODO 6-2: Touch detected; the animation should be stopped.
                // Prepare for drag events and record velocity of a fling.
                //사용자가 왼쪽에서 오른쪽으로 이동하는 속도를 계산하는 데 사용
                val velocityTracker = VelocityTracker()
                // Wait for drag events.
                //사용자 입력 이벤트를 기다렸다가 이에 응답할 수 있는 정지 함수
                awaitPointerEventScope {
                    // 터치 이벤트의 위치를 애니메이션 값에 동기화해야 함
                    horizontalDrag(pointerId) { change ->
                        // TODO 6-3: Apply the drag change to the Animatable offset.
                        val horizontalDragOffset = offsetX.value + change.positionChange().x
                        //awaitPointerEvents의 경우에만 suspend
                        launch {
                            // Instantly set the Animable to the dragOffset to ensure its moving
                            // as the user's finger moves
                            //포인터이벤트가아님
                            offsetX.snapTo(horizontalDragOffset)
                        }
                        // Record the velocity of the drag.
                        velocityTracker.addPosition(change.uptimeMillis, change.position)
                        // Consume the gesture event, not passed to external
                        if (change.positionChange() != Offset.Zero) change.consume()
                    }
                }
                // Dragging finished. Calculate the velocity of the fling.
                val velocity = velocityTracker.calculateVelocity().x
                // TODO 6-4: Calculate the eventual position where the fling should settle
                //           based on the current offset value and velocity
                // Add this line to calculate where it would end up with
                // the current velocity and position
                //요소를 원래 위치로 다시 슬라이드해야 할지,
                // 아니면 슬라이드하여 없애고 콜백을 호출해야 할지
                // 결정하려면 플링이 정착한 최종 위치를 계산해야 합니다.
                // 앞에서 만든 decay 객체를 사용하여 targetOffsetX를 계산
                val targetOffsetX = decay.calculateTargetValue(offsetX.value, velocity)

                // TODO 6-5: Set the upper and lower bounds so that the animation stops when it
                //           reaches the edge.

                //애니메이션을 시작하려고 합니다.
                // 하지만 그 전에 상한값과 하한값을 Animatable에 설정하여
                // 경계(-size.width 및 size.width임. offsetX가 이 두 값을 지나 확장할 수 없도록)에 도달하는 즉시 중지되도록 합니다.
                // pointerInput 수정자를 사용하면 size 속성으로 요소의 크기에 액세스할 수 있으므로
                // 이를 사용하여 경계가져옴
                offsetX.updateBounds(
                    lowerBound = -size.width.toFloat(),
                    upperBound = size.width.toFloat()
                )

                launch {
                    // TODO 6-6: Slide back the element if the settling position does not go beyond
                    //           the size of the element. Remove the element if it does.
                    // 먼저 앞서 계산한 플링의 정착 위치와 요소의 크기를 비교합니다.
                    // 정착 위치가 크기보다 작다면 플링의 속도가 충분하지 않은 것입니다.
                    // animateTo를 사용하여 값을 다시 0f로 애니메이션 처리할 수 있습니다.
                    // 크기보다 작지 않은 경우 animateDecay를 사용하여 플링 애니메이션을 시작합니다.
                    // 애니메이션이 완료되면(대부분 앞에서 설정한 경계에 따라) 콜백을 호출

                    if (targetOffsetX.absoluteValue <= size.width) {
                        // Not enough velocity; Slide back.
                        offsetX.animateTo(targetValue = 0f, initialVelocity = velocity)
                    } else {
                        // Enough velocity to slide away the element to the edge.
                        offsetX.animateDecay(velocity, decay)
                        // The element was swiped away.
                        onDismissed()
                    }
                }
            }
        }
    }
        .offset {
            // TODO 6-7: Use the animating offset value here.
            //IntOffset(0, 0)
            // 모든 애니메이션과 동작이 설정됨
            //요소에 오프셋을 적용
            IntOffset(offsetX.value.roundToInt(), 0)
        }
}

@Preview
@Composable
private fun PreviewHomeTabBar() {
    HomeTabBar(
        backgroundColor = Purple100,
        tabPage = TabPage.Home,
        onTabSelected = {}
    )
}

@Preview
@Composable
private fun PreviewHome() {
    AnimationCodelabTheme {
        Home()
    }
}
