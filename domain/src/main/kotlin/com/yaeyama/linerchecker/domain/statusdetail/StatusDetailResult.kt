package com.yaeyama.linerchecker.domain.statusdetail

import com.yaeyama.linerchecker.domain.timetable.TimeTable

data class StatusDetailResult(
    val portStatus: PortStatus,
    val timeTable: TimeTable,
)
