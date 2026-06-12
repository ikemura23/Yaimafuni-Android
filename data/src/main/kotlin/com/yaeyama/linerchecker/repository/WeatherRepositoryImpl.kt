package com.yaeyama.linerchecker.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.yaeyama.linerchecker.ext.reference
import com.yaeyama.linerchecker.ext.valueEvents
import com.yaeyama_liner_checker.domain.common.UiState
import com.yaeyama_liner_checker.domain.repository.WeatherRepository
import com.yaeyama_liner_checker.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
/**
 * 天気 Repository
 */
class WeatherRepositoryImpl(
    private val database: FirebaseDatabase,
) : WeatherRepository {

    /**
     * 天気を取得
     */
    override fun fetchWeather(): Flow<UiState<WeatherInfo>> {
        val dbRef = database.reference("weather")
        return dbRef.valueEvents
            .map { snapShot: DataSnapshot ->
                snapShot.getValue<WeatherInfo>()
                    ?.let { UiState.Success(it) }
                    ?: UiState.Error(Exception("天気データが取得できませんでした"))
            }
            .catch {
                emit(UiState.Error(it))
            }
    }
}
