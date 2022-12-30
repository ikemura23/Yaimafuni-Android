package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.yaeyama_liner_checker.domain.weather.Weather

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherListItem(
    modifier: Modifier = Modifier,
    weather: Weather,
    onItemClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onItemClick
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            CardHeader(weather.date)
            CardContent(weather)
        }
    }
}

@Composable
fun CardHeader(title: String) {
    Text(
        text = title,
        modifier = Modifier.background(color = colorResource(id = R.color.primary)).fillMaxWidth()
            .padding(16.dp),
        style = TextStyle.Default.copy(color = Color.White)
    )
}

@Composable
fun CardContent(weather: Weather) {
    // 天気情報（天気・気温・風・波）
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("天気：")
            Text(weather.weather)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("気温：")
            Text("最高: ${weather.temperature.hight}℃ 最低: ${weather.temperature.low}℃")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("風：")
            Text(weather.wind)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("波：")
            Text(weather.wave)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherListItemPreview() {
    WeatherListItem(
        weather = Weather(),
        onItemClick = {}
    )
}

@Preview
@Composable
fun CardHeaderPreview() {
    CardHeader("タイトル")
}

@Preview(showBackground = true)
@Composable
fun CardContentPreview() {
    CardContent(Weather())
}
