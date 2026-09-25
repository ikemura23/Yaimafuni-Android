package com.yaeyama.linerchecker.domain.timetable

/**
 * 時刻表の1行（石垣島発と相手の港発の便の組）
 */
data class Row(
    /** 石垣島発の便 */
    val left: RowItem = RowItem(),
    /** 相手の港発の便 */
    val right: RowItem = RowItem(),
)
