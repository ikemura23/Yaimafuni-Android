package com.yaeyama.linerchecker.ui.dashboard

import com.yaeyama.linerchecker.domain.top.Ports

data class DashBoardUiState(
    val isLoading: Boolean,
    val isError: Boolean,
    val portList: List<Ports>,
) {
    companion object {
        val InitialValue = DashBoardUiState(
            isLoading = false,
            isError = false,
            portList = listOf(),
        )
    }
}
