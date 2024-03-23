package com.ikemura23.yaeyama.linerchecker.ui.compose.weather

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikemura23.yaeyama.linerchecker.R
import com.ikemura23.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.title_weather))
                },
            )
        }
    ) { contentPadding ->
        Text(
            text = "TODO",
            modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
        )
    }
}

@Preview
@Composable
private fun WeatherScreenPreview() {
    YaimafuniAndroidTheme {
        WeatherScreen()
    }
}