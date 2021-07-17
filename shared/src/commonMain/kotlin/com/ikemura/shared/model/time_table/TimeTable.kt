package com.ikemura.shared.model.time_table

import kotlinx.serialization.Serializable

@Serializable
data class TimeTable(
    val header: Header = Header(),
    val row: List<Row> = listOf()
)
