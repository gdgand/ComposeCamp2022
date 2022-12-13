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
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.core.widget.TextViewCompat
import com.google.android.material.composethemeadapter.MdcTheme
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel

//메모리 누수?
//viewmodel은 프래그먼트나 컴포지션 보다 수명이 길어서 걱정할 필요없다
//stateful : opinionated (프래그먼트를 호출할 수 있다?)
@Composable
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel) {
    // 스트림을 compose의 state api로 변환함
    val currentPlant by plantDetailViewModel.plant.observeAsState()
    currentPlant?.let { plant ->
        PlantDetailDescription(plant)
    }

}

//가변상태를 포함하지 않기 떄문에 상태를 저장하지 않는다. stateless : Preview + reusable
@Composable
private fun PlantDetailDescription(plant: Plant) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.margin_normal))
        ) {
            PlantName(name = plant.name)
            PlantWatering(wateringInterval = plant.wateringInterval)
            PlantDescription(description = plant.description)
        }
    }
}

@Composable
fun PlantName(name: String) {
    Text(
        text = name,
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally),
        style = MaterialTheme.typography.h5
    )
}

@Composable
fun PlantWatering(wateringInterval: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
    ) {
        Text(
            text = stringResource(id = R.string.watering_needs_prefix),
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_normal)),
            color = MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold
        )
        val resources = LocalContext.current.resources
        val quantityString = resources.getQuantityString(
            R.plurals.watering_needs_suffix,
            wateringInterval, wateringInterval
        )
        Text(text = "Every $quantityString days")
    }
}

//composable text함수에는 html를 랜더링할 수 없어서 우회적인 방법으로 참고한다.
//html마크업을 분석하고 앱 화면에서 클릭 가능한 링크를 인식한다.
@Composable
fun PlantDescription(description: String){
    AndroidView(
        factory = { context -> //뷰타입 생성
        TextView(context).apply{
            movementMethod = LinkMovementMethod.getInstance()
            TextViewCompat.setTextAppearance(this, android.R.style.TextAppearance_Medium)
        } },
        update ={ tv -> //콜백으로 레이아웃이 전개된 후 실행
            tv.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
        },
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            .padding(top = dimensionResource(id = R.dimen.margin_small))
            .heightIn(min = dimensionResource(id = R.dimen.plant_description_min_height))
    )

}

@Preview
@Composable
fun PlantDetailDescriptionPreivew() {
    MdcTheme() {
        val plant = Plant(
            plantId = "1",
            name = "Abocado",
            description = "HELLOE",
            growZoneNumber = 1,
            wateringInterval = 7, // how often the plant should be watered, in days
            imageUrl = ""
        )
        PlantDetailDescription(plant)
    }
}
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
fun PlantNamePreview() {
    MdcTheme {
        PlantName(name = "Abocado")
    }
}