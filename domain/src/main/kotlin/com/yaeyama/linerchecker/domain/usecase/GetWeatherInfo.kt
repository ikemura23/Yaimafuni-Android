package com.yaeyama.linerchecker.domain.usecase

import com.yaeyama.linerchecker.domain.repository.WeatherRepository
import com.yaeyama.linerchecker.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

/**
 * 八重山地方の今日・明日の天気を取得する
 */
class GetWeatherInfo(
    private val weatherRepository: WeatherRepository,
) {
    operator fun invoke(): Flow<WeatherInfo> = weatherRepository.fetchWeather()
}
