package com.yaeyama_liner_checker.domain.statusdetail

import com.yaeyama_liner_checker.domain.time_table.TimeTable


data class StatusDetailResult(
    val portStatus: PortStatus,
    val timeTable: TimeTable,
)
