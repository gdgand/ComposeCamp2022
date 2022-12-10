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

//viewmodel 인스턴스 전달
@Composable
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel) {
    // Observes values coming from the VM's LiveData<Plant> field
    //라이브데이터 관찰을 시작하고 state 객체를 통해 값을 나타냄
    //새 값이 게시될 때마다 반환된 State가 업데이트되어 모든 State.value 사용이 재구성됨
    val plant by plantDetailViewModel.plant.observeAsState()

    // If plant is not null, display the content
    plant?.let {
        PlantDetailContent(it)
    }
}

@Composable
private fun PlantDescription(description: String) {
    // Remembers the HTML formatted description. Re-executes on a new description
    val htmlDescription = remember(description) {
        //HtmlCompat.fromHtml을 사용하여 TextView에 텍스트설정
        //그러나 Compose는 현재 Spanned 클래스를 지원하지 않으며 HTML 형식 텍스트도 표시하지 않습니다.
        //따라서 이 제한을 우회하려면 Compose 코드에서 뷰 시스템의 TextView를 사용해야 합니다.
        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
    //Compose는 아직 HTML 코드를 렌더링할 수 없으므로
    // 프로그래매틱 방식으로 TextView를 만들어
    // AndroidView API를 사용하여 정확히 렌더링을 해야 합니다.

    // Displays the TextView on the screen and updates with the HTML description when inflated
    // Updates to htmlDescription will make AndroidView recompose and update the text
    AndroidView(
        //context를 사용하여 HTML 상호작용에 반응하는 TextView를 초기화
        factory = { context ->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        //저장된 HTML 형식의 설명으로 텍스트를 설정
        update = {
            //htmlDescription 매개변수로 전달된 특정 description의 HTML 설명을 기억
            //description 매개변수가 변경되면
            //remember 내 htmlDescription 코드가 다시 실행됩니다

            //마찬가지로 htmlDescription변경 시
            //AndroidView 업데이트 콜백이 재구성
            //콜백 내에서 상태를 읽게 되면 재구성됨됨
            it.text = htmlDescription
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PlantWatering(wateringInterval: Int) {
    Column(Modifier.fillMaxWidth()) {
        // Same modifier used by both Texts
        val centerWithPaddingModifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.margin_small))
            .align(Alignment.CenterHorizontally)

        val normalPadding = dimensionResource(R.dimen.margin_normal)

        Text(
            text = stringResource(R.string.watering_needs_prefix),
            color = MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = centerWithPaddingModifier.padding(top = normalPadding)
        )

        val wateringIntervalText = pluralStringResource(
            R.plurals.watering_needs_suffix, wateringInterval, wateringInterval
        )
        Text(
            text = wateringIntervalText,
            modifier = centerWithPaddingModifier.padding(bottom = normalPadding)
        )
    }
}

@Composable
fun PlantDetailContent(plant: Plant) {
    //PlantName(plant.name)
    Surface {
        Column(Modifier.padding(dimensionResource(R.dimen.margin_normal))) {
            PlantName(plant.name)
            PlantWatering(plant.wateringInterval)
            PlantDescription(plant.description)
        }
    }
}

@Preview
@Composable
private fun PlantNamePreview() {
    MdcTheme {
        PlantName("Apple")
    }
}

@Preview
@Composable
private fun PlantDescriptionPreview() {
    //MaterialTheme ->MdcTheme
    MdcTheme {
        PlantDescription("HTML<br><br>description")
    }
}

@Preview
@Composable
private fun PlantDetailContentPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    //MaterialTheme -> MdcTheme
    MdcTheme {
        PlantDetailContent(plant)
    }
}

@Preview
@Composable
private fun PlantWateringPreview() {
    //MaterialTheme ->MdcTheme
    MdcTheme {
        PlantWatering(7)
    }
}

//미리보기 uiMode
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PlantDetailContentDarkPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    MdcTheme {
        PlantDetailContent(plant)
    }
}