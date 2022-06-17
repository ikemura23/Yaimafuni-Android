package com.yaeyama_liner_checker.domain.time_table

import kotlinx.serialization.Serializable

@Serializable
data class TimeTable(
    val header: Header = Header(),
    val row: List<Row> = listOf()
)
