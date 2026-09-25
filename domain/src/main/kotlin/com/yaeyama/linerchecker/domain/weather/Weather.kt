package com.yaeyama.linerchecker.domain.weather

/**
 * 天気 一日分
 */
data class Weather(
    /** 日付 */
    val date: String = "",
    /** 3時間毎の天気 */
    val table: List<Table> = listOf(),
    /** 気温（最低＋最高） */
    val temperature: Temperature = Temperature(),
    /** 波の高さ */
    val wave: String = "",
    /** 天気（例: 「曇り」） */
    val weather: String = "",
    /** 風の予報（例: 「北東の風やや強く」） */
    val wind: String = "",
)
