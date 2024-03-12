package com.yaeyama.linerchecker.ui.dashboard

import com.yaeyama_liner_checker.domain.top.Ports

data class DashBoardUiState(
    val isLoading: Boolean,
    val isError: Boolean,
    val portList: List<Ports>,
    // val aneiStatus: PortStatus,
    // val ykfStatus: PortStatus,
) {
    companion object {
        val InitialValue = DashBoardUiState(
            isLoading = false,
            isError = false,
            portList = listOf(),
            // aneiStatus = PortStatus(),
            // ykfStatus = PortStatus(),
        )
    }
}
