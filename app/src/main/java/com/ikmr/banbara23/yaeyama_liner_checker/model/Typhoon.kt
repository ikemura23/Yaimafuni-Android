package com.ikmr.banbara23.yaeyama_liner_checker.model

/**
 * 台風のモデル
 */
data class Typhoon(
    val name: String = "",
    val dateTime: String = "",
    val img: String = "",
    val scale: String = "",
    val intensity: String = "",
    val pressure: String = "",
    val area: String = "",
    val maxWindSpeedNearCenter: String = ""
)