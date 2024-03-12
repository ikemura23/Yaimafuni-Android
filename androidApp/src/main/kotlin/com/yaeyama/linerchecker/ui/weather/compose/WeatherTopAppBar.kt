package com.yaeyama.linerchecker.ui.weather.compose

import androidx.annotation.StringRes
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.R

@Composable
fun WeatherTopAppBar(
    @StringRes titleRes: Int,
) {
    TopAppBar(
        contentColor = Color.White,
        title = { Text(stringResource(titleRes)) },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
    )
}

@Preview(showBackground = false)
@Composable
private fun WeatherTopAppBarPreview() {
    WeatherTopAppBar(R.string.weather_screen_title)
}
