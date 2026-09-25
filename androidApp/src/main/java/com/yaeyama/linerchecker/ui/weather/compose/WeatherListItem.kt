package com.yaeyama.linerchecker.ui.weather.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yaeyama_liner_checker.domain.weather.Weather

@Composable
fun WeatherListItem(
    modifier: Modifier = Modifier,
    weather: Weather,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
        ) {
            WeatherListItemCardHeader(weather.date)
            WeatherListItemCardContent(weather)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherListItemPreview() {
    WeatherListItem(
        weather = Weather(),
    )
}
