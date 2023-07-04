# Testing compose Layout

## Semantics

Compose에서의 UI Test는 UI 계층과 상호작용하기 위해 `Semantics`를 사용합니다.

`Semantics`는 이름과 같이, UI의 일부분에 의미를 부여합니다.
여기에서 'UI의 일부분'은 단일 Composable부터 전체 화면에 이르기까지 다양한 것을 의미합니다.

`Semantics` 트리는 UI 계층과 함께 생성되며, UI 계층을 설명합니다.

<img src="../../resource/testing-semantic-tree.png" width="70%">

**그림1. UI 계층 구조와 그에 대한 Semantics 트리**

`Semantics` 프레임워크는 주로 접근성을 위해 사용되므로, Test는 `Semantics`가 UI 계층에 대해 노출하는 정보를 활용합니다.
여기에서 개발자는 노출할 정보와 얼마나 노출할지를 결정합니다.

<img src="../../resource/testing-button.png" width="50%">

**그림2. 아이콘과 텍스트를 포함하는 일반적인 버튼**

예를 들어, 이런 아이콘과 텍스트 요소로 구성된 버튼이 있을 때, 기본 `Semantics` 트리에는 "Like" 텍스트 레이블만 포함됩니다.

이는 `Text`과 같은 일부 Composable이 `Semantics` 트리에 일부 속성을 노출하기 때문입니다.
`Modifier`를 사용하여 `Semantics` 트리에 속성을 추가할 수 있습니다.

```kotlin
MyButton(
    modifier = Modifier.semantics { 
        contentDescription = "Add to Favorites"
    }
)
```

---

## Set Up

```groovy
// Test rules and transitive dependencies:
androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
// Needed for createAndroidComposeRule, but not createComposeRule:
debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")
```

이 모듈에는 `ComposeTestRule`과 `AndroidComposeTestRule`라는 Android 구현체가 포함되어 있습니다.
이 규칙을 통해 Compose 컨텐츠를 설정하거나 `Activity`에 접근할 수 있습니다.

```kotlin
// file: app/src/androidTest/java/com/package/MyComposeTest.kt

class MyComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    // 액티비티에 접근이 필요하면 createAndroidComposeRule<YourActivity>() 사용

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            MyAppTheme {
                MainScreen(uiState = fakeUiState, /*...*/)
            }
        }

        composeTestRule.onNodeWithText("Continue").performClick()

        composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
    }
}
```

---

## Testing API

요소와 상호작용하는 주요 방법은 3가지가 있습니다.

- Finders를 사용하면 하나 이상의 요소(또는 `Semantics` 트리의 노드)를 선택하여 그들에 대한 `assertion` 또는 `action`을 수행할 수 있습니다.
- Assertions은 요소가 존재하거나 특정 속성을 가지고 있는지 확인하는데 사용합니다.
- Action은 클릭이나 다른 제스쳐와 같은 시뮬레이트된 사용자 이벤트를 요소에 주입합니다.

이러한 API 중 일부는 하나 이상의 `Semantics` 트리 노드를 참조하기 위해 `SemanticsMatcher`를 받습니다.

### Finders

하나 또는 여러 노드를 선택하기 위해 `onNode` 또는 `onAllNodes`를 사용할 수 있지만, 
`onNodeWithText` 또는 `onAllNodesWithText`와 같은 가장 일반적인 검색을 위한 편의성 검색기를 사용할 수도 있습니다.

#### 단일 노드 선택

```kotlin
composeTestRule.onNode(<<SemanticsMatcher>>, useUnmergedTree = false) : SemanticsNodeInteraction

// Example
composeTestRule.onNode(hasText("Button")) // == onNodeWithText("Button")
```

#### 여러 노드 선택

```kotlin
composeTestRule.onAllNodes(<<SemanticsMatcher>>): SemanticsNodeInteractionCollection

// Example
composeTestRule.onAllNodes(hasText("Button")) // == onAllNodesWithText("Button")
```

#### 병합되지 않은 트리 사용

일부 노드는 자식의 `Semantics` 정보를 병합합니다.

아래 예처럼 2개의 `Text` 요소가 있는 버튼은 그들의 레이블을 병합합니다.

```kotlin
MyButton {
    Text("Hello")
    Text("World")
}
```

Test에서는 `printToLog()`를 사용하여 `Semantics` 트리를 출력할 수 있습니다.

```kotlin
composeTestRule.onRoot().printToLog("TAG")
```

위 코드는 다음과 같은 트리 형태를 출력합니다.

```text
Node #1 at (...)px
 |-Node #2 at (...)px
   Role = 'Button'
   Text = '[Hello, World]'
   Actions = [OnClick, GetTextLayoutResult]
   MergeDescendants = 'true'
```

병합되지 않은 트리의 노드를 일치시키리면 `useUnmergedTree`를 `true`로 설정할 수 있습니다.

```kotlin
composeTestRule.onRoot(useUnmergedTree = true).printToLog("TAG")
```

아래는 변경된 트리 형태입니다.

```text
Node #1 at (...)px
 |-Node #2 at (...)px
   OnClick = '...'
   MergeDescendants = 'true'
    |-Node #3 at (...)px
    | Text = '[Hello]'
    |-Node #5 at (83.0, 86.0, 191.0, 135.0)px
      Text = '[World]'
```

`useUnmergedTree` 파라미터는 모든 `Finders API`에서 사용할 수 있습니다.
예를 들어 아래 코드는 `onNodeWithText`에서 `useUnmergedTree` 파라미터를 사용합니다.

```kotlin
composeTestRule.onNodeWithText("World", useUnmergedTree = true).assertIsDisplayed()
```