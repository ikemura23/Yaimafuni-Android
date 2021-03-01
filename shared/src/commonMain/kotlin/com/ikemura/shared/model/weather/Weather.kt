package com.ikemura.shared.model.weather

import kotlinx.serialization.Serializable

/**
 * 天気 一日分
 */
@Serializable
data class Weather(
    // 日付
    var date: String = "",
    // 3時間毎の天気
    var table: List<Table> = listOf(),
    // 気温（最低＋最高）
    var temperature: Temperature = Temperature(),
    // 波の高さ
    var wave: String = "",
    // 天気
    var weather: String = "",
    // 風速
    var wind: String = "",
)
