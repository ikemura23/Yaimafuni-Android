package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.ui.weather.WeatherScreenViewModel
import com.ikmr.banbara23.yaeyama_liner_checker.ui.weather.WeatherUiState
import timber.log.Timber

@Composable
fun WeatherScreen(
    weatherViewModel: WeatherScreenViewModel,
    onMoreClick: () -> Unit
) {
    val uiState by weatherViewModel.weatherFlow.collectAsState()
    WeatherScreen(
        uiState,
        onMoreClick
    )
}

@Composable
private fun WeatherScreen(uiState: WeatherUiState, onMoreClick: () -> Unit) {
    Timber.d("XXX uiState: $uiState")
    Scaffold(
        backgroundColor = Color.Transparent,
        topBar = { WeatherTopAppBar(titleRes = R.string.weather_screen_title) }
    ) { padding ->
        WeatherPage(
            modifier = Modifier.padding(padding),
            uiState = uiState
        )
    }
}

@Preview
@Composable
private fun WeatherScreenPreview() {
    WeatherScreen(
        WeatherUiState.Loading
    ) {}
}
