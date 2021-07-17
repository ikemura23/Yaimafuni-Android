package com.ikemura.shared.model.time_table

data class TimeTable(
    val header: Header = Header(),
    val row: List<Row> = listOf()
)
