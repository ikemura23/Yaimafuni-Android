package com.ikmr.banbara23.yaeyama_liner_checker.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.Table
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.Temperature
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.Weather
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo
import com.ikmr.banbara23.yaeyama_liner_checker.ui.weather.WeatherUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

/**
 * 天気 Repository
 */
class WeatherRepository(private val dbRef: DatabaseReference) {

    /**
     * 天気を取得
     */
    @ExperimentalCoroutinesApi
    fun fetchWeather(): Flow<WeatherUiState> = callbackFlow { // callbackFlowの戻り値はFlow型
        // DBへの接続
        dbRef.addValueEventListener(object : ValueEventListener {
            // 正常に取得できた
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(WeatherInfo::class.java)?.let { weather ->
                    Timber.d(weather.toString())
                    // offerで購読側に値を流せる
                    offer(WeatherUiState.Success(weather))
                }
            }

            // エラー
            override fun onCancelled(error: DatabaseError) {
                offer(WeatherUiState.Error(error.message))
            }
        })
        awaitClose {
            // DatabaseReference は自動で破棄される
        }
    }

    /**
     * テスト表示用
     */
    @ExperimentalCoroutinesApi
    fun fetchDummyWeather(): Flow<WeatherUiState> = callbackFlow {
        while (true) {
            delay(3000)
            // 値を送信
            offer(WeatherUiState.Success(createDummyData()))
            // offer(WeatherUiState.Error("error"))
        }

    }

    private fun createDummyData(): WeatherInfo {
        val weather = Weather(
            date = "1月2日(土)",
            temperature = Temperature(
                hight = "15℃",
                low = "12℃"
            ),
            wave = "2.5メートル",
            weather = "曇り",
            wind = "北東の風やや強く",
            table = listOf(
                Table(
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
