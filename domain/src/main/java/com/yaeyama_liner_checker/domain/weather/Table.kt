package com.yaeyama_liner_checker.domain.weather

import kotlinx.serialization.Serializable

/**
 * 時間毎の天気
 */
@Serializable
data class Table(
    // 時間
    val hour: String = "",
    // 天気
    val weather: String = "",
    // 風向き
    val windBlow: String = "",
    // 風速
    val windSpeed: String = ""
)
