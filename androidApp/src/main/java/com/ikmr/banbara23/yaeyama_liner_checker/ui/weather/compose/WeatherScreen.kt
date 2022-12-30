package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Composable
private fun WeatherPage(modifier: Modifier = Modifier, uiState: WeatherUiState) {
    Box(
        modifier = modifier
            .background(color = Color.Transparent)
            .fillMaxSize()
    ) {
        if (uiState is WeatherUiState.Success) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // TODO: 固定のアイテム数をAPIレスポンスに直す
                val values = listOf(uiState.weather.today, uiState.weather.tomorrow)
                items(values) { weather ->
                    WeatherListItem(
                        weather = weather,
                        onItemClick = {}
                    )
                }
            }
        }
        if (uiState is WeatherUiState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
private fun WeatherScreenPreview() {
    WeatherScreen(
        WeatherUiState.Loading
    ) {}
}

@Preview(showBackground = true)
@Composable
fun WeatherPagePreview() {
    WeatherPage(uiState = WeatherUiState.Loading)
}
