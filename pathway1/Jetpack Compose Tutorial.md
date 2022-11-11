# Jetpack Compose Tutorial
[Compose Camp](https://composecamp.kr/developer)
<br>
[CodeLab](https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-1)
<br>

### Before Starting...
__Jetpack Compose 란?__
- Native Android UI를 빌드하기 위한 최신 도구 키트
- 더 적은 수의 코드, 강력한 도구, 직관적인 Kotlin API로 Android에서의 UI 개발을 간소화 및 가속화
  
### Composable Function(구성 가능한 함수)
- 텍스트 요소 추가
    - Empty Compose Activity 프로젝트 생성
    ```kotlin
    override fun onCreate(savedInstanceState: Bundle?){
      super.onCreate(savedInstanceState)
      setContent {
        Text("Hello world!")
      }
    }
    ```
- Composable Function 정의
  - `@Composable` annotation 사용
    ```kotlin
    @Composable
    fun MessageCard(name: String){
      Text(text = "Hello $name!")
    }

    // onCreate
    setContext {
      MessageCard("Android")
    }
    ```
- 함수 미리보기
  - `@Preview` annotation 사용
  - 매개변수를 사용하지 않는 함수에서 사용
    - 미리볼 수 없기 때문
  - 함수를 실행하지 않더라도 프로젝트 빌드 후 미리보기 창에서 확인 가능
  
<br>

### Layouts
- 여러 텍스트 추가
  ```kotlin
  data class Message(val author: String, val body: String)

  @Composable
  fun MessageCard(msg: Message) {
    Text(text = msg.author)
    Text(text = msg.body)
  }
  
  // onCreate
  MessageCard(Message("Android","Jetpack Compose"))
  ```
  - 텍스트 정렬 방법에 대해 정의하지 않았기 때문에 겹쳐서 출력됨
  -  `Column`, `Row`, `Box` 함수 사용
- 이미지 요소 추가
  - [Resource Manager](https://developer.android.com/studio/write/resource-manager#import) 사용
  ```kotlin
  @Composable
  fun MessageCard(msg: Message) {
    Row {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
        )
    
       Column {
            Text(text = msg.author)
            Text(text = msg.body)
        }
    }
  }
  ```
  ![결과화면](https://user-images.githubusercontent.com/101886039/201403601-fd9c35ac-97a0-4a5a-9bc5-6351761aa50b.png)
  
- 레이아웃 구성
  - `Modifier` 사용
    - Composable의 크기, layout, 모양 변경
    - element 클릭 가능하게 만들기
    - 복잡한 Composable 만들 수 있음
  ```kotlin
  @Composable
  fun MessageCard(msg: Message) {
    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = msg.author)
            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = msg.body)
        }
    }
  }
  ```
  ![image](https://user-images.githubusercontent.com/101886039/201403638-daca4051-c25c-42eb-8133-72f0d35fd437.png)

<br>

### Material Design
- Composable이 앱 테마에 정의된 스타일을 상속해 앱 전체에서의 일관성 보장
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard(Message("Android","Jetpack Compose"))

            ComposeTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {
                    MessageCard(msg = Message("Android","Jetpack Compose"))
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
   Row(modifier = Modifier.padding(all = 8.dp)) {
       Image(
           painter = painterResource(R.drawable.profile_picture),
           contentDescription = null,
           modifier = Modifier
               .size(40.dp)
               .clip(CircleShape)
               .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
       )
       Spacer(modifier = Modifier.width(8.dp))

       Column {
           Text(
               text = msg.author,
               color = MaterialTheme.colorScheme.secondary,
               style = MaterialTheme.typography.titleSmall
           )

           Spacer(modifier = Modifier.height(4.dp))

           Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp) {
               Text(
                   text = msg.body,
                   modifier = Modifier.padding(all = 4.dp),
                   style = MaterialTheme.typography.bodyMedium
               )
           }
       }
   }
}

@Preview
@Composable
fun PreviewMessageCard() {
    ComposeTutorialTheme {
        Surface {
            MessageCard(
                msg = Message("Lexi", "Take a look at Jetpack Compose, it's great!")
            )
        }
    }
}
```

<br>

### Lists and animations
- 목록 만들기
  - `LazyColumn`, `LazyRow` 사용
  - 하위 요소는 `items`
  ```kotlin
  @Composable
  fun Conversation(messages: List<Message>) {
      LazyColumn {
          items(messages) { message ->
              MessageCard(message)
          }
      }
  }
  ```
- [애니메이션](https://developer.android.com/jetpack/compose/tutorial?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fjetpack-compose-for-android-developers-1%23article-https%3A%2F%2Fdeveloper.android.com%2Fjetpack%2Fcompose%2Ftutorial)
