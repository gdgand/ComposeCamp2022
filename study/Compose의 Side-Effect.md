# Compose: Side-Effect

Side-Effect는 Compose에서 비동기적으로 실행될 때 일어나는 부작용을 다루는 개념입니다.   
이는 프로그램의 실행 결과에 영향을 주지만, UI 구성에는 직접적인 영향을 미치지 않는 작업을 포함합니다.   
일반적으로 Coroutine을 사용하여 비동기적으로 실행되며 데이터를 가져오거나 저장하는 등의 I/O 작업을 처리합니다.

## Side-Effect의 구현

Composable 함수 내부에서 호출될 수 있으며, `LaunchedEffect` 함수를 사용하여 비동기로 실행됩니다.

```kotlin
@Composable
fun SideEffectExample() {
   val data = remember { mutableStateOf("No data") }

   LaunchedEffect(Unit) {
       // 비동기적으로 데이터를 가져옴
       val newData = fetchData()
       data.value = newData
   }

   // UI 업데이트
   Text(data.value)
}
```

Composable 함수 내에서 Side-Effect를 사용하여 푸시 알림을 처리하는 것도 가능합니다.     
예를 들어, FCM을 사용하여 푸시 알림을 받고 처리하는 경우 아래와 같이 구현할 수 있습니다.

```kotlin
@Composable
fun PushNotificationExample() {
   val data = remember { mutableStateOf("No data") }

   LaunchedEffect(Unit) {
       // Firebase Cloud Messaging으로부터 푸시 알림을 받아서 처리
       FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
           if (task.isSuccessful) {
               val token = task.result
               // 받은 푸시 알림을 처리
               processPushNotification(token)
               // 데이터를 업데이트
               data.value = "New data from push notification"
           }
       }
   }

   // UI 업데이트
   Text(data.value)
}
```

## Side-Effect 사용 시 주의사항

1. Side-Effect는 Composable 함수 내부에서 호출되어야 합니다.
2. Composable 함수가 실행을 완료하면 자동으로 취소되기 때문에, Side-Effect 내부에서 무한 루프나 블로킹 작업을 수행하면 안됩니다. 이러한 오류는 앱의 성능을 저하시키거나 앱이 충돌하는 원인이 될 수 있습니다.
3. 코드의 복잡도가 올라가므로 필요한 경우에만 Side-Effect를 사용해야 합니다.

예를 들어, 앱의 초기 데이터를 가져오는 작업은 앱이 처음 실행될 때 한 번만 수행되어야 합니다.  
이를 위해 `LaunchedEffect`와 `remember`를 사용하여 데이터를 가져올 수 있습니다.

```kotlin
@Composable
fun InitialDataExample() {
  

 val data = remember { mutableStateOf("No data") }

   LaunchedEffect(key1 = true) {
       // 데이터를 가져옴
       val newData = fetchData()
       data.value = newData
   }

   // UI 업데이트
   Text(data.value)
}
```

Composable 함수가 재구성될 때마다 `LaunchedEffect`는 실행되지 않지만, `key1` 파라미터 값이 변경될 때만 실행됩니다.   
이 경우 `key1`는 `true`로 고정되어 있으므로 `LaunchedEffect` 블록은 한 번만 실행됩니다.   
따라서, 위와 같은 예제 코드는 1번만 데이터를 초기화하는데 사용할 수 있습니다.