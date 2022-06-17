package com.ikemura.shared.repository

import com.ikemura.shared.model.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchWeather(): Flow<UiState<WeatherInfo>>
}
