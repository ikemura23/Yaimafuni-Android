package com.ikemura.shared.repository

import com.ikemura.shared.model.weather.WeatherInfo
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
class WeatherRepository : KoinComponent {
    private val database: FirebaseDatabase by inject()

    /**
     * 天気を取得
     */
    fun fetchWeather(): Flow<UiState<WeatherInfo>> {
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

//    /**
//     * テスト表示用
//     */
//    fun fetchDummyWeather(): Flow<WeatherUiState> = callbackFlow {
//        while (true) {
//            delay(3000)
//            // 値を送信
//            offer(WeatherUiState.Success(createDummyData()))
//            // offer(WeatherUiState.Error("error"))
//        }
//
//    }
//
//    private fun createDummyData(): WeatherInfo {
//        val weather = Weather(
//            date = "1月2日(土)",
//            temperature = Temperature(
//                hight = "15℃",
//                low = "12℃"
//            ),
//            wave = "2.5メートル",
//            weather = "曇り",
//            wind = "北東の風やや強く",
//            table = listOf(
//                Table(
//                    hour = "06",
//                    weather = "晴れ",
//                    windBlow = "1",
//                    windSpeed = "5"
//                )
//            )
//        )
//        return WeatherInfo(
//            today = weather,
//            tomorrow = weather
//        )
//    }
}

sealed class WeatherUiState {
    data class Success(val weatherInfo: WeatherInfo) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}
