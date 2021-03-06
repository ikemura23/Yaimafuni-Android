package com.ikemura.shared.model.weather

import kotlinx.serialization.Serializable

/**
 * 天気ルート
 */
@Serializable
data class WeatherInfo(
    // 今日の天気
    var today: Weather = Weather(),
    // 明日の天気
    var tomorrow: Weather = Weather()
)
