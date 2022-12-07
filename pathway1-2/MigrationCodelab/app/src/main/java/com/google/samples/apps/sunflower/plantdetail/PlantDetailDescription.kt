/*
 * Copyright 2020 Google LLC
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

package com.google.samples.apps.sunflower.plantdetail

import android.content.res.Configuration
import android.provider.SyncStateContract.Helpers.update
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.google.android.material.composethemeadapter.MdcTheme
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel


// PlantDetailViewModel 매개변수 추가
@Composable
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel) {

    // Composable에서 LiveData를 관찰하기 위해 LiveData.observeAsState() 함수를 사용.
    val plant by plantDetailViewModel.plant.observeAsState()

    // plant가 null이 아니라면 Content 표시
    plant?.let {
        PlantDetailContent(it)
    }

//    Surface {
//        Text("Hello Compose")
//    }

}
// Plant 정보를 표시하는 Composable
/**
 * PlantDetailContent에서 이름과 물주기 정보를 함께 표시하고 이를 padding으로 포함하는 Column 생성
 * 또한 사용되는 배경 색상과 텍스트 색상이 적절하도록 하기 위해 이를 처리하는 Surface 추가.
 */
@Composable
    fun PlantDetailContent(plant: Plant) {
    Surface {
        Column(Modifier.padding(dimensionResource(R.dimen.margin_normal))) {
            PlantName(plant.name)
            PlantWatering(plant.wateringInterval)
            PlantDescription(plant.description)
        }
    }
    }

// xml을 사용하여 Composable 생성
/**
 * Text의 스타일은 Xml 코드에서 textAppearanceHeadline5로 매핑되는 MaterialTheme.typography.h5
 * fillMaxWidth modifier는 XML에서 android:layout_width="match_parent"
 * dimensionResource => 가로로 padding 값을 줌. (margin_small-> 8dp)
 * 만약 strings.xml을 가져올 경우 stringResource(id) 를 사용.
 * wrapContentWidht => Text를 가로로 정렬.
 */
@Composable
private fun PlantName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.margin_small))
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

/**
 * PlantWatering 이라는 새 Composable을 만들고 Text를 추가해 화면에 물주기 정보를 표시
 * @OptIn(ExperimentalComposeUiApi::class) -> Jetpack Compose를 사용하여 개발할 때 일부
   실험적인 기능 이나 베타 기능을 사용할 때 해당 어노테이션을 사용 한다.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PlantWatering(wateringInterval: Int) {
    Column(Modifier.fillMaxWidth()) {
        val centerWithPaddingModifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.margin_small))
            .align(Alignment.CenterHorizontally)        // 가로축 중앙 정렬

        val normalPadding = dimensionResource(R.dimen.margin_normal)

        Text(
            text = stringResource(R.string.watering_needs_prefix),
            color = MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = centerWithPaddingModifier.padding(top = normalPadding)
        )

        // pluralStringResource() : 복수 형태의 문자열 전달시 사용 - 현재 베타 단계
        val wateringIntervalText = pluralStringResource(
            R.plurals.watering_needs_suffix, wateringInterval, wateringInterval
        )
        
        Text(
            text = wateringIntervalText,
            modifier = centerWithPaddingModifier.padding(bottom = normalPadding)
        )
    }
}


/**
 * AndroidView를 호출하는 새 Composable
 * factory 콜백에서 주어진 Context를 사용하여 HTML 상호작용에 반응하는 TextView를 초기화, update 콜백에서 저장된 HTML
   형식의 설명으로 텍스트를 설정.
 */
@Composable
private fun PlantDescription(description: String) {
    // htmlDescription = 매개변수로 전달된 특정 description의 HTML 설명을 기억
    // description 매개변수가 변경되면 remember 내 htmlDescription 코드가 다시 실행.
    val htmlDescription = remember(description) {
        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = {
            it.text = htmlDescription
        }
    )

}

// PlantName Composable 결과 미리보기
@Preview
@Composable
private fun PlantNamePreview() {
    MdcTheme {
        PlantName("Apple")
    }
}

// PlantDetailContent의 Preview
// PlantName을 호출하기 때문에 동일한 Preview를 보여줌.
@Preview
@Composable
private fun PlantDetailContentPreview() {
    // <br><br> 줄바꿈 태그
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    MdcTheme {
        PlantDetailContent(plant)
    }
}


// 물주기 정보 미리표시 
@Preview
@Composable
private fun PlantWateringPreview() {
    MdcTheme {
        PlantWatering(7)
    }
}

@Preview
@Composable
private fun PlantDescriptionPreview() {
    MdcTheme {
        PlantDescription("HTML<br><br>description")
    }
}

// uiMode에 Configuration.UI_MODE_NIGHT_YES를 전달하여 어두운 테마 UI를 미리 볼수도 있음.
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PlantDetailContentDarkPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    MdcTheme {
        PlantDetailContent(plant)
    }
}