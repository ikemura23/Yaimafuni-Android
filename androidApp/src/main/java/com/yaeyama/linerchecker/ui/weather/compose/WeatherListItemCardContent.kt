package com.yaeyama.linerchecker.ui.weather.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.weather.Temperature
import com.yaeyama.linerchecker.domain.weather.Weather

/**
 * 天気情報（天気・気温・風・波）
 */
@Composable
fun WeatherListItemCardContent(weather: Weather) {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Row(
            // 「天気：」と値を1項目として読み上げる
            modifier = Modifier
                .fillMaxWidth()
                .semantics(mergeDescendants = true) {},
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(stringResource(R.string.weather_label_weather))
            Text(weather.weather)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            // 「天気：」と値を1項目として読み上げる
            modifier = Modifier
                .fillMaxWidth()
                .semantics(mergeDescendants = true) {},
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(stringResource(R.string.weather_label_high))
            Text(stringResource(R.string.temperature_format, weather.temperature.hight))
        }

        Row(
            // 「天気：」と値を1項目として読み上げる
            modifier = Modifier
                .fillMaxWidth()
                .semantics(mergeDescendants = true) {},
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(stringResource(R.string.weather_label_low))
            Text(stringResource(R.string.temperature_format, weather.temperature.low))
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            // 「天気：」と値を1項目として読み上げる
            modifier = Modifier
                .fillMaxWidth()
                .semantics(mergeDescendants = true) {},
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(stringResource(R.string.weather_label_wind))
            Text(weather.wind)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            // 「天気：」と値を1項目として読み上げる
            modifier = Modifier
                .fillMaxWidth()
                .semantics(mergeDescendants = true) {},
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(stringResource(R.string.weather_label_wave))
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
