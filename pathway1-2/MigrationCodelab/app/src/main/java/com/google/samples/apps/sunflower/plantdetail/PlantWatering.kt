package com.google.samples.apps.sunflower.plantdetail

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.google.samples.apps.sunflower.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PlantWatering(interval: Int) {
    val intervalText = pluralStringResource(R.plurals.watering_needs_suffix, interval, interval)

    Column(Modifier.fillMaxWidth()) {

        val centerWithPaddingModifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.margin_small))
            .align(Alignment.CenterHorizontally)

        PlantWateringHeader(centerWithPaddingModifier, value = R.string.watering_needs_prefix)
        Text(
            text = intervalText,
            modifier = centerWithPaddingModifier
        )
    }
}

@Composable
private fun PlantWateringHeader(modifier: Modifier = Modifier, @StringRes value: Int) {
    Text(
        text = stringResource(id = value),
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primarySurface
    )
}

@Preview
@Composable
fun PreviewPlantWatering() {
    MaterialTheme {
        PlantWatering(interval = 10)
    }
}
