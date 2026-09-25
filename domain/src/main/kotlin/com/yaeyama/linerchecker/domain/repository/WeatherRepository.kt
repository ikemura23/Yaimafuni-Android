package com.yaeyama.linerchecker.domain.repository

import com.yaeyama.linerchecker.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

/**
 * 天気を取得する Repository
 */
interface WeatherRepository {

    /**
     * 八重山地方の今日・明日の天気を取得する
     *
     * 購読している間は更新のたびに値を流し、失敗時は
     * [com.yaeyama.linerchecker.domain.common.DataException] で Flow を終了する。
     */
    fun fetchWeather(): Flow<WeatherInfo>
}
