package com.yaeyama.linerchecker.repository

import com.yaeyama_liner_checker.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchWeather(): Flow<UiState<WeatherInfo>>
}
