package com.yaeyama.linerchecker.ui.dashboard

import kotlinx.coroutines.flow.StateFlow

interface DashBoardViewModel {
    val uiState: StateFlow<DashBoardUiState>
    suspend fun fetchPortList()
}
