package com.yaeyama.linerchecker.ui.dashboard

import androidx.lifecycle.LiveData
import com.yaeyama.linerchecker.core.Event
import com.yaeyama_liner_checker.domain.top.Ports
import kotlinx.coroutines.flow.StateFlow

interface DashBoardViewModel {
    val nav: LiveData<Event<DashBoardViewModelImpl.Nav>>
    val uiState: StateFlow<DashBoardUiState>
    fun onClickPort(ports: Ports?)
}
