package com.ikmr.banbara23.yaeyama_liner_checker.model.time_table

data class TimeTable(
    val header: Header = Header(),
    val row: List<Row> = listOf()
)
