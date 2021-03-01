package com.ikemura.shared.repository

import com.ikemura.shared.model.weather.WeatherInfo
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.app
import dev.gitlive.firebase.database.DataSnapshot
import dev.gitlive.firebase.database.DatabaseReference
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.serialization.DeserializationStrategy

/**
 * 天気 Repository
 */
@Suppress("EXPERIMENTAL_API_USAGE")
class WeatherRepository(
//    private val dbRef: DatabaseReference
) {

    /**
     * 天気を取得
     */
    fun fetchWeather(): Flow<WeatherUiState> = flow { // callbackFlowの戻り値はFlow型
        val dbRef = Firebase.database(Firebase.app).reference("weather")
        dbRef.valueEvents.collect { snapShot: DataSnapshot ->
            val weather = snapShot.value(WeatherInfo.serializer())
            emit(WeatherUiState.Success(weather))
        }

//        // DBへの接続
//        dbRef.addValueEventListener(object : ValueEventListener {
//            // 正常に取得できた
//            override fun onDataChange(snapshot: DataSnapshot) {
//                snapshot.getValue(WeatherInfo::class.java)?.let { weather ->
////                    Timber.d(weather.toString())
//                    // offerで購読側に値を流せる
//                    offer(WeatherUiState.Success(weather))
//                }
//            }
//
//            // エラー
//            override fun onCancelled(error: DatabaseError) {
//                offer(WeatherUiState.Error(error.message))
//            }
//        })
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