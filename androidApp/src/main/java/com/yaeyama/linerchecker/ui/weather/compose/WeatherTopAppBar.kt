package com.yaeyama.linerchecker.ui.weather.compose

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.yaeyama.linerchecker.ui.common.PreviewBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopAppBar(
    @StringRes titleRes: Int,
) {
    TopAppBar(
        title = {
            Text(
                stringResource(titleRes),
                color = Color.White,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
    )
}

@Preview
@Composable
private fun WeatherTopAppBarPreview() {
    PreviewBox {
        WeatherTopAppBar(R.string.weather_screen_title)
    }
}
