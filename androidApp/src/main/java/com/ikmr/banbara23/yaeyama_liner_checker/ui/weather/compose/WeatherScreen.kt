package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
        topBar = { WeatherTopAppBar(titleRes = R.string.weather_screen_title) }
    ) { padding ->
        WeatherPage(
            modifier = Modifier
                .padding(padding)
        )
    }
}

@Composable
private fun WeatherTopAppBar(
    @StringRes titleRes: Int
) {
    TopAppBar(
        title = { Text(stringResource(titleRes)) },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}

@Composable
private fun WeatherPage(modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(color = Color.Transparent).fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // TODO: 固定のアイテム数をAPIレスポンスに直す
            items(5) {
                WeatherListItem() {}
            }
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
private fun WeatherTopAppBarPreview() {
    WeatherTopAppBar(R.string.weather_screen_title)
}

@Preview(showBackground = true)
@Composable
fun WeatherPagePreview() {
    WeatherPage()
}
