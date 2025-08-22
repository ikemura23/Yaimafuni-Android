package com.yaeyama.linerchecker.ui.weather

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.yaeyama.linerchecker.ui.weather.compose.WeatherPage
import com.yaeyama.linerchecker.ui.weather.compose.WeatherTopAppBar
import timber.log.Timber

@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by weatherViewModel.weatherFlow.collectAsState()
    WeatherScreen(
        uiState,
        modifier
    )
}

@Composable
private fun WeatherScreen(
    uiState: WeatherUiState,
    modifier: Modifier = Modifier,
) {
    Timber.d("XXX uiState: $uiState")
    Scaffold(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        topBar = { WeatherTopAppBar(titleRes = R.string.weather_screen_title) },
    ) { padding ->
        WeatherPage(
            modifier = Modifier.padding(padding),
            uiState = uiState,
        )
    }
}

@Preview
@Composable
private fun WeatherScreenPreview() {
    WeatherScreen(
        WeatherUiState.Loading,
    )
}
