package com.yaeyama.linerchecker.ui.weather.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama_liner_checker.domain.weather.Temperature
import com.yaeyama_liner_checker.domain.weather.Weather

/**
 * 天気情報（天気・気温・風・波）
 */
@Composable
fun WeatherListItemCardContent(weather: Weather) {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("天気：")
            Text(weather.weather)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("最高：")
            Text(" ${weather.temperature.hight} ℃")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("最低：")
            Text(" ${weather.temperature.low} ℃")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("風：")
            Text(weather.wind)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("波：")
            Text(weather.wave)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherListItemCardContentPreview() {
    WeatherListItemCardContent(
        Weather(
            date = "YYYY/MM/DD",
            weather = "曇り時々雨",
            temperature = Temperature(
                hight = "21",
                low = "18",
            ),
            wind = "北東の風強く",
            wave = "４メートルうねりを伴う",
        ),
    )
}
