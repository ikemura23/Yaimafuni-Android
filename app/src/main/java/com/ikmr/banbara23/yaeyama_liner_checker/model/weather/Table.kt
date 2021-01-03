package com.ikmr.banbara23.yaeyama_liner_checker.model.weather

/**
 * 時間毎の天気
 */
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
