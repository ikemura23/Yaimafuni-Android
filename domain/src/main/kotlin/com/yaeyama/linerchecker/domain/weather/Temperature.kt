package com.yaeyama.linerchecker.domain.weather

/**
 * 気温
 */
data class Temperature(
    /** 最高気温（Firebase のキーに合わせて綴りは hight のまま） */
    val hight: String = "",
    /** 最低気温 */
    val low: String = "",
)
