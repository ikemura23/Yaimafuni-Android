package com.yaeyama.linerchecker.repository

import com.yaeyama.linerchecker.domain.repository.WeatherRepository
import com.yaeyama.linerchecker.domain.weather.Table
import com.yaeyama.linerchecker.domain.weather.Temperature
import com.yaeyama.linerchecker.domain.weather.Weather
import com.yaeyama.linerchecker.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.flow

/**
 * 天気のFakeリポジトリ
 */
class FakeWeatherRepository : WeatherRepository {
    override fun fetchWeather() = flow {
        val weatherInfo = WeatherInfo(
            today = Weather(
                date = "1月2日(土)",
                temperature = Temperature(
                    hight = "15℃",
                    low = "12℃",
                ),
                wave = "2.5メートル",
                weather = "曇り",
                wind = "北東の風やや強く",
                table = listOf(
                    Table(
                        hour = "06",
                        weather = "晴れ",
                        windBlow = "1",
                        windSpeed = "5",
                    ),
                ),
            ),
            tomorrow = Weather(
                date = "1月3日(日)",
                temperature = Temperature(
                    hight = "15℃",
                    low = "12℃",
                ),
                wave = "2.5メートル",
                weather = "曇り",
                wind = "北東の風やや強く",
                table = listOf(
                    Table(
                        hour = "06",
                        weather = "晴れ",
                        windBlow = "1",
                        windSpeed = "5",
                    ),
                ),
            ),
        )
        emit(weatherInfo)
    }
}
