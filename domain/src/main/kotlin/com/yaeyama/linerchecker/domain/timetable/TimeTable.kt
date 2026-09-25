package com.yaeyama.linerchecker.domain.timetable

data class TimeTable(
    val header: Header = Header(),
    val row: List<Row> = listOf(),
)
