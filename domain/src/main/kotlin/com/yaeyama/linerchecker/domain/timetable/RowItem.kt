package com.yaeyama.linerchecker.domain.timetable

import com.yaeyama.linerchecker.domain.statusdetail.Status

/**
 * 時刻表の1便
 */
data class RowItem(
    /** 便の補足。無い場合は空文字 */
    val memo: String = "",
    /** 便ごとの運航状況 */
    val status: Status = Status(),
    /** 出発時刻（経由地などの補足を含む場合がある） */
    val time: String = "",
)
