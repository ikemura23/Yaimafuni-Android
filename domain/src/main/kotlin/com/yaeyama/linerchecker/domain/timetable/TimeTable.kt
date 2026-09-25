package com.yaeyama.linerchecker.domain.timetable

/**
 * ある会社が運航する1航路の時刻表
 *
 * Firebase からデシリアライズするため、プロパティ名は Firebase のキーと一致させること。
 */
data class TimeTable(
    /** 列の見出し */
    val header: Header = Header(),
    /** 便の一覧（出発時刻順） */
    val row: List<Row> = listOf(),
)
