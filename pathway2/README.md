# Pathway 2

## Deep dive into Compose Layouts

### Layout Model

- **State → Composition → Layout → Drawing → UI**
    - Composition
        - Executes Composeable functions, which can emit UI, creating a UI tree
    - Layout - Measure, Place
        - UI tree is worked and each piece of UI is measured and placed on the screen in 2D space
    - Drawing
        - UI tree is worked and all elements rendered
- **Layout**
    - Measure children
    - Decide own size
    - Place children
    
    ![Untitled](https://user-images.githubusercontent.com/87517193/204749253-2ac2228b-b4fd-4ff8-ad53-6de33adfb5f5.png)

    
    - Once all elements are measured in size, the tree is walked again, and all placement instructions are executed in the placement phase.
- **Layout Composable**
- **Modifiers**
    - LayoutModifer
        - can have **single** Modifier
        - Resolved sizes come back up, and placement instructions are created (step 2)
        
        ![Untitled_1](https://user-images.githubusercontent.com/87517193/204749497-4c300757-d0b9-4bec-ba8b-eef6165ab9eb.png)
        
- **Advanced features**
    - IntrinsicSize (고유 너비)
        - element의 최대, 최소 값을 측정하여 반환
    - Parent Data
    - Alignment Lines
        - .alignBy
    - BoxWithConstraints
        - Sizing infomation isn’t available until the layout phase
        - Generally not available at composition time to use to make decisions about what to show
        - BoxWithConstraints defers composition of its content until the layout phase, when layout information is known.

## Lazy layouts

- rememberLazyListState()
    - Important state object that stores the scroll position
    - Contains useful information
    - `derivedStateOf`
        - Guarantees that the recomposition will only occur when the state property used in the calculation changes
    
    ![Untitled_2](https://user-images.githubusercontent.com/87517193/204749599-fd196547-9d8e-4597-ac83-55b62d80f2f9.png)
