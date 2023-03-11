package com.yaeyama_liner_checker.domain.weather

import kotlinx.serialization.Serializable

/**
 * 気温
 */
@Serializable
data class Temperature(
    // 最高
    val hight: String = "",
    // 最低
    val low: String = "",
)
