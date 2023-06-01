package com.ikemura.shared.repository

import com.yaeyama_liner_checker.domain.weather.WeatherInfo
import dev.gitlive.firebase.database.DataSnapshot
import dev.gitlive.firebase.database.FirebaseDatabase
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
                val deserializeValue = snapShot.value(WeatherInfo.serializer())
                UiState.Success(deserializeValue)
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
