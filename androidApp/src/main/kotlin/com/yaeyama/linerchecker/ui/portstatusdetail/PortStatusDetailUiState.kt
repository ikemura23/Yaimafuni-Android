package com.yaeyama.linerchecker.ui.portstatusdetail

import com.yaeyama_liner_checker.domain.statusdetail.PortStatus
import com.yaeyama_liner_checker.domain.time_table.TimeTable

data class PortStatusDetailUiState(
    val isLoading: Boolean,
    val isError: Boolean,
    val portStatus: PortStatus,
    val timeTable: TimeTable,
) {
    companion object {
        val InitialValue = PortStatusDetailUiState(
            isLoading = false,
            isError = false,
            portStatus = PortStatus(),
            timeTable = TimeTable(),
        )
    }
}
