package com.yaeyama.linerchecker.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.yaeyama.linerchecker.domain.repository.WeatherRepository
import com.yaeyama.linerchecker.domain.weather.WeatherInfo
import com.yaeyama.linerchecker.ext.valueEvents
import kotlinx.coroutines.flow.Flow

/**
 * 天気 Repository
 */
class WeatherRepositoryImpl(
    private val database: FirebaseDatabase,
) : WeatherRepository {

    /**
     * 天気を取得
     */
    override fun fetchWeather(): Flow<WeatherInfo> =
        database.valueEvents("weather") { snapshot ->
            snapshot.getValue<WeatherInfo>()
        }
}
