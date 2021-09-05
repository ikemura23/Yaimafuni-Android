package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import androidx.lifecycle.MutableLiveData
import com.ikemura.shared.model.top.Ports
import com.ikemura.shared.model.top.TopPort
import com.ikemura.shared.repository.TopStatusRepositoryImpl
import com.ikemura.shared.repository.UiState
import com.ikmr.banbara23.yaeyama_liner_checker.core.Event
import com.ikmr.banbara23.yaeyama_liner_checker.core.toEvent
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

/**
 * トップに表示するステータスのダッシュボード ViewModel
 */
class DashBoardViewModelImpl : DashBoardViewModel() {

    override val nav = MutableLiveData<Event<Nav>>()

    private val topStatusRepository = TopStatusRepositoryImpl() // TODO: DIする

    fun fetchTopStatuses(): Flow<UiState<List<Ports>>> = topStatusRepository.fetchTopStatuses()

    /**
     * 港クリック
     */
    override fun onClickPort(ports: Ports?) {
        Timber.d("clicked: $ports")
        ports ?: return
        nav.value = Nav.GoDetail(ports).toEvent()
    }

    sealed class Nav {
        data class GoDetail(val ports: Ports) : Nav()
    }
}

sealed class TopPortStatusState {
    data class Success(val topPort: TopPort) : TopPortStatusState()
    data class Error(val error: Throwable) : TopPortStatusState()
}
