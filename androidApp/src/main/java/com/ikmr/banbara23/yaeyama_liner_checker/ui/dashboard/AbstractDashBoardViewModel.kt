package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ikmr.banbara23.yaeyama_liner_checker.core.Event
import com.yaeyama_liner_checker.domain.top.Ports

abstract class DashBoardViewModel : ViewModel() {
    abstract val nav: LiveData<Event<DashBoardViewModelImpl.Nav>>

    abstract fun onClickPort(ports: Ports?)
}
