package com.yaeyama.linerchecker.domain.repository

import com.yaeyama.linerchecker.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchWeather(): Flow<WeatherInfo>
}
