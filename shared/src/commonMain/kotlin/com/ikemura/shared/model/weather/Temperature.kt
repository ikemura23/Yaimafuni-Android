package com.ikemura.shared.model.weather

import kotlinx.serialization.Serializable

/**
 * 気温
 */
@Serializable
data class Temperature(
    // 最高
    var hight: String = "",
    // 最低
    var low: String = ""
)
