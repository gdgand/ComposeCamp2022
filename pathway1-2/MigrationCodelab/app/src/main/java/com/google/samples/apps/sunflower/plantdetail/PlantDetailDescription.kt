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
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

@Composable
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel) {
    // viewModel 의 liveData<Plant> 필드로 부터 들어오는 값을 관찰한다.
    val plant by plantDetailViewModel.plant.observeAsState()
    // plant 가 null 이 아니면, 내용을 표시한다.
    plant?.let {
        PlantDetailContent(it)
    }
}

@Composable
fun PlantDetailContent(plant: Plant) {
    Surface {
        Column(Modifier.padding(dimensionResource(id = R.dimen.margin_normal))) {
            PlantName(name = plant.name)
            PlantWatering(wateringInterval = plant.wateringInterval)
            PlantDescription(description = plant.description)
        }
    }
}

@Preview
@Composable
private fun PlantDetailContentPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    MdcTheme {
        PlantDetailContent(plant = plant)
    }
}

@Composable
private fun PlantName(name: String) {
    Text(
        // Text의 스타일은 XML 코드에서 textAppearanceHeadline5에 매핑되는 MaterialTheme.typography.h5다.
        // Modifier는 Text를 장식하여 XML 버전처럼 보이도록 조정한다.
        // fillMaxWidth Modifier는 XML 코드의 android:layout_width=”match_parent”에 해당한다.
        // margin_small의 수평 padding은 View 시스템에서 사용하는 dimension Resource 헬퍼 함수로부터 온 값이다.
        // wrapContentWidth를 사용하여 Text를 수평으로 정렬한다.
        text = name,
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(
                    R.dimen.margin_small
                )
            )
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Preview
@Composable
private fun PlantNamePreview() {
    MdcTheme {
        PlantName(name = "Apple")
    }
}

@Composable
private fun PlantWatering(wateringInterval: Int) {
    // * 몇가지 알아야 할 것들이 있다.
    // Text 컴포저블에서 수평 패딩(horizontal padding) 및 정렬(align) 같은 데코레이션을 공유하므로,
    // Modifier를 로컬 변수(예: centerWithPaddingModifier)에 할당하여 Modifier를 재사용할 수 있다.
    // Modifier는 일반 Kotlin 객체이므로 그렇게 할 수 있다.

    // 컴포즈의 MaterialTheme는 plant_watering_header에 사용된 colorAccent와 정확히 일치하지 않는다.
    // 지금은 테마 섹션에서 개선할 MaterialTheme.colors.primaryVariant를 사용하겠다.
    Column(Modifier.fillMaxWidth()) {
        // 두 Text에 같은 modifier가 사용된다.
        val centerWithPaddingModifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.margin_small))
            .align(Alignment.CenterHorizontally)

        val normalPadding = dimensionResource(R.dimen.margin_normal)

        Text(
            text = stringResource(id = R.string.watering_needs_prefix),
            color = MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = centerWithPaddingModifier.padding(top = normalPadding)
        )
        val wateringIntervalText = LocalContext.current.resources.getQuantityString(
            R.plurals.watering_needs_suffix, wateringInterval, wateringInterval
        )
        Text(
            text = wateringIntervalText,
            modifier = centerWithPaddingModifier.padding(bottom = normalPadding)
        )
    }
}

@Preview
@Composable
private fun PlantWateringPreview() {
    MdcTheme {
        PlantWatering(wateringInterval = 7)
    }
}

@Composable
private fun PlantDescription(description: String) {
// HTML 형식의 description을 내부에 저장한다. 새로운 description이 들어오면 람다식을 재실행한다.
    val htmlDescription = remember(description) {
        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
// 화면상에 TextView를 표시하고 전개 될 때 HTML description을 업데이트 한다.
// htmlDescription을 업데이트 하는 것은 AndroidView를 재구성하도록 만들고, 텍스트를 업데이트 시킨다.
    AndroidView(
        factory = {context ->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = {
            it.text = htmlDescription
        }
    )
}

@Preview
@Composable
private fun PlantDescription() {
    MdcTheme {
        PlantDescription("HTML<br><br>description")
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES) // 다크모드로 preview 보는 함수.
@Composable
private fun PlantDetailContentDarkPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    MdcTheme {
        PlantDetailContent(plant)
    }
}

