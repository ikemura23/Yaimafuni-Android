package com.ikmr.banbara23.yaeyama_liner_checker.model.weather

/**
 * 天気ルート
 */
data class WeatherInfo(
    // 今日の天気
    var today: Weather = Weather(),
    // 明日の天気
    var tomorrow: Weather = Weather()
)
