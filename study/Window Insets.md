# Window insets in compose

안드로이드 플랫폼은 상태 바나 네비게이션 바 같은 시스템 UI를 그리는 것을 담당합니다.
이 시스템 UI는 사용자가 어떤 앱을 사용하는지에 상관없이 항상 표시됩니다.

`WindowInsets`은 앱이 올바른 위치에 그려질 수 있도록, 그리고 앱의 UI가 시스템 UI에 의해 가려지지 않도록 시스템 UI에 대한 정보를 제공합니다.

<img src="../resource/e2e-intro.gif" width="50%" />

기본적으로 앱의 UI는 상태 바나 네비게이션 바와 같은 시스템 UI 내부에 레이아웃이 배치됩니다.
이렇게 하면 앱의 콘텐츠가 시스템 UI 요소에 의해 가려지지 않습니다.

그러나, 시스템 UI가 함께 표시되는 영역에서 앱을 표시하는 것을 권장합니다.
이렇게 하면 사용자 경험이 더욱 원활해지고, 앱은 화면 공간을 최대한 활용할 수 있게 됩니다.
또한, 소프트웨어 키보드를 표시하거나 숨길 때 시스템 UI와 함께 애니메이션을 실행할 수 있습니다.

이런 영역에서 표시하도록 선택하고, 시스템 UI 뒤에 콘텐츠를 표시하는 것을 "edge-to-edge"로 가는 것이라고 합니다.

## Inset의 기본

앱이 'edge-to-edge'로 갈 때, 중요한 콘텐츠나 상호작용이 시스템 UI에 가려지지 않도록 해야 합니다.   
예를 들어, 네비게이션 바 뒤에 버튼이 위치해 있다면 사용자가 그 버튼을 클릭할 수 없을 것입니다.

시스템 UI의 크기와 위치는 `insets`를 통해 지정됩니다.   
시스템 UI의 각 부분은 해당 크기와 위치를 설명하는 `inset`을 갖습니다.   
예를 들어, 상태 바의 크기와 위치는 상태 바의 `insets`에 의해 제공되며, 네비게이션 바의 크기와 위치는 네비게이션 바의 `insets`에 의해 제공됩니다.

각각의 `inset`은 상하좌우 네 가지 픽셀 값으로 이루어져 있습니다.  
이들 값은 시스템 UI가 앱의 창 어느 측면에서 얼마나 떨어져 있는지를 나타냅니다.
그러므로, 앱의 UI는 시스템 UI와 겹치지 않도록 그만큼의 간격을 둬야 합니다.

> 안드로이드 내부 inset 유형은 `WindowInsets`을 통해 이용할 수 있습니다.

| 속성                                             | 설명                                                                                                                                                  |
|------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| WindowInsets.statusBars                        | 상태 바를 설명하는 insets. 알림 아이콘과 기타 표시 항목이 포함된 상단 시스템 UI 바를 나타냅니다.                                                                                        |
| WindowInsets.statusBarsIgnoringVisibility      | 보이는 경우의 상태 바 insets. 상태 바가 현재 숨겨져 있다면(몰입형 전체 화면 모드로 들어갔을 경우), 주요 상태 바 insets는 비어있지만, 이 insets는 비어 있지 않을 것입니다.                                       |
| WindowInsets.navigationBars                    | 네비게이션 바를 설명하는 insets. 이들은 장치의 왼쪽, 오른쪽 또는 하단에 있는 시스템 UI 바로, 작업 표시 줄이나 네비게이션 아이콘을 나타냅니다. 이들은 사용자의 선호하는 네비게이션 방법과 작업 표시 줄과의 상호작용에 따라 실행 중에 변경될 수 있습니다. |
| WindowInsets.navigationBarsIgnoringVisibility  | 보이는 경우의 네비게이션 바 insets. 네비게이션 바가 현재 숨겨져 있다면(몰입형 전체 화면 모드로 들어갔을 경우), 주요 네비게이션 바 insets는 비어있지만, 이 insets는 비어 있지 않을 것입니다.                              |
| WindowInsets.captionBar                        | 미디어에서 실시간 자막을 위한 캡션 바를 설명하는 inset입니다.                                                                                                               |
| WindowInsets.captionBarIgnoringVisibility      | 보이는 경우의 캡션 바 insets. 캡션 바가 현재 숨겨져 있다면, 주요 네비게이션 바 insets는 비어있지만, 이 insets는 비어 있지 않을 것입니다.                                                           |
| WindowInsets.systemBars                        | 시스템 바 insets의 합,여기에는 상태 바, 네비게이션 바, 캡션 바가 포함됩니다.                                                                                                    |
| WindowInsets.systemBarsIgnoringVisibility      | 보이는 경우의 시스템 바 insets. 시스템 바가 현재 숨겨져 있다면(몰입형 전체 화면 모드로 들어갔을 경우), 주요 시스템 바 insets는 비어있지만, 이 insets는 비어 있지 않을 것입니다.                                    |
| WindowInsets.ime                               | 소프트웨어 키보드가 차지하는 하단 공간을 설명하는 insets입니다.                                                                                                              |
| WindowInsets.imeAnimationSource                | 현재 키보드 애니메이션 이전에 소프트웨어 키보드가 차지하고 있던 공간을 설명하는 insets입니다.                                                                                             |
| WindowInsets.imeAnimationTarget                | 현재 키보드 애니메이션 후에 소프트웨어 키보드가 차지할 공간을 설명하는 insets입니다.                                                                                                  |
| WindowInsets.tappableElement                   | 시스템이 아닌 앱에서 "탭" 처리할 공간을 알려주는 내비게이션 UI에 대한 자세한 정보를 설명하는 insets 유형입니다. 제스처 네비게이션을 사용하는 투명한 네비게이션 바에서 일부 앱 요소는 시스템 내비게이션 UI를 통해 탭 할 수 있습니다.            |
| WindowInsets.tappableElementIgnoringVisibility | 보이는 경우의 탭 가능 요소 insets. 탭 가능 요소가 현재 숨겨져 있다면(몰입형 전체 화면 모드로 들어갔을 경우), 주요 탭 가능 요소 insets는 비어있지만, 이 insets는 비어 있지 않을 것입니다.                              |
| WindowInsets.systemGestures                    | 시스템이 네비게이션을 위해 제스처를 인터셉트할 insets의 양을 나타냅니다. 앱은 Modifier.systemGestureExclusion를 통해 이러한 제스처 중 일부를 직접 처리하도록 지정할 수 있습니다.                               |
| WindowInsets.mandatorySystemGestures           | 시스템이 항상 처리하며, Modifier.systemGestureExclusion을 통해 선택적으로 제외할 수 없는 시스템 제스처의 하위 집합입니다.                                                                 |
| WindowInsets.displayCutout                     | 디스플레이 컷아웃(노치 또는 핀홀)과 겹치지 않도록 필요한 간격을 나타내는 insets입니다.                                                                                                |
| WindowInsets.waterfall                         | 워터폴 디스플레이의 곡선 부분을 나타내는 insets입니다. 워터폴 디스플레이는 화면이 장치의 측면을 따라 감싸는 곡선 부분이 있는 화면의 가장자리를 갖습니다.                                                           |

`WindowInsets`은 3가지 안전한 inset 유형을 제공하고 이 유형을 사용하면 Content 들이 가려지지 않습니다,
- WindowInsets.safeDrawing : 일반적인 Inset 사용법으로 시스템 UI에 의해 Content가 가려지지 않도록 합니다.
- WindowInsets.safeGestures : 시스템 제스처와 앱 제스처(Bottom Sheet, Carousels 등)가 충돌하는 것을 방지 합니다.
- WindowInsets.safeContent : `safeDrawing`과 `safeGestures`를 모두 적용합니다.

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