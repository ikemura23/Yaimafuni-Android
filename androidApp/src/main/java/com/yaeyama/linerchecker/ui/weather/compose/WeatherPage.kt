package com.yaeyama.linerchecker.ui.weather.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.ui.weather.WeatherUiState
import com.yaeyama_liner_checker.domain.weather.Weather

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
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
            )
        }
        if (uiState is WeatherUiState.Error) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.weather_fetch_failed),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = onRetry,
                ) {
                    Text(text = stringResource(R.string.retry))
                }
            }
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
    WeatherPage(uiState = WeatherUiState.Error)
}
