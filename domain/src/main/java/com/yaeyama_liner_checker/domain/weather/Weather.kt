package com.yaeyama_liner_checker.domain.weather

import kotlinx.serialization.Serializable

/**
 * 天気 一日分
 */
@Serializable
data class Weather(
    // 日付
    val date: String = "",
    // 3時間毎の天気
    val table: List<Table> = listOf(),
    // 気温（最低＋最高）
    val temperature: Temperature = Temperature(),
    // 波の高さ
    val wave: String = "",
    // 天気
    val weather: String = "",
    // 風速
    val wind: String = "",
)
