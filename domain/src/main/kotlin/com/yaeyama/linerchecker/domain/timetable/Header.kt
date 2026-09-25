package com.yaeyama.linerchecker.domain.timetable

/**
 * 時刻表の見出し（出発港名）
 */
data class Header(
    /** 左列の出発港名（石垣島側） */
    val left: String = "",
    /** 右列の出発港名（相手の港側） */
    val right: String = "",
)
