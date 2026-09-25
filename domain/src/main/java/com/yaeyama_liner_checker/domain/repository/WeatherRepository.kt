package com.yaeyama_liner_checker.domain.repository

import com.yaeyama_liner_checker.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchWeather(): Flow<WeatherInfo>
}
