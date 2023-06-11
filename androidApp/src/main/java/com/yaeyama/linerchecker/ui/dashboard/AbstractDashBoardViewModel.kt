package com.yaeyama.linerchecker.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yaeyama.linerchecker.core.Event
import com.yaeyama_liner_checker.domain.top.Ports
import kotlinx.coroutines.flow.StateFlow

abstract class DashBoardViewModel : ViewModel() {
    abstract val nav: LiveData<Event<DashBoardViewModelImpl.Nav>>
    abstract val uiState: StateFlow<DashBoardUiState>
    abstract fun onClickPort(ports: Ports?)
}
