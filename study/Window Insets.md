# Window insets in compose

> - 'edge-to-edge'로 가는 것은 '앱 컨텐츠'가 'Status Bar'와 'Navigation Bar' 뒤로 확장되는 것을 의미함
> - `WindowInsets`을 통해 'App UI'가 'System UI'에 의해 가려지지 않고 표현되도록 할 수 있음
>   - 키보드가 나타날 때 'App UI'를 키보드 위로 옮기기
>   - 'Status Bar' 아래로 확장된 이미지, 텍스트가 가려지지 않도록 상단에 여백 추가 처리

---

Android 플랫폼은 'Status Bar', 'Navigation Bar'와 같은 'System UI'를 그리는 역할을 합니다.  
이 'System UI'는 사용자가 어떤 앱을 사용하든 항상 표시됩니다.

컴포즈는 'Foundation' 계층의 `WindowInsets`를 통해 앱이 올바른 영역에 그려지도록 할 수 있습니다.  
즉, 'System UI'에 의해 'App UI'가 가려지지 않도록, 'System UI'에 대한 정보를 통해 'App UI'가 올바른 영역에 그려지도록 할 수 있습니다.

<img src="../resource/e2e-intro.gif" height="300" />

기본적으로 'App UI'는 'System UI' 내에서 배치되도록 제한됩니다.  
이는 '앱의 컨텐츠'가 'System UI'에 의해 가려지지 않도록 보장합니다.

그러나, 'Sytem UI' 영역에 'App UI'를 표현하는 것이 '앱의 컨텐츠 표현 확장성'과 '더 매끄러운 UX'를 제공할 수 있기에 권장됩니다.  
이는 앱이 'System UI'와 함께 애니메이션을 수행할 수 있게 하며, 특히 소프트웨어 키보드를 보이거나 숨길 때 유용합니다.

이처럼 'System UI' 뒤에 '앱 컨텐츠'를 보여주는 것을 'edge-to-edge'로 가는 것이라고 합니다.  
'edge-to-edge'로 가는 방식을 사용할 때는, `WindowInsets` API를 활용하여 컨텐츠가 'System UI'에 가려지지 않도록 해야 합니다. 

## Inset fundamentals

> - 'Inset'은 각 'System UI 요소'의 크기와 위치를 제공 
>   - 'Inset'은 Pixel 단위로 'Screen'의 상하좌우 각 측면에서 'System UI'가 얼마나 확장되는지를 지정
> - Compose는 'App UI'가 가려지지 않도록 보장하는 Safe Inset Type 제공
>   - `WindowInsets.safeDrawing` : 스크린에서 'System UI'가 '앱 컨텐츠'를 가리는 것을 방지
>   - `WindowInsets.safeGestures` : 앱 내에서 수행되는 제스처와 시스템 제스처가 충돌하는 것을 방지
>   - `WindowInsets.safeContent` : 위 2가지 기능을 결합, 컨텐츠가 시각적으로나 제스처적으로 'System UI'와 중복되지 않도록 함

앱이 'edge-to-edge'로 갈 때, 중요한 컨텐츠와 상호작용이 'System UI'에 가려지지 않도록 보장되어야 합니다.  
예를 들어, 버튼이 'Navigation Bar' 뒤에 배치되면 사용자가 클릭할 수 없을 수 있습니다.

'System UI'의 크기와 위치에 대한 정보는 'Inset'들을 통해 지정됩니다.  
즉, 각각의 'System UI' 요소의 크기와 위치를 설명하는 'Inset Type'이 존재합니다.  
예를 들어, 'Status Bar Inset'은 'Status Bar'의 크기와 위치를 제공하고, 'Navigation Bar Inset'은 'Navigation Bar'의 크기와 위치를 제공합니다.

'Inset' 값은 pixel 단위로, 'Screen'에서 'StatusBar', 'NavigationBar', '기타 System UI 요소' 까지의 거리를 각각 '상하좌우'로 나타냅니다.  
따라서 개발자는 'Inset'을 활용하여 'App UI'와 해당 'Sysytem UI'가 겹치지 않도록 적절한 여백을 설정할 수 있습니다.

Android 내부 'Inset Type'들은 [WindowInsets을 통해 사용이 가능](https://developer.android.com/jetpack/compose/layouts/insets#inset-fundamentals)합니다.

아래 3가지 타입들은 '앱 컨텐츠'가 가려지지 않도록 보장하는 'Safe Inset Type'들입니다.

- `WindowInsets.safeDrawing`
- `WindowInsets.safeGestures`
- `WindowInsets.safeContent`

위 3가지 타입들은 'Platform Inset'을 기반으로 '앱 컨텐츠'를 다양한 방식으로 보호합니다.

`WindowInsets.safeDrawing`은 'System UI'에 의해 가려져셔는 안되는 컨텐츠를 그리는 것을 방지하는데 사용됩니다.  
즉, 스크린에서 'System UI'가 '앱 컨텐츠'를 가리는 것을 방지하여 항상 사용자에게 컨텐츠가 보여지는 것을 보장합니다.

`WindowInsets.safeGestures`는 앱 내에서 사용자가 수행하는 제스처와 시스템 제스처가 충돌하는 것을 방지합니다.  
즉, 사용자가 화면 '하단에서 위로 스와이프'할 때, 이러한 제스처가 시스템 제스처(앱 드로어를 여는 제스처)와 충돌하지 않도록 합니다.

`WindowInsets.safeContent`는 `WindowInsets.SafeDrawing`과 `WindowInsets.SafeGestures`의 기능을 결합하여 
컨텐츠가 시각적으로나 제스처적으로 'System UI'와 중복되지 않도록 합니다. 
즉, '앱 컨텐츠'가 'System UI' 뒤로 그려지거나 시스템 제스처와 충돌하는 것을 모두 방지합니다. 

---

## Insets 적용하기

1. `Activity.onCreate`에서 `WindowCompat.setDecorFitsSystemWindows(window, false)`를 호출합니다. 
    이 호출은 앱이 시스템 UI 뒤에 표시되도록 요청합니다. 그러면 앱이 이러한 `insets`가 UI를 조정하는 데 어떻게 사용되는지를 제어하게 됩니다.

2. `Activity`의 `AndroidManifest.xml` 항목에서 `android:windowSoftInputMode="adjustResize"`를 설정합니다. 
   이 설정을 통해 앱은 소프트웨어 `IME`의 크기를 `insets`로 받을 수 있으며, `IME`가 앱에 나타나거나 사라질 때 적절하게 콘텐츠를 패딩하고 배치하는 데 사용할 수 있습니다.
```xml
<!-- in your AndroidManifest.xml file: -->
<activity
  android:name=".ui.MainActivity"
  android:label="@string/app_name"
  android:windowSoftInputMode="adjustResize"
  android:theme="@style/Theme.MyApplication"
  android:exported="true">
```

3. 앱의 테마에서 시스템 바와 아이콘의 색상을 투명으로 조정합니다.

```xml
<style name="Theme.MyApplication" parent="Theme.Material.DayNight.NoActionBar">
    <!--Other app theme properties-->
    <item name="android:statusBarColor">@android:color/transparent</item>
    <item name="android:navigationBarColor">@android:color/transparent</item>
</style>
```