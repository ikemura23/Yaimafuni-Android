package com.yaeyama_liner_checker.domain.time_table

data class TimeTable(
    val header: Header = Header(),
    val row: List<Row> = listOf(),
)
