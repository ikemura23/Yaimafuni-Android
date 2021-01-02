package com.ikmr.banbara23.yaeyama_liner_checker

import com.google.firebase.database.DatabaseReference
import com.ikmr.banbara23.yaeyama_liner_checker.front.weather.WeatherUiState
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.HourlyWeather
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.Temperature
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.Weather
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class WeatherRepository(private val ref: DatabaseReference) {

    @ExperimentalCoroutinesApi
    fun fetchWeather(): Flow<WeatherUiState> {
        return callbackFlow<WeatherUiState> {
            delay(3000)
            // 値を送信
            offer(WeatherUiState.Success(createDummyData()))
            // offer(WeatherUiState.Error("error"))

            awaitClose {
                // TODO: DatabaseReferenceを開放する
            }
        }
    }

    private fun createDummyData(): WeatherInfo {
        val weather = Weather(
            date = "1月2日(土)",
            temperature = Temperature(
                height = "15℃",
                low = "12℃"
            ),
            wave = "2.5メートル",
            weather = "曇り",
            wind = "北東の風やや強く",
            hourlyWeather = listOf(
                HourlyWeather(
                    hour = "06",
                    weather = "晴れ",
                    windBlow = "1",
                    windSpeed = "5"
                )
            )
        )
        return WeatherInfo(
            today = weather,
            tomorrow = weather
        )
    }
}
