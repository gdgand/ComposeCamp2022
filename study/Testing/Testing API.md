| 카테고리         | 함수                                                  | 설명                                      |
|--------------|-----------------------------------------------------|-----------------------------------------|
| **Assert**   |
|              | `assertExists()`                                    | 해당 노드가 존재함을 확인합니다.                      |
|              | `assertDoesNotExist()`                              | 해당 노드가 존재하지 않음을 확인합니다.                  |
|              | `assertIsDisplayed()`                               | 해당 노드가 화면에 표시되고 있음을 확인합니다.              |
|              | `assertIsNotDisplayed()`                            | 해당 노드가 화면에 표시되지 않음을 확인합니다.              |
|              | `assertIsEnabled()`                                 | 해당 노드가 사용 가능한 상태임을 확인합니다.               |
|              | `assertIsNotEnabled()`                              | 해당 노드가 사용 불가능한 상태임을 확인합니다.              |
|              | `assertIsFocused()`                                 | 해당 노드가 현재 포커스를 가지고 있음을 확인합니다.           |
|              | `assertIsNotFocused()`                              | 해당 노드가 포커스를 가지고 있지 않음을 확인합니다.           |
|              | `assertHasClickAction()`                            | 해당 노드에 클릭 액션이 존재함을 확인합니다.               |
|              | `assertHasNoClickAction()`                          | 해당 노드에 클릭 액션이 존재하지 않음을 확인합니다.           |
|              | `assert(hasText("example"))`                        | 해당 노드에 "example" 텍스트가 있는지 확인합니다.        |
| **Finders**  |
|              | `onNodeWithText(text: String)`                      | 주어진 텍스트를 포함하는 노드를 찾습니다.                 |
|              | `onNodeWithTag(tag: String)`                        | 주어진 태그를 가진 노드를 찾습니다.                    |
|              | `onNodeWithContentDescription(description: String)` | 주어진 콘텐츠 설명을 가진 노드를 찾습니다.                |
|              | `onRoot()`                                          | 루트 노드를 찾습니다.                            |
| **Matchers** |
|              | `hasTestTag(tag: String)`                           | 노드가 특정 테스트 태그를 가지고 있는지 확인합니다.           |
|              | `hasText(text: String)`                             | 노드가 특정 텍스트를 가지고 있는지 확인합니다.              |
|              | `isFocused()`                                       | 노드가 포커스를 가지고 있는지 확인합니다.                 |
|              | `isNotFocused()`                                    | 노드가 포커스를 가지고 있지 않은지 확인합니다.              |
|              | `isEnabled()`                                       | 노드가 활성 상태인지 확인합니다.                      |
|              | `isDisabled()`                                      | 노드가 비활성 상태인지 확인합니다.                     |
|              | `hasContentDescription(label: String)`              | 노드가 주어진 콘텐츠 설명을 가지고 있는지 확인합니다.          |
|              | `hasParent(matcher: SemanticsMatcher)`              | 노드의 부모가 주어진 매처 조건을 충족하는지 확인합니다.         |
|              | `hasAnyChild(matcher: SemanticsMatcher)`            | 노드의 어느 하나의 자식이 주어진 매처 조건을 충족하는지 확인합니다.  |
|              | `hasClickAction()`                                  | 노드가 클릭 가능한지 확인합니다.                      |
|              | `isDialog()`                                        | 노드가 대화 상자인지 확인합니다.                      |
|              | `isOff()`                                           | 노드가 꺼진 상태인지 확인합니다.                      |
|              | `isOn()`                                            | 노드가 켜진 상태인지 확인합니다.                      |
|              | `isRoot()`                                          | 노드가 루트 노드인지 확인합니다.                      |
| **Actions**  |
|              | `performClick()`                                    | 노드를 클릭하는 액션을 수행합니다.                     |
|              | `performDoubleClick()`                              | 노드를 더블 클릭하는 액션을 수행합니다.                  |
|              | `performScrollTo()`                                 | 노드를 스크롤하여 보이게 하는 액션을 수행합니다.             |
|              | `performTextInput(text: String)`                    | 노드에 텍스트 입력을 하는 액션을 수행합니다.               |
|              | `performLongClick()`                                | 노드를 길게 클릭하는 액션을 수행합니다.                  |
|              | `performTextClearance()`                            | 노드의 텍스트를 지우는 액션을 수행합니다.                 |
|              | `performTextInputSelection(textRange: TextRange)`   | 노드의 텍스트에서 특정 범위를 선택하는 액션을 수행합니다.        |
|              | `performScrollToIndex(index: Int)`                  | 노드를 스크롤하여 특정 인덱스의 항목이 보이게 하는 액션을 수행합니다. |
|              | `performSwipeUp()`                                  | 노드를 위로 스와이프하는 액션을 수행합니다.                |
|              | `performSwipeDown()`                                | 노드를 아래로 스와이프하는 액션을 수행합니다.               |
|              | `performSwipeLeft()`                                | 노드를 왼쪽으로 스와이프하는 액션을 수행합니다.              |
|              | `performSwipeRight()`                               | 노드를 오른쪽으로 스와이프하는 액션을 수행합니다.             |
