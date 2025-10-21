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
    onItemClick: () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        // onClick = onItemClick // TODO: 遷移先の画面がまだないので、いったんコメントアウト
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
        onItemClick = {},
    )
}
