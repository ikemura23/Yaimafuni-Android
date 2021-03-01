package com.ikemura.shared.model.weather

import kotlinx.serialization.Serializable

/**
 * 時間毎の天気
 */
@Serializable
data class Table(
    // 時間
    var hour: String = "",
    // 天気
    var weather: String = "",
    // 風向き
    var windBlow: String = "",
    // 風速
    var windSpeed: String = ""
)
