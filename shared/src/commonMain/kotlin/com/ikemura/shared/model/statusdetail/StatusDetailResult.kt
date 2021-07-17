package com.ikemura.shared.model.statusdetail

import com.ikemura.shared.model.time_table.TimeTable

data class StatusDetailResult(
    val portStatus: PortStatus,
    val timeTable: TimeTable,
)
