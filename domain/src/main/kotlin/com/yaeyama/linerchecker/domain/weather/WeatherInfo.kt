package com.yaeyama.linerchecker.domain.weather

/**
 * 天気ルート
 */
data class WeatherInfo(
    /** 今日の天気 */
    var today: Weather = Weather(),
    /** 明日の天気 */
    var tomorrow: Weather = Weather(),
)
