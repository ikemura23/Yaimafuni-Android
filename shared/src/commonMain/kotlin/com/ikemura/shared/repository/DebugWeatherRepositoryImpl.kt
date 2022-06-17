package com.ikemura.shared.repository

import com.ikemura.shared.model.weather.Table
import com.ikemura.shared.model.weather.Temperature
import com.ikemura.shared.model.weather.Weather
import com.ikemura.shared.model.weather.WeatherInfo
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class DebugWeatherRepositoryImpl : WeatherRepository, KoinComponent {
    override fun fetchWeather() = flow<UiState<WeatherInfo>> {
        val weatherInfo = WeatherInfo(
            today = Weather(
                date = "1月2日(土)",
                temperature = Temperature(
                    hight = "15℃",
                    low = "12℃"
                ),
                wave = "2.5メートル",
                weather = "曇り",
                wind = "北東の風やや強く",
                table = listOf(
                    Table(
                        hour = "06",
                        weather = "晴れ",
                        windBlow = "1",
                        windSpeed = "5"
                    )
                )
            ),
            tomorrow = Weather(
                date = "1月3日(日)",
                temperature = Temperature(
                    hight = "15℃",
                    low = "12℃"
                ),
                wave = "2.5メートル",
                weather = "曇り",
                wind = "北東の風やや強く",
                table = listOf(
                    Table(
                        hour = "06",
                        weather = "晴れ",
                        windBlow = "1",
                        windSpeed = "5"
                    )
                )
            ),
        )
        emit(UiState.Success(weatherInfo))
    }
}
