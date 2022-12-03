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

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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

@Composable // Stateful
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel) {
    // Observes values coming from the VM's LiveData<Plant> field
    val currentPlant by plantDetailViewModel.plant.observeAsState()

    currentPlant?.let { plant ->
        PlantDetailDescription(plant)
    }
}

@Composable // Stateless : Preview + reusable
private fun PlantDetailDescription(plant: Plant) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.margin_normal))
        ) {
            PlantName(name = plant.name)
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

@Composable
private fun PlantWatering(wateringInterval: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
    ) {
        Text(
            text = stringResource(id = R.string.watering_needs_prefix),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_normal))
        )

        val resources = LocalContext.current.resources
        val quantityString = resources.getQuantityString(
            R.plurals.watering_needs_suffix,
            wateringInterval,
            wateringInterval
        )
        Text(text = quantityString)
    }
}

@Composable
private fun PlantDescription(description: String) {
    val htmlDescription = remember(description) {
        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    AndroidView(
        factory = { context ->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance()
                TextViewCompat.setTextAppearance(this, android.R.style.TextAppearance_Medium)
            }
        },
        update = {
            it.text = htmlDescription
        },
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            .padding(top = dimensionResource(id = R.dimen.margin_small))
            .heightIn(min = dimensionResource(id = R.dimen.plant_description_min_height))
    )
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PlantDetailDescriptionPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    MdcTheme {
        PlantDetailDescription(plant = plant)
    }
}