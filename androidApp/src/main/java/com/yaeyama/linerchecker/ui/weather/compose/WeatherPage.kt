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
import com.yaeyama.linerchecker.domain.weather.WeatherInfo
import com.yaeyama.linerchecker.ui.common.LoadState
import com.yaeyama.linerchecker.ui.common.compose.FullScreenErrorContent
import com.yaeyama.linerchecker.ui.common.compose.LoadingContent

@Composable
fun WeatherPage(
    modifier: Modifier = Modifier,
    uiState: LoadState<WeatherInfo>,
    onRetry: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(color = Color.Transparent)
            .fillMaxSize(),
    ) {
        if (uiState is LoadState.Success) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                val values: List<Weather> = listOf(uiState.data.today, uiState.data.tomorrow)
                items(values) { weather ->
                    WeatherListItem(
                        weather = weather,
                    )
                }
            }
        }
        if (uiState is LoadState.Loading) {
            LoadingContent()
        }
        if (uiState is LoadState.Error) {
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
    WeatherPage(uiState = LoadState.Loading)
}

@Preview(showBackground = true)
@Composable
private fun WeatherPageErrorPreview() {
    WeatherPage(uiState = LoadState.Error(R.string.weather_fetch_failed))
}
