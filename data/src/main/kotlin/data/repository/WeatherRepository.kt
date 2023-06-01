package com.ikemura.shared.repository

import com.yaeyama_liner_checker.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchWeather(): Flow<UiState<WeatherInfo>>
}
