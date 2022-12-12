package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ikmr.banbara23.yaeyama_liner_checker.ui.weather.WeatherScreenViewModel
import com.ikmr.banbara23.yaeyama_liner_checker.ui.weather.WeatherUiState

@Composable
fun WeatherScreen(
    weatherViewModel: WeatherScreenViewModel,
    modifier: Modifier = Modifier,
    onMoreClick: () -> Unit
) {
    val uiState by weatherViewModel.weatherFlow.collectAsState()
    WeatherScreen(
        uiState,
        onMoreClick
    )
}

@Composable
fun WeatherScreen(uiState: WeatherUiState, onMoreClick: () -> Unit) {
}

@Preview
@Composable
private fun WeatherScreenPreview() {
    WeatherScreen(
        WeatherUiState.Loading
    ) {}
}
