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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * 天気 Repository
 */
class WeatherRepositoryImpl : WeatherRepository, KoinComponent {
    private val database: FirebaseDatabase by inject()

    /**
     * 天気を取得
     */
    override fun fetchWeather(): Flow<UiState<WeatherInfo>> {
        val dbRef = database.reference("weather")
        return dbRef.valueEvents
            .map { snapShot: DataSnapshot ->
                UiState.Success(snapShot.getValue<WeatherInfo>()!!)
            }
            .catch {
                UiState.Error(it)
            }
    }
}

// sealed class WeatherUiState {
//     object Loading
//     data class Success(val weatherInfo: WeatherInfo) : WeatherUiState()
//     data class Error(val message: String) : WeatherUiState()
// }
