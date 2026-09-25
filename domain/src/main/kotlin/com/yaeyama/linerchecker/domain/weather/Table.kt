package com.yaeyama.linerchecker.domain.weather

/**
 * 時間毎の天気
 */
data class Table(
    /** 時刻（時） */
    val hour: String = "",
    /** 天気 */
    val weather: String = "",
    /** 風向き（Firebase のキーに合わせて名前は windBlow のまま） */
    val windBlow: String = "",
    /** 風速 */
    val windSpeed: String = "",
)
