package com.ikemura.shared.repository

import com.ikemura.shared.model.weather.WeatherInfo
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.app
import dev.gitlive.firebase.database.DataSnapshot
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * 天気 Repository
 */
class WeatherRepository(
    // TODO: DIする
//    private val dbRef: DatabaseReference
) {

    /**
     * 天気を取得
     */
    fun fetchWeather(): Flow<WeatherUiState> = flow {
        val dbRef = Firebase.database(Firebase.app).reference("weather")
        dbRef.valueEvents
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
