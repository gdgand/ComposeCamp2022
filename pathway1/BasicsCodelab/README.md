# Jetpack Compose Basic Codelab
Week1 Jetpack Compose Basic 코드랩은 새 프로젝트를 만드는 것으로 시작합니다.

## 작업 전 필수 확인
Basics에 올려둔 프로젝트는 new project 입니다. 바로 이 프로젝트 위에서 코드랩을 시작하시면 됩니다. 

## 작업 후
Git 명령어 또는 [SourceTree](https://www.sourcetreeapp.com/), [GitKraken](https://www.gitkraken.com/) 등을 이용해 작업 결과를 push 해주세요.

## Write your first Compose app

- Creating columns and rows
    - `alignEnd` 수정자가 없다
        - Composable에 weight를 줘서 이동시킨다.
        - The `weight` modifier makes the element fill all available space, making it flexible, effectively pushing away the other elements that don’t have a weight, which are called inflexible.
        - It also makes the `fillMaxWidth` modifier redundant.
- State in Compose
    - recomposition
- State hoisting
    - `by` 키워드로 `.value`를 입력할 필요가 없음
    - In Compose you don’t hide UI elements.
    - Instead, you simply don't add them to the composition, so they're not added to the UI tree that Compose generates.
- Creating a performant lazy list
    - `LazyColumn` and `LazyRow` are equivalent to `RecyclerView` in Android Views.
    - `LazyColumn` doesn't recycle its children like `RecyclerView`
    - It emits new Composables as you scroll through it and is still performant, as emitting Composables is relatively cheap compared to instantiating Android Views.
- Persisting State
    - The `remember` function works **only as long as the composable is kept in the Composition**.
        - When you rotate, the whole activity is restarted so all state is lost.
    - You can use `rememberSaveable` instead `remember` to solve this case.
- Animating your list
    - `animateDpAsState`
    - `fun <T : Comparable> T.coerceAtLeast(minimumValue: T): T`
        - 호출된 객체가 특정 개체보다 큰지 아닌지를 확인해준다. 호출된 객체가 더 크면 객체 자체를 반환하고 그렇지 않으면 최소 객체를 반환한다.
        - padding이 음수가 되면 앱이 다운되므로 설정
- Styleing and Theming your app
    - Modify a predefined style by using the `copy` function.
