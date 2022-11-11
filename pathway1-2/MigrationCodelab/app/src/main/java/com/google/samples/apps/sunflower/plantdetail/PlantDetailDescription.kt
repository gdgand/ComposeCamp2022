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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.google.android.material.composethemeadapter.MdcTheme
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel

@Composable
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel) {
    val plant by plantDetailViewModel.plant.observeAsState()

    plant?.let {
        PlantDetailContent(it)
    }
}

@Composable
fun PlantDetailContent(plant: Plant) {
    Surface {
        Column(Modifier.padding(16.dp)) {
            PlantName(plant.name)
            PlantWatering(wateringInterval = plant.wateringInterval)
            PlantDescription(description = plant.description)
        }
    }
}


@Composable
private fun PlantName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PlantWatering(wateringInterval: Int) {
    Column(Modifier.fillMaxWidth()) {
        val centerWithPaddingModifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            .align(Alignment.CenterHorizontally)

        val normalPadding = dimensionResource(id = R.dimen.margin_normal)

        Text(
            text = stringResource(id = R.string.watering_needs_prefix),
            color = MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = centerWithPaddingModifier.padding(top = normalPadding)
        )

        val wateringIntervalText = pluralStringResource(
            id = R.plurals.watering_needs_suffix,
            wateringInterval,
            wateringInterval
        )

        Text(
            text = wateringIntervalText,
            modifier = centerWithPaddingModifier.padding(bottom = normalPadding)
        )

    }
}

@Composable
private fun PlantDescription(description : String){
    val htmlDescription = remember(description){
        HtmlCompat.fromHtml(description,HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    AndroidView(factory = { context ->
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
fun PlantDetailContentPreview() {
    val plant = Plant("id", "Apple", "description", 3, 30, "")
    MdcTheme {
        PlantDetailContent(plant)
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PlantDetailContentDarkPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    MdcTheme {
        PlantDetailContent(plant)
    }
}

@Preview
@Composable
fun PlantNamePreview() {
    MdcTheme {
        PlantName(name = "Apple")
    }
}

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