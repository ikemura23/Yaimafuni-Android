package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ikemura.shared.model.top.Ports
import com.ikmr.banbara23.yaeyama_liner_checker.core.Event
import com.ikmr.banbara23.yaeyama_liner_checker.model.top.TopPort

abstract class DashBoardViewModel : ViewModel() {
    abstract val uiState: LiveData<TopPort>
    abstract val nav: LiveData<Event<DashBoardViewModelImpl.Nav>>

    abstract fun onClickPort(ports: Ports?)
}
