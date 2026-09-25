package com.yaeyama.linerchecker.ui.weather.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.weather.Weather
import com.yaeyama.linerchecker.ui.common.compose.FullScreenErrorContent
import com.yaeyama.linerchecker.ui.common.compose.LoadingContent
import com.yaeyama.linerchecker.ui.weather.WeatherUiState

@Composable
fun WeatherPage(
    modifier: Modifier = Modifier,
    uiState: WeatherUiState,
    onRetry: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(color = Color.Transparent)
            .fillMaxSize(),
    ) {
        if (uiState is WeatherUiState.Success) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                val values: List<Weather> = listOf(uiState.weather.today, uiState.weather.tomorrow)
                items(values) { weather ->
                    WeatherListItem(
                        weather = weather,
                    )
                }
            }
        }
        if (uiState is WeatherUiState.Loading) {
            LoadingContent()
        }
        if (uiState is WeatherUiState.Error) {
            FullScreenErrorContent(
                messageRes = uiState.messageRes,
                onRetry = onRetry,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherPagePreview() {
    WeatherPage(uiState = WeatherUiState.Loading)
}

@Preview(showBackground = true)
@Composable
private fun WeatherPageErrorPreview() {
    WeatherPage(uiState = WeatherUiState.Error(R.string.weather_fetch_failed))
}
